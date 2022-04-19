package com.task.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProcessFileService {

    List<String> processFile(MultipartFile inputFile) throws IOException;
}
