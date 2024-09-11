package com.example.dictionary.orm;

import com.example.dictionary.model.Frequency;
import com.example.dictionary.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FrequencyServ {
    @Autowired
    private FrequencyRepo frequencyRepo;

    public Frequency findByWord(String word)
    {
        return frequencyRepo.findByWord(word);
    }

    public int getFrequency(String word)
    {
        Frequency s = frequencyRepo.findByWord(word);
        if(s==null)
        {
            return 4;
        }
        int id=s.getId();
        if(id>=0     && id<10000) return 1;
        if(id>=10000 && id<20000) return 2;
        if(id>=20000 && id<30000) return 3;
        else return 4;
    }
    public int getTotalNumber()
    {
       return (int) frequencyRepo.count();
    }

    public Frequency getByNumber(int l)
    {
        Optional<Frequency> o= frequencyRepo.findById(l);
        Frequency f;
        try {
             f=o.get();
        }
        catch (NoSuchElementException e)
        {
            return  null;
        }
        return f;
    }
    public List<Frequency> findFrequencyLike(String id)
    {
        List<Frequency> frequencyList=  frequencyRepo.findAllByWordContains(id);
        List<Frequency> frequencyNew=  new ArrayList<>();
        for (Frequency f: frequencyList
             ) {
            if( getFrequency(f.getWord())<=2) frequencyNew.add(f);
        }
        return  frequencyNew;
    }

    public List<Frequency> findFrenquencyWithPrefix(String id)
    {
        List<Frequency> frequencyList= frequencyRepo.findAllByWordStartingWith(id);
        List<Frequency> frequencyNew=  new ArrayList<>();
        for (Frequency f: frequencyList
        ) {
            if( getFrequency(f.getWord())<=2) frequencyNew.add(f);
        }
        return  frequencyNew;
    }

    public List<Frequency> findFrenquencyWithSuffix(String id)
    {
        List<Frequency> frequencyList=  frequencyRepo.findAllByWordEndingWith(id);
        List<Frequency> frequencyNew=  new ArrayList<>();
        for (Frequency f: frequencyList
        ) {
            if( getFrequency(f.getWord())<=2) frequencyNew.add(f);
        }
        return  frequencyNew;
    }

    public List<Frequency> findFrenquencyWithPrefixAndSuffix(String id)
    {
        String[] ss=id.split("\\*");

        Frequency frequency1=new Frequency();
        Frequency frequency2=new Frequency();

        frequency1.setWord(ss[0]);
        List<Frequency> words1=frequencyRepo.findAllByWordStartingWith(frequency1.getWord());

        frequency2.setWord(ss[1]);
        List<Frequency> words2=frequencyRepo.findAllByWordEndingWith(frequency2.getWord());

        List<Frequency> results=new ArrayList<>();
        for (Frequency w1:words1
        ) {
            if(words2.contains(w1))
            {
                results.add(w1);
            }
        }
        List<Frequency> frequencyNew=  new ArrayList<>();
        for (Frequency f: results
        ) {
            if( getFrequency(f.getWord())<=2) frequencyNew.add(f);
        }
        return  frequencyNew;
    }

    public List<Word> getWordFromFrequency(List<Word> words)
    {
        List<Word> results=new ArrayList<>();
        for (Word w: words
             ) {
            if(frequencyRepo.existsByWord(w.getWord())  && frequencyRepo.findByWord(w.getWord()).getId()<=20000)
            {
                    results.add(w);
            }
        }
        return results;
    }

}
