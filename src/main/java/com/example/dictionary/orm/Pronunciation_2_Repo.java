package com.example.dictionary.orm;

import com.example.dictionary.model.Pronunciation_US_2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Pronunciation_2_Repo extends JpaRepository<Pronunciation_US_2,Integer> {

    public Pronunciation_US_2 findByWord(String word);
    public Boolean existsByWord(String word);
}
