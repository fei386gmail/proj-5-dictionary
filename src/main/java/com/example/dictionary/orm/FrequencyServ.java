package com.example.dictionary.orm;

import com.example.dictionary.model.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
