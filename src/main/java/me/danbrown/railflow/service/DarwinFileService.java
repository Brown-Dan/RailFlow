package me.danbrown.railflow.service;

import jakarta.jms.JMSException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import me.danbrown.railflow.repository.FileImportRepository;
import me.danbrown.railflow.repository.JourneyRepository;
import me.danbrown.railflow.service.mapper.XmlToTimetableMapper;
import me.danbrown.railflow.service.model.Timetable;
import me.danbrown.railflow.service.model.xml.JourneyXml;
import me.danbrown.railflow.service.model.xml.StationXml;
import me.danbrown.railflow.service.model.xml.TimetableXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

import static java.util.function.Predicate.not;

@Service
public class DarwinFileService {

    private static final Logger LOG = LoggerFactory.getLogger(DarwinFileService.class);

    private final S3Client s3Client;
    private final String bucket;
    private final String object;
    private final XmlToTimetableMapper timetableXmlMapper;
    private final JourneyRepository journeyRepository;
    private final FileImportRepository fileImportRepository;

    public DarwinFileService(S3Client s3Client, @Value("${darwin.s3.bucket}") String bucket, @Value("${darwin.s3.object.prefix}") String object, XmlToTimetableMapper timetableXmlMapper, JourneyRepository journeyRepository, FileImportRepository fileImportRepository) {
        this.s3Client = s3Client;
        this.bucket = bucket;
        this.object = object;
        this.timetableXmlMapper = timetableXmlMapper;
        this.journeyRepository = journeyRepository;
        this.fileImportRepository = fileImportRepository;
    }

    private BufferedReader getBufferedReader(ResponseInputStream<GetObjectResponse> input) throws JMSException, IOException {
        GZIPInputStream gzipInputStream = new GZIPInputStream(input);
        InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);

        return new BufferedReader(inputStreamReader);
    }

    public void download() {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucket)
                .prefix(object)
                .build();
        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);
        List<S3Object> objects = listObjectsV2Response.contents().stream().filter(not(file -> fileImportRepository.importedFileExists(file.eTag()))).toList();

        objects.stream().map(obj -> GetObjectRequest.builder()
                .bucket(bucket)
                .key(obj.key())
                .build()).map(s3Client::getObject).map(inputStream -> {
            try {
                return mapDataToTimetable(getBufferedReader(inputStream));
            } catch (JAXBException | IOException | JMSException e) {
                LOG.error("Failed mapping input stream to timetable", e);
                return null;
            }
        }).filter(Objects::nonNull).forEach(timetable -> {
            LOG.info("Downloaded timetable from S3: {}", timetable.timetableId());
            timetable.journeys().forEach(journeyRepository::insertJourney);
        });

        objects.forEach(obj -> fileImportRepository.insertImportedFile(obj.eTag()));
    }

    public Timetable mapDataToTimetable(BufferedReader data) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(TimetableXml.class, JourneyXml.class, StationXml.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        TimetableXml timetableXml = (TimetableXml) unmarshaller.unmarshal(data);

        return timetableXmlMapper.map(timetableXml);
    }
}
