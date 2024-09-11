package com.example.dictionary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Data
@AllArgsConstructor
@Entity
@Table
public class Sentence1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String word;
    @Column
    private String sentence_english;
    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column( columnDefinition="mediumblob")
    byte[] pronunciation;
    @Column
    private String sentence_chinese;


    public Sentence1() {
    }

    public Sentence1(String word, String sentence_english, byte[] pronunciation, String sentence_chinese) {
        this.word = word;
        this.sentence_english = sentence_english;
        this.pronunciation = pronunciation;
        this.sentence_chinese = sentence_chinese;
    }
}
