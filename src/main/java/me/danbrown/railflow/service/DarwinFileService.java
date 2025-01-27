package me.danbrown.railflow.service;

import jakarta.jms.JMSException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import me.danbrown.railflow.service.model.Timetable;
import me.danbrown.railflow.service.model.TimetableXmlMapper;
import me.danbrown.railflow.service.model.xml.JourneyXml;
import me.danbrown.railflow.service.model.xml.StationXml;
import me.danbrown.railflow.service.model.xml.TimetableXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class DarwinFileService {

    private static final Logger LOG = LoggerFactory.getLogger(DarwinFileService.class);

    private final S3Client s3Client;
    private final String bucket;
    private final String object;
    private final TimetableXmlMapper timetableXmlMapper;

    public DarwinFileService(S3Client s3Client, @Value("${darwin.s3.bucket}") String bucket, @Value("${darwin.s3.object.prefix}") String object, TimetableXmlMapper timetableXmlMapper) {
        this.s3Client = s3Client;
        this.bucket = bucket;
        this.object = object;
        this.timetableXmlMapper = timetableXmlMapper;
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
        List<S3Object> objects = listObjectsV2Response.contents();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(objects.getLast().key())
                .build();

        ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getObjectRequest);
        try {
            BufferedReader bufferedReader = getBufferedReader(s3Object);
            try (FileWriter fileWriter = new FileWriter(new ClassPathResource("/").getFile().getPath() + "/test.xml")) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    fileWriter.write(line);
                    fileWriter.write(System.lineSeparator());
                }
            }
            Timetable timetable = processTimetableFile();
        } catch (JMSException | IOException e) {
            LOG.error("Failed to download file.");
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public Timetable processTimetableFile() throws JAXBException, IOException {
        LOG.info("Processing file");
        JAXBContext jaxbContext = JAXBContext.newInstance(TimetableXml.class, JourneyXml.class, StationXml.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        File file = new ClassPathResource("/test.xml").getFile();
        TimetableXml timetableXml = (TimetableXml) unmarshaller.unmarshal(file);
        return timetableXmlMapper.map(timetableXml);
    }
}
