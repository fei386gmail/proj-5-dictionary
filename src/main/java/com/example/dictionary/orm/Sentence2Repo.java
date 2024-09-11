package com.example.dictionary.orm;

import com.example.dictionary.model.Sentence1;
import com.example.dictionary.model.Sentence2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sentence2Repo extends JpaRepository<Sentence2,Integer> {
    public Sentence2 findSentenceByWord(String word);
    public List<Sentence2> findAllByWord(String word);
}
