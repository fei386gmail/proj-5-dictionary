package com.example.dictionary.common;

import com.example.dictionary.model.Word;
import com.example.dictionary.orm.FrequencyServ;
import com.example.dictionary.orm.Pronunciation_2_Serv;
import com.example.dictionary.orm.Pronunciation_US_1_Serv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Tools {
    private Pronunciation_US_1_Serv pronunciationUs1Serv;
    private Pronunciation_2_Serv pronunciation_2_serv;
    private FrequencyServ frequencyServ;
    @Autowired
    Tools(Pronunciation_US_1_Serv pronunciationUs1Serv,
          Pronunciation_2_Serv pronunciation_2_serv,
          FrequencyServ frequencyServ)
    {
        this.pronunciationUs1Serv=pronunciationUs1Serv;
        this.pronunciation_2_serv=pronunciation_2_serv;
        this.frequencyServ=frequencyServ;
    }
    public List<WordResult> copyWords2WordResults(List<Word> wordList)
    {
        List<WordResult> wordResults=new ArrayList<>();

        for (Word w: wordList
        ) {
            WordResult result=new WordResult();
            if(w.getWord()==null ||
            w.getRemember()==null ||
            w.getTranslation() ==null) continue;
            wordResults.add(new WordResult(w.getWord(),w.getTranslation(), pronunciationUs1Serv.havePronunciation(w.getWord()),pronunciation_2_serv.havePronunciation(w.getWord()),frequencyServ.getFrequency(w.getWord()),w.getRemember()));

        }
        return wordResults;
    }
}
