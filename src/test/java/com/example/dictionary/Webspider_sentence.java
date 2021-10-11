package com.example.dictionary;

import com.example.dictionary.model.Sentence1;
import com.example.dictionary.model.Sentence2;
import com.example.dictionary.model.Word;
import com.example.dictionary.orm.Sentence1Serv;
import com.example.dictionary.orm.Sentence2Serv;
import com.example.dictionary.orm.WordServ;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@SpringBootTest
public class Webspider_sentence {


    @Autowired
    private WordServ wordServ;
    @Autowired
    private Sentence1Serv sentence1Serv;
    @Autowired
    private Sentence2Serv sentence2Serv;
    private WebDriver webDriver;
    @Test
    public void test() throws InterruptedException, IOException {
        //参数
        int startPage=927;
        int wordsPerPage=100;

        //开始
        System.setProperty("webdriver.chrome.driver","/Users/chenfei/Documents/GitHub/proj-5-dictionary/lib/chromedriver94.0.4606.61");
        webDriver = new ChromeDriver();


        //查询分页数量
        PageRequest pageRequest=PageRequest.of(startPage,wordsPerPage);
        Page<Word> page=wordServ.findAll(pageRequest);
        long totalPages= page.getTotalPages();

        // 初始化页面
        webDriver.get("http://dict.youdao.com/w/eng/");
        Thread.sleep(1000);
        //循环整个词库
        for(int j=startPage;j<totalPages;j++) {
            PageRequest pageRequest1 = PageRequest.of(j, wordsPerPage);
            Page<Word> page1 = wordServ.findAll(pageRequest1);
            List<Word> words = page1.getContent();

            for (int i = 0; i < words.size(); i++) {
                Word w=words.get(i);
                Thread.sleep(100);
                findSentence(w.getWord());
            }
        }

    }
    public void findSentence(String word) throws InterruptedException, IOException {
        //找到输入框
        WebElement input;
        try {
             input=webDriver.findElement(new By.ById("query"));
        }
        catch (NoSuchElementException e)
        {
              input=  webDriver.findElement(new By.ById("translateContent"));
        }
        CharSequence ss=word;
        input.clear();
        input.sendKeys(ss);
        input.sendKeys(Keys.ENTER);
        Thread.sleep(100);
        WebElement bilingual;
        try {
             bilingual=webDriver.findElement(new By.ById("bilingual"));
        }
        catch (NoSuchElementException e)
        {
            return;
        }

        /*
        div id=bilingual
            ul
                li
                    p
                        span
                        span
                        ...
                    p
                        span
                        span
                        ...
                li
                    ...

         */
        //获取例句
        List<WebElement> lis=bilingual.findElements(new By.ByTagName("li"));

        if (lis.size() == 0 ) {
            return;
        }
        //保存第一个例句
        for (int i = 0; i < 2; i++) {
            WebElement li;
            try {
                li = lis.get(i);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
            //获取例句
            List<WebElement> ps = li.findElements(new By.ByTagName("p"));
            //获取英文句子
            String eng_sentence_words = "";
            for (WebElement w : ps.get(0).findElements(new By.ByTagName("span"))) {
                eng_sentence_words = eng_sentence_words.concat(w.getText()).concat(" ");
            }
            //获取英文语音
            WebElement prononciation=ps.get(0).findElement(new By.ByTagName("a"));
            byte[] pronunBytes=getPronunciation(prononciation);
            //获取汉语句子
            String chi_sentence_words = "";
            for (WebElement w : ps.get(1).findElements(new By.ByTagName("span"))) {
                chi_sentence_words = chi_sentence_words.concat(w.getText());
            }

            //保存

            try{
                if(i==0){
                    Sentence1 sentence1 = new Sentence1(word, eng_sentence_words,pronunBytes, chi_sentence_words);
                    sentence1Serv.save(sentence1);
                }
                else {
                    Sentence2 sentence2 = new Sentence2(word, eng_sentence_words,pronunBytes, chi_sentence_words);
                    sentence2Serv.save(sentence2);
                }
            }
            catch (Exception e)
            {
            }

        }
    }



    private byte[] getPronunciation(WebElement w) throws IOException {

        String c= w.getAttribute("data-rel");
        String urlString="http://dict.youdao.com/dictvoice?audio=".concat(c);
        URL url=new URL(urlString);
        URLConnection conn = url.openConnection();
        InputStream inputStream;

        while (true)
        {
            try {
                inputStream = conn.getInputStream();
                break;
            }
            catch (SocketException e)
            { e.printStackTrace(); }
            catch (TimeoutException e)
            { e.printStackTrace(); }
            catch (FileNotFoundException e )
            {e.printStackTrace();
                return null;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        byte[] pronun= IOUtils.toByteArray(inputStream);
        inputStream.close();
        return pronun;
    }
}
