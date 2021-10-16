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
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String word;
    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column( columnDefinition="mediumblob")
    byte[] picture;

    public Picture() {

    }

    public Picture(String word, byte[] picture) {
        this.word = word;
        this.picture = picture;
    }
}
