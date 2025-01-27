package me.danbrown.railflow.service;

import jakarta.xml.bind.JAXBException;
import me.danbrown.railflow.service.mapper.XmlToTimetableMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class DarwinFileServiceTest {

    @Mock
    S3Client s3Client;

    private DarwinFileService darwinFileService;

    @BeforeEach
    void setUp() {
        darwinFileService = new DarwinFileService(s3Client, "bucket", "object", new XmlToTimetableMapper());
    }

    @Test
    void name() throws IOException, JAXBException {
        darwinFileService.mapDataToTimetable();
    }

    @Test
    void me() throws JAXBException {
    }
}
