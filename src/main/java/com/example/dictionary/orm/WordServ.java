package com.example.dictionary.orm;

import com.example.dictionary.model.Frequency;
import com.example.dictionary.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

import com.example.dictionary.orm.WordRepo;
@Service
public class WordServ {
    @Autowired
    private WordRepo wordRepo;
    @Autowired
    private FrequencyServ frequencyServ;

    public Page<Word> findAll(Pageable pageable){
        return wordRepo.findAll(pageable);
    }

    public Word findOneById(String id) {
        Optional<Word> o= wordRepo.findById(id);
        if(o.isPresent())
        return o.get();
        else
            return null;

    }
    public int getTotalNumber()
    {
        return (int) wordRepo.count();
    }
    public List<Word> findWords(String id)
    {
        return wordRepo.findAllByWordContainsOrderByTranslationDesc(id);
    }

    public List<Word> findWordWithPrefix(String id)
    {

        return  wordRepo.findAllByWordStartingWithOrderByTranslationDesc(id);
    }

    public List<Word> findWordWithSuffix(String id)
    {

        return  wordRepo.findAllByWordEndingWithOrderByTranslationDesc(id);
    }

    public List<Word> findWordWithPrefixAndSuffix(String id)
    {
        String[] ss=id.split("\\*");

        Word word1=new Word();
        Word word2=new Word();

        word1.setWord(ss[0]);
        List<Word> words1=wordRepo.findAllByWordStartingWithOrderByTranslationDesc(word1.getWord());

        word2.setWord(ss[1]);
        List<Word> words2=wordRepo.findAllByWordEndingWithOrderByTranslationDesc(word2.getWord());

        List<Word> results=new ArrayList<>();
        for (Word w1:words1
             ) {
           if(words2.contains(w1))
           {
               results.add(w1);
           }
        }

        return  results;
    }

    public List<Word> findWordWithTranslation(String id)
    {
//
        return  wordRepo.findAllByTranslationContaining(id);
    }

    public Boolean isExist(String w)
    {
        Word word=new Word();
        word.setWord(w);
        Example<Word> example=Example.of (word);
        return  wordRepo.exists(example);
    }

    public void  save(Word word)
    {
        wordRepo.save(word);
    }

    public List<Word> findWordsFromFrequencies(List<Frequency> frequencies)
    {
        List<Word> wordList=new ArrayList<>();
        for (Frequency f: frequencies
             ) {
            System.out.println(f.getWord());
            Optional<Word> optionalWord= wordRepo.findById(f.getWord());
            if(optionalWord.isPresent())
            {
                wordList.add(optionalWord.get());
            }
        }
        return wordList;
    }
    public boolean isRemember(String word)
    {
        Optional<Word> w=  wordRepo.findById(word);
        if(w.isPresent())
        {
            return w.get().getRemember();
        }
        else return false;
    }
    public List<Word> getRecallWords()
    {
        List<Word> results= wordRepo.findAllByRememberIsTrue();
        Collections.shuffle(results);
        return results;
    }
}
