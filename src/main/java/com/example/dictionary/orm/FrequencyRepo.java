package com.example.dictionary.orm;

import com.example.dictionary.model.Frequency;
import com.example.dictionary.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrequencyRepo extends JpaRepository<Frequency,Integer> {

   public Frequency findByWord(String word);
   public List<Frequency> findByWordLike(String word);
   public List<Frequency> findTop500ByWordContains(String word);
   public List<Frequency> findTop500ByWordStartingWith(String word);
   public List<Frequency> findTop500ByWordEndingWith(String word);
   public Boolean existsByWord(String word);

   public List<Frequency> findTop500ByWordIsStartingWithAndWordIsEndingWith(String start,String end);
}
