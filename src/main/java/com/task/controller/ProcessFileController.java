package com.task.controller;

import com.task.service.ProcessFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ProcessFileController {

    private final ProcessFileService processFileService;

    @Autowired
    public ProcessFileController(ProcessFileService processFileService) {
        this.processFileService = processFileService;
    }

    /**
     * This method takes file as input and send to service to process
     *
     * @param inputFile
     * @return
     * @throws IOException
     */
    @PostMapping("processFiles")
    public List<String> processFile(@RequestPart("file") MultipartFile inputFile) throws IOException {
        return processFileService.processFile(inputFile);
    }
}
