package me.danbrown.railflow.controller;

import me.danbrown.railflow.service.DarwinFileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    private final DarwinFileService darwinFileService;

    public FileController(DarwinFileService darwinFileService) {
        this.darwinFileService = darwinFileService;
    }

    @GetMapping("/download")
    public void download() {
        darwinFileService.download();
    }
}
