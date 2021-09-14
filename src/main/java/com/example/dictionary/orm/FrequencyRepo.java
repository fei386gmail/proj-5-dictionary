package com.example.dictionary.orm;

import com.example.dictionary.model.Frequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrequencyRepo extends JpaRepository<Frequency,Integer> {

   public Frequency findByWord(String word);
}
