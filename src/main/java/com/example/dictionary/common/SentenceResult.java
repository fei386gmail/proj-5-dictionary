package com.example.dictionary.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class SentenceResult {
    int id;
    String word;
    private String sentence_english;
    private String sentence_chinese;

    public SentenceResult() {
    }
}
