package com.example.dictionary.orm;

import com.example.dictionary.model.Collocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollocationServ {
    @Autowired
    private CollocationRepo collocationRepo;

    public void newCollocation(Collocation collocation){
        collocationRepo.save(collocation);
    }
}
