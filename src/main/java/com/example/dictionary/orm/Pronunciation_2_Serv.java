package com.example.dictionary.orm;

import com.example.dictionary.model.Pronunciation_US_2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Pronunciation_2_Serv {
    @Autowired
    private Pronunciation_2_Repo pronunciation_2_repo;

    public void save(Pronunciation_US_2 p)
    {
        pronunciation_2_repo.save(p);
    }

    public Pronunciation_US_2 findByWord(String word){
       return   pronunciation_2_repo.findByWord(word);
    }

    public boolean havePronunciation(String word){
        if(pronunciation_2_repo.findByWord(word)!=null)
        {
            return  true;
        }
        else {
            return  false;
        }
    }
    public Boolean isExist(String word)
    {
        return  pronunciation_2_repo.existsByWord(word);
    }
}
