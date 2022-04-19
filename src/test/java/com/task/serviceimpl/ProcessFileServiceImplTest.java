package com.task.serviceimpl;

import com.task.exception.EmptyFileException;
import com.task.exception.InvalidFileTypeException;
import com.task.service.ProcessFileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProcessFileServiceImplTest {

    private final ProcessFileService processFileService = new ProcessFileServiceImpl();

    @Test
    void testProcessFileService_withValidData() throws IOException {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello Text".getBytes()
        );
        List<String> resultList = processFileService.processFile(file);
        Assertions.assertEquals(2, resultList.size());
        Assertions.assertEquals(resultList.get(0), "({e, o}, 5) -> 2");
        Assertions.assertEquals(resultList.get(1), "({e}, 4) -> 1");
    }

    @Test
    void testProcessFileService_withValid1() throws IOException {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Platon made bamboo boats.".getBytes()
        );
        List<String> resultList = processFileService.processFile(file);
        Assertions.assertEquals(3, resultList.size());
        Assertions.assertEquals(resultList.get(0), "({a, o}, 6) -> 2.5");
        Assertions.assertEquals(resultList.get(1), "({a, e}, 4) -> 2");
        Assertions.assertEquals(resultList.get(2), "({a, o}, 5) -> 2");
    }

    @Test
    void testProcessFileService_withInvalidFileType() throws IOException {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.PDF",
                MediaType.APPLICATION_PDF_VALUE,
                "Platon made bamboo boats.".getBytes()
        );
        InvalidFileTypeException invalidFileTypeException = assertThrows(InvalidFileTypeException.class, () -> processFileService.processFile(file));

        Assertions.assertEquals("Please select .txt files", invalidFileTypeException.getMessage());
    }

    @Test
    void testProcessFileService_withNoData() throws IOException {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "".getBytes()
        );
        EmptyFileException emptyFileException = assertThrows(EmptyFileException.class, () -> processFileService.processFile(file));

        Assertions.assertEquals("Provided file does not contains any words", emptyFileException.getMessage());
    }
}
