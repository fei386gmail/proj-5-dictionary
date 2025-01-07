package com.example.dictionary.orm;


import com.example.dictionary.model.EnWords_bak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnWords_repo extends JpaRepository<EnWords_bak,String> {

    EnWords_bak getTopByWordIs(String word);

    @Query(nativeQuery = true,value = "select translation from EnWords_bak where word=:word ")
    String getTranByWord(@Param("word") String word);
}
