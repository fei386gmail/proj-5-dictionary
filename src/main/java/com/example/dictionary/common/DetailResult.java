package com.example.dictionary.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class DetailResult {
    public DetailResult() {
    }

    private String word;
    private String translation;
    private String synonym;
    private String antonym;
    private String collocation;

}
