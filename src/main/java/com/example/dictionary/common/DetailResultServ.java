package com.example.dictionary.common;


import com.example.dictionary.model.Word;
import com.example.dictionary.orm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DetailResultServ {

    @Autowired
    private WordServ wordServ;
    @Autowired
    private SynonymServ synonymServ;
    @Autowired
    private AntonymServ antonymServ;
    @Autowired
    private CollocationServ collocationServ;
    @Autowired
    private Pronunciation_US_1_Serv pronunciation_1Serv;

    public DetailResult getResult(String id)
    {
        Word word=wordServ.findOneById(id);
        String synonym=synonymServ.findSynonym(id);
        String antonym=antonymServ.findAntonym(id);
        String collocation=collocationServ.findCollations(id);
        boolean pronunciation= pronunciation_1Serv.havePronunciation(id);
        return new DetailResult(word.getWord(),word.getTranslation(),synonym,antonym,collocation);
    }
}
