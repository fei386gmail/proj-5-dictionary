package com.example.dictionary;

import com.example.dictionary.common.ResultServ;
import com.example.dictionary.orm.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServTest {
    @Autowired
    private SynonymServ synonymServ;
    @Autowired
    private AntonymServ antonymServ;
    @Autowired
    private CollocationServ  collocationServ;
    @Autowired
    private ResultServ resultServ;

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
    }
}
