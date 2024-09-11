package com.example.dictionary.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordResult {

    private String word;
    private String translation;
    private boolean pronunciation1;
    private boolean pronunciation2;
    private int frequency;
    private boolean remember;

}
