package com.example.dictionary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Data
@AllArgsConstructor
@Entity
@Table(name = "Antonyms")
public class Antonym {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(length=32,unique = false)
    private String word;
    @Column(length=512)
    private String antonym;
    @Column(length = 32)
    private String property;

    public Antonym() {

    }

    public Antonym(String word, String antonym, String property) {
        this.word = word;
        this.antonym = antonym;
        this.property = property;
    }
}
