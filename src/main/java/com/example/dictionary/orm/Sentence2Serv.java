package com.example.dictionary.orm;

import com.example.dictionary.model.Sentence1;
import com.example.dictionary.model.Sentence2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Sentence2Serv {
    @Autowired
    private Sentence2Repo sentence2Repo;
    public List<Sentence2> findSentenceList(String word) {
        return  sentence2Repo.findAllByWord(word);
    }
    public void save(Sentence2 sentence)
    {
        sentence2Repo.save(sentence);
    }

    public static boolean hasPronunciation(Sentence1 sentence)
    {
        if (sentence.getPronunciation()==null || sentence.getPronunciation().length==0 ){
            return false;
        }
        else {
            return true;
        }
    }

    public Sentence2 findSentenceById(int id)
    {
        return sentence2Repo.findById(id).get();
    }
    public Sentence2 findSentenceByWord(String word){
        return  sentence2Repo.findSentenceByWord(word);
    }
}
