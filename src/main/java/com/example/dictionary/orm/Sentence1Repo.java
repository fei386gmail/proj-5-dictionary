package com.example.dictionary.orm;

import com.example.dictionary.model.Sentence1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sentence1Repo extends JpaRepository<Sentence1,Integer> {
    public Sentence1 findSentenceByWord(String word);
    public List<Sentence1> findAllByWord(String word);
    public Boolean existsByWord(String word);
}
