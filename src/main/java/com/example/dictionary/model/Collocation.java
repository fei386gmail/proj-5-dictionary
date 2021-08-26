package com.example.dictionary.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Data
@AllArgsConstructor
@Entity
@Table(name = "Collocations")
public class Collocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(length=32,unique = false)
    private String word;
    @Column(length=512)
    private String Collocation;
    @Column(length = 32)
    private String property;

    public Collocation() {

    }

    public Collocation(String word, String collocation, String property) {
        this.word = word;
        Collocation = collocation;
        this.property = property;
    }
}
