package com.example.dictionary.orm;

import com.example.dictionary.model.Synonym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SynonymRepo extends JpaRepository<Synonym,String> {
}
