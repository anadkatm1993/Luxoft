package com.task.controller;

import com.task.service.ProcessFileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ProcessFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProcessFileService processFileService;

    @Test
    void testProcessController_withInvalidFileType() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.pdf",
                String.valueOf(MediaType.APPLICATION_PDF),
                "Hello, World!".getBytes()
        );
        mockMvc.perform(MockMvcRequestBuilders.multipart("/processFiles")
                        .file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testProcessController_withNoData() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "".getBytes()
        );
        mockMvc.perform(MockMvcRequestBuilders.multipart("/processFiles")
                        .file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testProcessController_withData() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello".getBytes()
        );
        mockMvc.perform(MockMvcRequestBuilders.multipart("/processFiles")
                        .file(file))
                .andExpect(status().isOk());
    }
}
