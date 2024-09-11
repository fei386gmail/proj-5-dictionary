package com.example.dictionary.orm;

import com.example.dictionary.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepo extends JpaRepository<Picture,Integer> {
    public Picture findByWord(String word);
}
