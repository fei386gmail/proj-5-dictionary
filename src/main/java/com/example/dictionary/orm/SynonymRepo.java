package com.example.dictionary.orm;

import com.example.dictionary.model.Synonym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SynonymRepo extends JpaRepository<Synonym,Integer> {

    @Query(value = "select distinct s.property from synonyms s where s.word=?1",nativeQuery = true)
    public List<String> getPropertiesByWord(String word);

    public List<Synonym> findAllByWord(String word);
    public Boolean existsByWord(String word);
}
