package me.danbrown.railflow.service;

import jakarta.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class DarwinFileService {

    private static final Logger LOG = LoggerFactory.getLogger(DarwinFileService.class);

    private final S3Client s3Client;
    private final String bucket;
    private final String object;

    public DarwinFileService(S3Client s3Client, @Value("${darwin.s3.bucket}") String bucket, @Value("${darwin.s3.object.prefix}") String object) {
        this.s3Client = s3Client;
        this.bucket = bucket;
        this.object = object;
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
                .key(objects.get(1).key())
                .build();

        ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getObjectRequest);
        try {
            BufferedReader bufferedReader = getBufferedReader(s3Object);
            try (FileWriter fileWriter = new FileWriter(new ClassPathResource("/").getFile().getPath() + "/downloaded_file.xml")) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    fileWriter.write(line);
                    fileWriter.write(System.lineSeparator());
                }
            }
        } catch (JMSException | IOException e) {
            LOG.error("Failed to download file.");
        }
    }


}
