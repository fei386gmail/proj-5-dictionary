package com.example.dictionary.orm;

import com.example.dictionary.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.dictionary.orm.WordRepo;
@Service
public class WordServ {
    @Autowired
    private WordRepo wordRepo;

    public Page<Word> findAll(Pageable pageable){
        return wordRepo.findAll(pageable);
    }

    public Word findOneById(String id) {
        Optional<Word> o= wordRepo.findById(id);
        return o.get();
    }

    public List<Word> findWords(String id)
    {
        Word word=new Word();
        word.setWord(id);
        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())//模糊查询匹配开头，即{username}%
                .withMatcher("word" , ExampleMatcher.GenericPropertyMatchers.contains())//全部模糊查询，即%{address}%
                .withIgnorePaths("translation");
        Example<Word> example = Example.of(word ,matcher);
        List<Word> words=wordRepo.findAll(example);
        return  words;
    }

    public List<Word> findWordWithPrefix(String id)
    {
        Word word=new Word();
        word.setWord(id);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("word", ExampleMatcher.GenericPropertyMatchers.startsWith())//模糊查询匹配开头，即{username}%
//                .withMatcher("word" , ExampleMatcher.GenericPropertyMatchers.contains())//全部模糊查询，即%{address}%
                .withIgnorePaths("translation");
        Example<Word> example = Example.of(word ,matcher);
        List<Word> words=wordRepo.findAll(example);
        return  words;
    }

    public List<Word> findWordWithSuffix(String id)
    {
        Word word=new Word();
        word.setWord(id);
        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withMatcher("word", ExampleMatcher.GenericPropertyMatchers.startsWith())//模糊查询匹配开头，即{username}%
                .withMatcher("word",ExampleMatcher.GenericPropertyMatchers.endsWith())  //模糊查询匹配结尾，
//                .withMatcher("word" , ExampleMatcher.GenericPropertyMatchers.contains())//全部模糊查询，即%{address}%
                .withIgnorePaths("translation");
        Example<Word> example = Example.of(word ,matcher);
        List<Word> words=wordRepo.findAll(example);
        return  words;
    }

    public List<Word> findWordWithPrefixAndSuffix(String id)
    {
        String[] ss=id.split("\\*");

        Word word1=new Word();
        Word word2=new Word();

        word1.setWord(ss[0]);
        ExampleMatcher matcher1 = ExampleMatcher.matching()
                .withMatcher("word", ExampleMatcher.GenericPropertyMatchers.startsWith())//模糊查询匹配开头，即{username}%
//                .withMatcher("word",ExampleMatcher.GenericPropertyMatchers.endsWith())  //模糊查询匹配结尾，
//                .withMatcher("word" , ExampleMatcher.GenericPropertyMatchers.contains())//全部模糊查询，即%{address}%
                .withIgnorePaths("translation");
        Example<Word> example1 = Example.of(word1 ,matcher1);
        List<Word> words1=wordRepo.findAll(example1);

        word2.setWord(ss[1]);
        ExampleMatcher matcher2 = ExampleMatcher.matching()
//                .withMatcher("word", ExampleMatcher.GenericPropertyMatchers.startsWith())//模糊查询匹配开头，即{username}%
                .withMatcher("word",ExampleMatcher.GenericPropertyMatchers.endsWith())  //模糊查询匹配结尾，
//                .withMatcher("word" , ExampleMatcher.GenericPropertyMatchers.contains())//全部模糊查询，即%{address}%
                .withIgnorePaths("translation");
        Example<Word> example2 = Example.of(word2 ,matcher2);
        List<Word> words2=wordRepo.findAll(example2);

        List<Word> results=new ArrayList<>();
        for (Word w1:words1
             ) {
           if(words2.contains(w1))
           {
               results.add(w1);
           }
        }

        return  results;
    }

    public List<Word> findWordWithTranslation(String id)
    {
        Word word=new Word();
        word.setTranslation(id);
        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withMatcher("word", ExampleMatcher.GenericPropertyMatchers.startsWith())//模糊查询匹配开头，即{username}%
//                .withMatcher("translation",ExampleMatcher.GenericPropertyMatchers.endsWith())  //模糊查询匹配结尾，
                .withMatcher("translation" , ExampleMatcher.GenericPropertyMatchers.contains())//全部模糊查询，即%{address}%
                .withIgnorePaths("word");
        Example<Word> example = Example.of(word ,matcher);
        List<Word> words=wordRepo.findAll(example);
        return  words;
    }


}
