package com.example.dictionary;

import com.example.dictionary.model.Word;
import com.example.dictionary.orm.AntonymServ;
import com.example.dictionary.orm.CollocationServ;
import com.example.dictionary.orm.SynonymServ;
import com.example.dictionary.orm.WordServ;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest
public class Webspider_translation {
    @Autowired
    private SynonymServ synonymServ;
    @Autowired
    private WordServ wordServ;
    @Autowired
    private AntonymServ antonymServ;
    @Autowired
    private CollocationServ collocationServ;

    private WebDriver webDriver;
    ;
    //参数
    int startPage=0;
    int wordsPerPage=100;
    Webspider_translation()
    {
        //开始
        System.setProperty("webdriver.chrome.driver","/Users/chenfei/Documents/GitHub/proj-5-dictionary/lib/chromedriver131");
        webDriver=new ChromeDriver();
    }
    @Test
    public void searchNullTranslation() throws Exception
    {

        PageRequest pageRequest=PageRequest.of(0,wordsPerPage);
        Page<Word> page=wordServ.findAll(pageRequest);
        long totalPages= page.getTotalPages();
        System.out.println("totalPages"+totalPages);

        for(int i=startPage;i<totalPages;i++)
        {
            PageRequest pageRequesti=PageRequest.of(i,wordsPerPage);
            System.out.println("Page:"+i);
            Page<Word> pagei=wordServ.findAll(pageRequesti);
            List<Word> words=pagei.getContent();
            for (Word w:words)
            {
                if(w.getTranslation()==null || w.getTranslation().equals(".") || w.getTranslation().equals(""))
                {
                    System.out.println(w);
                    spideTranslation(w.getWord());
                    Thread.sleep(100);
                }
            }
        }
    }



    public String spideTranslation(String word)
    {

        webDriver.get("http://www.youdao.com/w/eng/"+word+"/#keyfrom=dict2.index");
        List<WebElement> transcontainer;
        try{
             transcontainer=webDriver.findElements(new By.ByClassName("trans-container"));
        }
        catch (Exception e)
        {
            return null;
        }
        if(transcontainer==null || transcontainer.size()==0) return null;
        List<WebElement> pos=transcontainer.get(0).findElements(new By.ByTagName("li"));
        String translation="";
        for (WebElement w: pos
             ) {
            translation=translation.concat(w.getText());
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return translation;
     }

    @Test
    public void getTranslationFromEnWords_bak()
    {
        List<Word> wordsNotran=wordServ.findWordWithSpecTranslation(null);
        for (Word w: wordsNotran
        ) {
            String tran= null;
            tran = spideTranslation(w.getWord());
            w.setTranslation(tran);
            wordServ.save(w);
            System.out.println(w.toString());
        }

    }




}
