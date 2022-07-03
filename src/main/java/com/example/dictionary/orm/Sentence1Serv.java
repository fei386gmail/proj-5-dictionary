package com.example.dictionary.orm;

import com.example.dictionary.model.Sentence1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Sentence1Serv {
    @Autowired
    private Sentence1Repo sentence1Repo;

    public List<Sentence1> findSentenceList(String word) {
        return  sentence1Repo.findAllByWord(word);
    }
    public void save(Sentence1 sentence)
    {
        sentence1Repo.save(sentence);
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

    public Sentence1 findSentenceById(int id)
    {
        return sentence1Repo.findById(id).get();
    }
    public Sentence1 findSentenceByWord(String word){
        return  sentence1Repo.findSentenceByWord(word);
    }
    public Boolean isExist(String word)
    {
        return  sentence1Repo.existsByWord(word);
    }
}
