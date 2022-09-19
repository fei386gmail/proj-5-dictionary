package com.example.dictionary.common;


import com.example.dictionary.model.Sentence1;
import com.example.dictionary.model.Sentence2;
import com.example.dictionary.model.Word;
import com.example.dictionary.orm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    private Sentence1Serv sentence1Serv;
    @Autowired
    private Sentence2Serv sentence2Serv;
    @Autowired
    private PictureServ pictureServ;
    public DetailResult getResult(String id)
    {
        Word word=wordServ.findOneById(id);
        String synonym=synonymServ.findSynonym(id);
        String antonym=antonymServ.findAntonym(id);
        String collocation=collocationServ.findCollations(id);
        Sentence1 sentence1= sentence1Serv.findSentenceByWord(id);
        Sentence2 sentence2=sentence2Serv.findSentenceByWord(id);
        List<SentenceResult> sentenceResultList=new ArrayList<>();
        if(sentence1!=null)
        sentenceResultList.add(this.copyToSentenceResult(sentence1));
        if(sentence2!=null)
        sentenceResultList.add(this.copyToSentenceResult(sentence2));
        boolean hasPic=pictureServ.hasPic(id);
        boolean remember =wordServ.isRemember(id);

        return new DetailResult(word.getWord(),word.getTranslation(),synonym,antonym,collocation,sentenceResultList,hasPic,remember);
    }
    private SentenceResult copyToSentenceResult(Sentence1 sentence1)
    {
        if(sentence1==null) return null;
        return new SentenceResult(sentence1.getId(),sentence1.getWord(),sentence1.getSentence_english(),sentence1.getSentence_chinese());
    }
    private SentenceResult copyToSentenceResult(Sentence2 sentence2)
    {
        if(sentence2==null) return null;
        return new SentenceResult(sentence2.getId(),sentence2.getWord(),sentence2.getSentence_english(),sentence2.getSentence_chinese());
    }
}
