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

    public List<Word> findTop500ByWordContainingOrderByTranslationDesc(String id);

    public List<Word> findTop500ByWordStartingWithOrderByTranslationDesc(String id);
    public List<Word> findTop500ByWordEndingWithOrderByTranslationDesc(String id);
    public List<Word> findTop500ByTranslationContaining(String id);
    public List<Word> findTop500ByWordLike(String id);
    public List<Word> findTop500ByWordIsStartingWithAndWordIsEndingWith(String start,String end);
}
