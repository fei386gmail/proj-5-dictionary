package com.example.dictionary.orm;

import com.example.dictionary.model.Pronunciation_US_1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Pronunciation_US_1_Repo extends JpaRepository<Pronunciation_US_1,Integer> {

    Pronunciation_US_1 findByWord(String word);
    public Boolean existsByWord(String word);

}
