package com.example.dictionary;

import com.example.dictionary.model.Antonym;
import com.example.dictionary.model.Collocation;
import com.example.dictionary.model.Synonym;
import com.example.dictionary.model.Word;
import com.example.dictionary.orm.AntonymServ;
import com.example.dictionary.orm.CollocationServ;
import com.example.dictionary.orm.SynonymServ;
import com.example.dictionary.orm.WordServ;
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

import java.util.List;

@SpringBootTest
public class WebSpider {

    @Autowired
     private SynonymServ synonymServ;
    @Autowired
    private WordServ wordServ;
    @Autowired
    private AntonymServ antonymServ;
    @Autowired
    private CollocationServ collocationServ;

    private WebDriver webDriver;

    @Test
    public void searchWord()
    {
        //参数
        String targetWord="amish";

        //开始
        int countPage=0;
        boolean breakFlag=false;

        PageRequest pageRequest=PageRequest.of(0,100);
        Page<Word> page=wordServ.findAll(pageRequest);
        long totalPages= page.getTotalPages();
        System.out.println("totalPages"+totalPages);
        for (int j=0;j<totalPages;j++)
        {
            System.out.println("Page Number:"+j);
            PageRequest pageRequest1=PageRequest.of(j,100);
            Page<Word> page1=wordServ.findAll(pageRequest1);
            List<Word> words=page1.getContent();

            for (Word word : words) {
                if (word.getWord().equals(targetWord)) {
                    countPage = j;
                    breakFlag = true;
                    break;
                }
            }
            if (breakFlag) {
                break;
            }
        }

        System.out.println("targetPage:"+countPage);
    }

    @Test
    public void searchAndSave()
    {
        //参数
        int startPage=30;
        int wordsPerPage=100;

        //开始
        System.setProperty("webdriver.chrome.driver","/Users/chenfei/OneDrive/IDEAProject/proj 5 dictionary/lib/chromedriver");
        webDriver=new ChromeDriver();
        PageRequest pageRequest=PageRequest.of(0,wordsPerPage);
        Page<Word> page=wordServ.findAll(pageRequest);
        long totalPages= page.getTotalPages();

        for(int j=startPage;j<totalPages;j++)
        {
            PageRequest pageRequest1=PageRequest.of(j,wordsPerPage);
            Page<Word> page1=wordServ.findAll(pageRequest1);
            List<Word> words=page1.getContent();
            for(int i=0;i<words.size();i++)
            {
                Word w=words.get(i);
                webDriver.get("https://cn.bing.com/dict/search?q="+w.getWord()+"&FORM=HDRSC6");

                List<WebElement> h2s= webDriver.findElements(new By.ByXPath("//h2[@class='b_primtxt']"));
                for (WebElement t: h2s
                     ) {
                    switch (t.getText()){
                        case "同义词": {
                            t.click();
                            saveSynonyms(w.getWord());
                            break;
                        }
                        case "反义词": {
                            t.click();
                            saveAntonyms(w.getWord());
                            break;
                        }
                        case "搭配":{
                            t.click();
                            saveCollocations(w.getWord());
                            break;
                        }
                        default:{
                            break;
                        }
                    }
                }

            }
        }


    }

    public void saveSynonyms(String word)
    {

        WebElement synonym=webDriver.findElement(new By.ById("synoid"));
        if(synonym.isDisplayed()){
//            System.out.println("synonym:"+synonym.getLocation());
        }

        List<WebElement> propertiesWebElements=synonym.findElements(new By.ByClassName("df_div2"));

//        System.out.println("几种同义词："+propertiesWebElements.size());
        for (WebElement propertyWebElement: propertiesWebElements
        ) {
            WebElement property=propertyWebElement.findElement(new By.ByClassName("b_dictHighlight"));
            saveSynonym(word,propertyWebElement,property.getText());

        }

    }

    public   void saveSynonym(String word,WebElement propertyWebElement,String property)
    {
        List<WebElement> spans= propertyWebElement.findElements(new By.ByClassName("b_alink"));
//        System.out.println("几个词："+spans.size());
        for (WebElement syn: spans
             ) {
            if(syn.getText().equals(" ") ||syn.getText().equals("") ||syn.getText().equals("   ")){}
            else
            {
//                System.out.println(syn.getText());
                Synonym synonym=new Synonym( word,syn.getText(),property);
                synonymServ.newSynonym(synonym);
             }
        }
    }

    public void saveAntonyms(String word)
    {

        WebElement synonym=webDriver.findElement(new By.ById("antoid"));
        if(synonym.isDisplayed()){
//            System.out.println("antonym:"+synonym.getLocation());
        }

        List<WebElement> propertiesWebElements=synonym.findElements(new By.ByClassName("df_div2"));

//        System.out.println("几种反义词："+propertiesWebElements.size());
        for (WebElement propertyWebElement: propertiesWebElements
        ) {
            WebElement property=propertyWebElement.findElement(new By.ByClassName("b_dictHighlight"));
            saveAntonym(word,propertyWebElement,property.getText());

        }
    }
    public   void saveAntonym(String word,WebElement propertyWebElement,String property)
    {
        List<WebElement> spans= propertyWebElement.findElements(new By.ByClassName("b_alink"));
//        System.out.println("几个词："+spans.size());
        for (WebElement syn: spans
        ) {
            if(syn.getText().equals(" ") ||syn.getText().equals("") ||syn.getText().equals("   ")){}
            else
            {
//                System.out.println(syn.getText());
                Antonym antonym=new Antonym(word,syn.getText(),property);
                antonymServ.newAntonym(antonym);
            }
        }
    }

    public void saveCollocations(String word)
    {

        WebElement synonym=webDriver.findElement(new By.ById("colid"));
        if(synonym.isDisplayed()){
//            System.out.println("collocations:"+synonym.getLocation());
        }

        List<WebElement> propertiesWebElements=synonym.findElements(new By.ByClassName("df_div2"));

//        System.out.println("几种搭配："+propertiesWebElements.size());
        for (WebElement propertyWebElement: propertiesWebElements
        ) {
            WebElement property=propertyWebElement.findElement(new By.ByClassName("b_dictHighlight"));
            saveCollocation(word,propertyWebElement,property.getText());

        }
    }
    public   void saveCollocation(String word,WebElement propertyWebElement,String property)
    {
        List<WebElement> spans= propertyWebElement.findElements(new By.ByClassName("b_alink"));
//        System.out.println("几个词："+spans.size());
        for (WebElement syn: spans
        ) {
            if(syn.getText().equals(" ") ||syn.getText().equals("") ||syn.getText().equals("   ")){}
            else
            {
//                System.out.println(syn.getText());
                Collocation collocation=new Collocation(word,syn.getText(),property);
                collocationServ.newCollocation(collocation);
            }
        }
    }
}
