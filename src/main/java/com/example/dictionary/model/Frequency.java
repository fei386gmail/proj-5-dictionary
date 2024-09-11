package com.example.dictionary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Data
@AllArgsConstructor
public class Frequency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(length=32,unique = true)
    private String word;
    @Column(length = 5)
    private String PoS;
    @Column
    private int total;
    @Column
    private int spoken;
    @Column
    private int fiction;
    @Column
    private int magazine;
    @Column
    private int newspaper;
    @Column
    private int academic;

    public Frequency() {

    }

}
