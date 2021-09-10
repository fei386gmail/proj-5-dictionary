package com.example.dictionary.orm;

import com.example.dictionary.model.Pronunciation_US_Bing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Pronunciation_USRepo extends JpaRepository<Pronunciation_US_Bing,Integer> {

    Pronunciation_US_Bing findByWord(String word);


}
