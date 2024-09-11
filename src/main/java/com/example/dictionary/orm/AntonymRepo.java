package com.example.dictionary.orm;

import com.example.dictionary.model.Antonym;
import com.example.dictionary.model.Synonym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AntonymRepo extends JpaRepository<Antonym,Integer> {
    @Query(value = "select distinct s.property from antonyms s where s.word=?1",nativeQuery = true)
    public List<String> getPropertiesByWord(String word);

    public List<Antonym> findAllByWord(String word);
    public Boolean existsByWord(String word);
}
