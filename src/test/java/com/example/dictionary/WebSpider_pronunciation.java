package com.example.dictionary;

import com.example.dictionary.model.Pronunciation_US;
import com.example.dictionary.model.Word;
import com.example.dictionary.orm.Pronunciation_USServ;
import com.example.dictionary.orm.WordServ;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.io.IOUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class WebSpider_pronunciation {
    @Autowired
    private WordServ wordServ;
    @Autowired
    private Pronunciation_USServ pronunciation_usServ;

    private WebDriver webDriver;
    @Test
    public void test() throws IOException {
        //参数
        int startPage=0;
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

                List<WebElement> divs= webDriver.findElements(new By.ByXPath("//div[@class='b_primtxt']"));
                for (WebElement t: divs
                ) {
                    if(t.getText().contains("美"))
                    {
                        t.click();
                        savePronunciation_US(w.getWord());

                    }
                }

            }
        }


    }

    public void savePronunciation_US(String word) throws IOException {
        webDriver.get("https://cn.bing.com/dict/search?q="+word+"&FORM=HDRSC6");
        List<WebElement> divs= webDriver.findElements(new By.ByXPath("//div[@class='hd_prUS b_primtxt']"));
        for (WebElement t: divs
        ) {
            if(t.getText().contains("美"))
            {
                List<WebElement> as=webDriver.findElements(new By.ByXPath("//a[@title='点击朗读']"));

                String att= as.get(0).getAttribute("onclick");
                System.out.println(att);
                String urlString=getLinksFromString(att).get(0);
                URL url=new URL(urlString);
                URLConnection conn = url.openConnection();
                InputStream inputStream = conn.getInputStream();
                byte[] pronun=IOUtils.toByteArray(inputStream);
                Pronunciation_US pronunciation_us=new Pronunciation_US(word,pronun);
                pronunciation_usServ.save(pronunciation_us);

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
