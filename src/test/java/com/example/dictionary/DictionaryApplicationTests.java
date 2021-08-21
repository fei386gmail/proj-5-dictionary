package com.example.dictionary;

import com.example.dictionary.model.Synonym;
import com.example.dictionary.model.Word;
import com.example.dictionary.orm.SynonymServ;
import com.example.dictionary.orm.WordServ;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class DictionaryApplicationTests {

    @Autowired
    private SynonymServ serv;

    @Test
    void contextLoads() {

        Synonym synonym=new Synonym();
        synonym.setWord("yyyy");
        synonym.setSynonym("xxx");
        serv.newSynonym(synonym);



    }

}
