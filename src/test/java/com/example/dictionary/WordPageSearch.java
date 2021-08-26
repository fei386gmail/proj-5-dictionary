package com.example.dictionary;

import com.example.dictionary.common.TablePageable;
import com.example.dictionary.model.Synonym;
import com.example.dictionary.model.Word;
import com.example.dictionary.orm.SynonymServ;
import com.example.dictionary.orm.WordServ;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class WordPageSearch {

    @Autowired
    private SynonymServ serv;
    @Autowired
    private WordServ wordServ;

    @Test
    void contextLoads() {

        PageRequest pageRequest=PageRequest.of(1039,100);
        Page<Word> page=wordServ.findAll(pageRequest);
       List<Word> words=  page.getContent();
       long total= page.getTotalElements();
       long totalPages= page.getTotalPages();

        System.out.println("total:"+total);
        System.out.println("pages:"+totalPages);
        System.out.println("content:"+page.getContent());

    }

}
