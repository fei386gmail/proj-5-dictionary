package com.example.dictionary.model;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Data
@Entity
@Table(name = "Synonyms")
public class Synonym {
    @Id
    private String word;
    @Column
    private String synonym;

}
