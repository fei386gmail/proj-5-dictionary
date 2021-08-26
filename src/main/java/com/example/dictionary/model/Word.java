package com.example.dictionary.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Data
@Entity
@Table(name = "Enwords")
public class Word {
    @Id
    @Column(length=32,unique = true)
    private String word;
    @Column(length=512)
    private String translation;
}


