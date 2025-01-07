package com.example.dictionary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Component
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EnWords_bak")
public class EnWords_bak {
    @Id
    @Column(length=32,unique = true,name = "word")
    private String word;
    @Column(length=512,name = "translation")
    private String translation;
}
