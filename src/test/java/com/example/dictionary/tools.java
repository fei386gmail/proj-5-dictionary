package com.example.dictionary;

import com.example.dictionary.model.*;
import com.example.dictionary.orm.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class tools {

    @Autowired
    private Pronunciation_US_1_Serv pronunciation_1Serv;
    @Autowired
    private WordServ wordServ;
    @Autowired
    private SynonymServ synonymServ;
    @Autowired
    private AntonymServ antonymServ;
    @Autowired
    private CollocationServ collocationServ;
    @Autowired
    private FrequencyServ frequencyServ;

    private WebDriver webDriver;

    @Test
    void CleanReplication() {
        //参数
        int startPage=0;
        int wordsPerPage=100;

        //查询分页数量
        PageRequest pageRequest=PageRequest.of(0,wordsPerPage);
        Page<Word> page=wordServ.findAll(pageRequest);
        long totalPages= page.getTotalPages();

        //循环整个词库
        for(int j=startPage;j<totalPages;j++)
        {
            PageRequest pageRequest1=PageRequest.of(j,wordsPerPage);
            Page<Word> page1=wordServ.findAll(pageRequest1);
            List<Word> words=page1.getContent();

            for(int i=0;i<words.size();i++)
            {
                // 取每个词的同义词，并对队列去重
                List<Synonym> synonymList=synonymServ.findAllSynonymClass(words.get(i).getWord());
                if(synonymList.size()>=2){
                    for (int k=0;k<synonymList.size();k++
                    ) {
                        Synonym a =synonymList.get(k);
                        for(int kk=k+1;kk<synonymList.size();kk++)
                        {
                            Synonym b=synonymList.get(kk);
                            if(synonymServ.compare(a,b))
                            {
                                synonymServ.delete(b);
                            }
                        }
                    }
                }
                //取每个词的反义词，去重
                List<Antonym> antonymList=antonymServ.findAllAntonyms(words.get(i).getWord());
                if(antonymList.size()>=2){
                    for (int k=0;k<antonymList.size();k++
                    ) {
                        Antonym a =antonymList.get(k);
                        for(int kk=k+1;kk<antonymList.size();kk++)
                        {
                            Antonym b=antonymList.get(kk);
                            if(antonymServ.compare(a,b))
                            {
                                antonymServ.delete(b);
                            }
                        }
                    }
                }
                // 取每个词的搭配，去重
                List<Collocation> collocationList=collocationServ.findAllCollations(words.get(i).getWord());
                if(collocationList.size()>=2){
                    for (int k=0;k<collocationList.size();k++
                    ) {
                        Collocation a =collocationList.get(k);
                        for(int kk=k+1;kk<collocationList.size();kk++)
                        {
                            Collocation b=collocationList.get(kk);
                            if(collocationServ.compare(a,b))
                            {
                                collocationServ.delete(b);
                            }
                        }
                    }
                }

            }
        }
    }
    @Test
    public void addFrequence2Word()   // 有些单词在words表里没有，在frequency表有，解决问题
    {
        int totalNumber= (int) frequencyServ.getTotalNumber();
        System.out.println("total of frequency:"+totalNumber);
        System.out.println("total of words:"+wordServ.getTotalNumber());

        for(int i=1;i<=totalNumber;i++)
        {
            Frequency f = frequencyServ.getByNumber(i);
            if(f==null) continue;

            String word=f.getWord();
            System.out.println(word);
            if(wordServ.isExist(word))
            {
                continue;
            }
            else {
                Word w=new Word();
                w.setWord(word);
                w.setTranslation("");
                wordServ.save(w);
                System.out.println("insert : "+w.getWord()+ "    -------------------------");
            }
        }
    }

}
