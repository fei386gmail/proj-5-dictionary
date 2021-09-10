package com.example.dictionary;

import com.example.dictionary.common.DetailResultServ;
import com.example.dictionary.model.Word;
import com.example.dictionary.orm.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    @Test
    public void ss()
    {
        String r=synonymServ.findSynonym("reverse");
        String a=antonymServ.findAntonym("reverse");
        String c=collocationServ.findCollations("reverse");
        System.out.println(r);
        System.out.println(a);
        System.out.println(c);

        System.out.println(resultServ.getResult("reverse"));

        PageRequest pageRequest=PageRequest.of(0,100);
        Page<Word> page=wordServ.findAll(pageRequest);
        long totalPages= page.getTotalPages();
        System.out.println("totalPages:"+totalPages);


    }
}
