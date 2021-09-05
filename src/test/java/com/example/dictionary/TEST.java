package com.example.dictionary;

import com.example.dictionary.common.TablePageable;
import com.example.dictionary.model.Synonym;
import com.example.dictionary.model.Word;
import com.example.dictionary.orm.Pronunciation_USServ;
import com.example.dictionary.orm.SynonymServ;
import com.example.dictionary.orm.WordServ;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
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
class TEST {

    @Autowired
    private Pronunciation_USServ pronunciation_usServ;
    @Autowired
    private WordServ wordServ;

    private WebDriver webDriver;

    @Test
    void contextLoads() {
        System.setProperty("webdriver.chrome.driver","/Users/chenfei/OneDrive/IDEAProject/proj 5 dictionary/lib/chromedriver");

        webDriver=new ChromeDriver();
        webDriver.get("https://cn.bing.com/dict/search?q=reverse&FORM=HDRSC6");
        List<WebElement> divs= webDriver.findElements(new By.ByXPath("//div[@class='hd_prUS b_primtxt']"));
        for (WebElement t: divs
             ) {
            if(t.getText().contains("美"))
            {
                List<WebElement> as=webDriver.findElements(new By.ByXPath("//a[@title='点击朗读']"));

                String att= as.get(0).getAttribute("onclick");
                System.out.println(att);
                String url=getLinksFromString(att).get(0);
                System.out.println(url);


            }
        }

    }
    //Pull all links from the body for easy retrieval
    private ArrayList<String> getLinksFromString(String text) {
        ArrayList<String> links = new ArrayList();

        String regex ="\\(?\\b(https://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while(m.find()) {
            String urlStr = m.group();
            if (urlStr.startsWith("(") && urlStr.endsWith(")"))
            {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            links.add(urlStr);
        }
        return links;
    }
}
