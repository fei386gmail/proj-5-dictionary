package com.example.dictionary;

import com.example.dictionary.common.DetailResultServ;
import com.example.dictionary.model.EnWords_bak;
import com.example.dictionary.model.Frequency;
import com.example.dictionary.model.Word;
import com.example.dictionary.orm.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest
public class ServTest {

    @Autowired
    private WordServ wordServ;
    @Autowired
    private SynonymServ synonymServ;
    @Autowired
    private AntonymServ antonymServ;
    @Autowired
    private CollocationServ  collocationServ;
    @Autowired
    private DetailResultServ resultServ;
    @Autowired
    private FrequencyServ frequencyServ;
    @Autowired
    private Sentence1Serv sentence1Serv;
    @Autowired
    private EnWordsServe enWordsServe;

    @Test
    public void ss()
    {
        String enWordsBak=enWordsServe.getTranByWord("yes");
        System.out.println(enWordsBak);

    }
}
