package com.example.dictionary.orm;


import com.example.dictionary.model.Synonym;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SynonymServ {
    @Autowired
    private SynonymRepo synonymRepo;

    public void newSynonym(Synonym synonym)
    {
        synonymRepo.save(synonym);
    }

    public String findSynonym(String word){

        List<Synonym> synonyms=synonymRepo.findAllByWord(word);
        List<String> properties =synonymRepo.getPropertiesByWord(word);
        String resultSynonym="";
        for (String s : properties
             ) {
            resultSynonym=resultSynonym.concat(s).concat(" ");
            for (Synonym syn: synonyms
                 ) {
                if(syn.getProperty().equals(s))
                {
                    resultSynonym=resultSynonym.concat(syn.getSynonym()).concat(". ");
                }
            }
            resultSynonym=resultSynonym.concat(" ");
        }

        return resultSynonym;
    }
    public void delete(Synonym s)
    {
        synonymRepo.delete(s);
    }

    public List<Synonym> findAllSynonymClass(String word)
    {
        return synonymRepo.findAllByWord(word);
    }
    public boolean compare(Synonym a,Synonym b)
    {
        if(a.getWord().equals(b.getWord()) && a.getProperty().equals(b.getProperty()) && a.getSynonym().equals(b.getSynonym()))
        {
            return true;
        }
        else {
            return  false;
        }
    }

    public Boolean isExist(String word)
    {
        return  synonymRepo.existsByWord(word);
    }
}
