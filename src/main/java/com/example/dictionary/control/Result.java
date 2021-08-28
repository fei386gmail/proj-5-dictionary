package com.example.dictionary.control;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class Result {
    public Result() {
    }

    private String word;
    private String translation;
    private String synonym;
    private String antonym;
    private String collocation;
}
