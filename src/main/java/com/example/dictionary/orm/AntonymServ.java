package com.example.dictionary.orm;

import com.example.dictionary.model.Antonym;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AntonymServ {
    @Autowired
    private AntonymRepo antonymRepo;

    public void newAntonym(Antonym antonym)
    {
        antonymRepo.save(antonym);
    }
}
