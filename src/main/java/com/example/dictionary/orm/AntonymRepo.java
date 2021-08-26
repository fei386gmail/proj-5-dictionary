package com.example.dictionary.orm;

import com.example.dictionary.model.Antonym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AntonymRepo extends JpaRepository<Antonym,Integer> {
}
