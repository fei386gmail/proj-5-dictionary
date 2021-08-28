package com.example.dictionary;

import com.example.dictionary.control.WordControl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ControlTest {
    @Autowired
    private WordControl wordControl;

    @Test
    public void tt()
    {
        System.out.println(wordControl.getResult("book"));
    }
}
