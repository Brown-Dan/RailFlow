package me.danbrown.railflow.service;

import jakarta.xml.bind.JAXBException;
import me.danbrown.railflow.repository.JourneyRepository;
import me.danbrown.railflow.service.mapper.XmlToTimetableMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@ExtendWith(MockitoExtension.class)
class DarwinFileServiceTest {

    @Mock
    S3Client s3Client;

    @Mock
    JourneyRepository journeyRepository;

    private DarwinFileService darwinFileService;

    @BeforeEach
    void setUp() {
        darwinFileService = new DarwinFileService(s3Client, "bucket", "object", new XmlToTimetableMapper(), journeyRepository);
    }

    @Test
    void name() throws IOException, JAXBException {
        darwinFileService.mapDataToTimetable(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(new ClassPathResource("test.xml").getContentAsByteArray()))));
    }

    @Test
    void me() throws JAXBException {
    }
}
