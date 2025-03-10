package me.danbrown.railflow.controller;

import me.danbrown.railflow.controller.model.SimpleJourneyResource;
import me.danbrown.railflow.repository.JourneyRepository;
import me.danbrown.railflow.service.DarwinFileService;
import me.danbrown.railflow.service.model.Journey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Journey findJourney(@RequestParam(required = false) String trainId, @RequestParam(required = false) String tiploc) {
        return journeyRepository.fetchJourneyByTrainId(trainId).orElseThrow();
    }

    @GetMapping("/origin/{tiploc}")
    public List<SimpleJourneyResource> getJourneyByTiploc(@PathVariable String tiploc) {
        return journeyRepository.fetchSimpleJourneyByOriginTiploc(tiploc);
    }
}
