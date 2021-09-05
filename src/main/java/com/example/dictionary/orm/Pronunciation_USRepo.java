package com.example.dictionary.orm;

import com.example.dictionary.model.Pronunciation_US;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Pronunciation_USRepo extends JpaRepository<Pronunciation_US,Integer> {

    Pronunciation_US findByWord(String word);
}
