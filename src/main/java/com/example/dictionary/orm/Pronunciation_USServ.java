package com.example.dictionary.orm;

import com.example.dictionary.model.Pronunciation_US_Bing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Pronunciation_USServ {

    @Autowired
    private Pronunciation_USRepo pronunciation_usRepo;

    public void save(Pronunciation_US_Bing p)
    {
        pronunciation_usRepo.save(p);
    }

    public Pronunciation_US_Bing get(String word)
    {
       return   pronunciation_usRepo.findByWord(word);
    }

    public boolean havePronunciation(String word){
        if(pronunciation_usRepo.findByWord(word)!=null)
        {
            return  true;
        }
        else {
            return  false;
        }
    }
}
