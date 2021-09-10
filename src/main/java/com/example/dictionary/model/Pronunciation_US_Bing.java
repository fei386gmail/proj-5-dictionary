package com.example.dictionary.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity(name = "pronunciation_us_bing")
@Component
@Data
public class Pronunciation_US_Bing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(length = 32,unique = true)
    private String word;
    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column( columnDefinition="BLOB")
    byte[] pronunciation;

    public Pronunciation_US_Bing(String word, byte[] pronunciation) {
        this.word = word;
        this.pronunciation = pronunciation;
    }

    public Pronunciation_US_Bing() {

    }
}
