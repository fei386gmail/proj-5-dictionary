package com.example.dictionary.orm;

import com.example.dictionary.model.Antonym;
import com.example.dictionary.model.Synonym;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AntonymServ {
    @Autowired
    private AntonymRepo antonymRepo;

    public void newAntonym(Antonym antonym)
    {
        antonymRepo.save(antonym);
    }

    public String findAntonym(String word){

        List<Antonym> antonyms=antonymRepo.findAllByWord(word);
        List<String> properties =antonymRepo.getPropertiesByWord(word);
        String result="";
        for (String s : properties
        ) {
            result=result.concat(s);
            for (Antonym syn: antonyms
            ) {
                if(syn.getProperty().equals(s))
                {
                    result=result.concat(syn.getAntonym()).concat(" ");
                }
            }
            result=result.concat(" ");
        }
        return result;
    }
}
