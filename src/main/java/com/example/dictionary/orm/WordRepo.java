package com.example.dictionary.orm;


import com.example.dictionary.model.Word;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WordRepo extends JpaRepository<Word,String> {

    public List<Word> findAllByWordContainsOrderByTranslationDesc(String id);
    public List<Word> findAllByWordStartingWithOrderByTranslationDesc(String id);
    public List<Word> findAllByWordEndingWithOrderByTranslationDesc(String id);
    public List<Word> findAllByTranslationContaining(String id);
    public List<Word> findAllByRememberIsTrue();
}
