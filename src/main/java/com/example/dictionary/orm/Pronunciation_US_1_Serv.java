package com.example.dictionary.orm;

import com.example.dictionary.model.Pronunciation_US_1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Pronunciation_US_1_Serv {

    @Autowired
    private Pronunciation_US_1_Repo pronunciation_1Repo;

    public void save(Pronunciation_US_1 p)
    {
        pronunciation_1Repo.save(p);
    }

    public Pronunciation_US_1 get(String word)
    {
       return   pronunciation_1Repo.findByWord(word);
    }

    public boolean havePronunciation(String word){
        if(pronunciation_1Repo.findByWord(word)!=null)
        {
            return  true;
        }
        else {
            return  false;
        }
    }
    public Boolean isExist(String word)
    {
        return  pronunciation_1Repo.existsByWord(word);
    }
}
