package com.task;

import com.task.controller.ProcessFileController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {

    @Autowired
    private ProcessFileController processFileController;

    @Test
    void contextLoad() {
        Assertions.assertNotNull(processFileController);
    }

}
