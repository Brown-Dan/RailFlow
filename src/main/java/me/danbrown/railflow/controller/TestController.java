package me.danbrown.railflow.controller;

import me.danbrown.railflow.repository.JourneyRepository;
import me.danbrown.railflow.service.DarwinFileService;
import me.danbrown.railflow.service.model.Journey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final DarwinFileService darwinFileService;
    private final JourneyRepository journeyRepository;

    public TestController(DarwinFileService darwinFileService, JourneyRepository journeyRepository) {
        this.darwinFileService = darwinFileService;
        this.journeyRepository = journeyRepository;
    }

    @GetMapping("/download")
    public void download() {
        darwinFileService.download();
    }

    @GetMapping("/search")
    public Journey findJourney(@RequestParam String trainId) {
        return journeyRepository.fetchJourneyByTrainId(trainId).orElseThrow();
    }
}
