package com.example.dictionary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Words")
public class Word {
    @Id
    @Column(length=32,unique = true)
    private String word;
    @Column(length=512)
    private String translation;
    @Column
    private Boolean remember;

}


