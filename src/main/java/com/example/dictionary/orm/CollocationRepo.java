package com.example.dictionary.orm;


import com.example.dictionary.model.Collocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollocationRepo extends JpaRepository<Collocation,Integer> {
}
