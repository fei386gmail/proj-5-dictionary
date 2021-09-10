package com.example.dictionary.model;


import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Data
public class Pronunciation_US_2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(length = 32,unique = true)
    private String word;
    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column( columnDefinition="BLOB")
    byte[] pronunciation;

    public Pronunciation_US_2(String word, byte[] pronunciation) {
        this.word = word;
        this.pronunciation = pronunciation;
    }

    public Pronunciation_US_2() {

    }
}
