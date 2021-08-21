package com.example.dictionary.orm;


import com.example.dictionary.model.Synonym;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SynonymServ {
    @Autowired
    private SynonymRepo synonymRepo;


    public void newSynonym(Synonym synonym)
    {
        synonymRepo.save(synonym);
    }
}
