package com.example.dictionary.orm;

import com.example.dictionary.model.Frequency;
import com.example.dictionary.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Word> findWordsContains(String id)
    {
        id=id.replace("*","");
        List<Word> words=wordRepo.findTop500ByWordContainingOrderByTranslationDesc(id);
        if(words==null || words.size()==0) return null;
        return words;
    }


    public List<Word> findWordWithPrefix(String id)
    {

        return  wordRepo.findTop500ByWordStartingWithOrderByTranslationDesc(id);
    }

    public List<Word> findWordWithSuffix(String id)
    {

        return  wordRepo.findTop500ByWordEndingWithOrderByTranslationDesc(id);
    }

    public List<Word> findWordsLike(String id){
        List<Word> words=wordRepo.findTop500ByWordLike(id);
        if(words==null || words.size()==0)
        {
            return null;
        }
        return words;
    }

    public List<Word> findWordWithPrefixAndSuffix(String id)
    {
        String[] ss=id.split("\\*");

        Word word1=new Word();
        Word word2=new Word();

        word1.setWord(ss[0]);
        List<Word> words1=wordRepo.findTop500ByWordStartingWithOrderByTranslationDesc(word1.getWord());

        word2.setWord(ss[1]);
        List<Word> words2=wordRepo.findTop500ByWordEndingWithOrderByTranslationDesc(word2.getWord());

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
        return  wordRepo.findTop500ByTranslationContaining(id);
    }
    public List<Word> findWordWithSpecTranslation(String trans)
    {
//
        return  wordRepo.findWordsByTranslationIs(trans);
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

    public List<Word> findWithStartAndEnd(String id)
    {
        String[] ss=id.split("\\*");
        String start=ss[0];
        int last=ss.length-1;
        String end=ss[last];
        List<Word> results=wordRepo.findTop500ByWordIsStartingWithAndWordIsEndingWith(start,end);
        if(results==null || results.size()==0) return  null;
        return results;
    }
}
