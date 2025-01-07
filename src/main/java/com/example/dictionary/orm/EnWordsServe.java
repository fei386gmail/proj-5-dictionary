package com.example.dictionary.orm;

import com.example.dictionary.model.EnWords_bak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnWordsServe {
    @Autowired
    private EnWords_repo enWordsRepo;

    public EnWords_bak getEnWord(String word){
        EnWords_bak enWordsBak=enWordsRepo.getTopByWordIs(word);
        return enWordsBak;
    }
    public String getTranByWord(String word)
    {
        return enWordsRepo.getTranByWord(word);
    }
}
