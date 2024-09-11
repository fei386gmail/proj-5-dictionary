package com.example.dictionary.orm;

import com.example.dictionary.model.Antonym;
import com.example.dictionary.model.Collocation;
import com.example.dictionary.model.Synonym;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollocationServ {
    @Autowired
    private CollocationRepo collocationRepo;

    public void newCollocation(Collocation collocation){
        collocationRepo.save(collocation);
    }

    public String findCollations(String word){

        List<Collocation> collocations=collocationRepo.findAllByWord(word);
        List<String> properties =collocationRepo.getPropertiesByWord(word);
        String result="";
        for (String s : properties
        ) {
            result=result.concat(s);
            for (Collocation syn: collocations
            ) {
                if(syn.getProperty().equals(s))
                {
                    result=result.concat(syn.getCollocation()).concat(". ");
                }
            }
            result=result.concat(" ");
        }
        return result;
    }

    public List<Collocation> findAllCollations(String word)
    {
        return collocationRepo.findAllByWord(word);
    }

    public boolean compare(Collocation a, Collocation b)
    {
        if(a.getWord().equals(b.getWord()) && a.getProperty().equals(b.getProperty()) && a.getCollocation().equals(b.getCollocation()))
        {
            return true;
        }
        else {
            return  false;
        }
    }

    public void delete(Collocation b) {
        collocationRepo.delete(b);
    }

    public Boolean isExist(String word){
         return collocationRepo.existsByWord(word);
    }

}
