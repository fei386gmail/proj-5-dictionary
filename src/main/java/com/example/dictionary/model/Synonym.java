package com.example.dictionary.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Data
@AllArgsConstructor
@Entity
@Table(name = "Synonyms")
public class Synonym {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(length=32,unique = false)
    private String word;
    @Column(length=512)
    private String synonym;
    @Column(length = 32)
    private String property;

    public Synonym() {

    }

    public Synonym(String word, String synonym, String property) {
        this.word = word;
        this.synonym = synonym;
        this.property = property;
    }


}
