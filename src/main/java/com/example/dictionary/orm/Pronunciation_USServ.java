package com.example.dictionary.orm;

import com.example.dictionary.model.Pronunciation_US;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Pronunciation_USServ {

    @Autowired
    private Pronunciation_USRepo pronunciation_usRepo;

    public void save(Pronunciation_US p)
    {
        pronunciation_usRepo.save(p);
    }

    public Pronunciation_US get(String word)
    {
       return   pronunciation_usRepo.findByWord(word);
    }


}
