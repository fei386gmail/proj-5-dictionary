package com.example.dictionary;

import com.example.dictionary.model.Pronunciation_US_1;
import com.example.dictionary.model.Pronunciation_US_2;
import com.example.dictionary.model.Word;
import com.example.dictionary.orm.Pronunciation_2_Serv;
import com.example.dictionary.orm.WordServ;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class WebSpider_pronunciation_2 {

    @Autowired
    private WordServ wordServ;
    @Autowired
    private Pronunciation_2_Serv pronunciation_2_serv;
    private WebDriver webDriver;

    @Test
    public void ttt() throws IOException, InterruptedException {
        //参数
        int startPage=1006;
        int wordsPerPage=100;

        //开始
        System.setProperty("webdriver.chrome.driver","/Users/chenfei/Documents/GitHub/proj-5-dictionary/lib/chromedriver94.0.4606.61");
        webDriver=new ChromeDriver();


        //查询分页数量
        PageRequest pageRequest=PageRequest.of(0,wordsPerPage);
        Page<Word> page=wordServ.findAll(pageRequest);
        long totalPages= page.getTotalPages();

        // 初始化页面
        webDriver.get(" https://cn.bing.com/search?q=abuse&qs=n&form=QBRE");
        Thread.sleep(5000);
        // 点击国际版
        webDriver.findElement(new By.ById("est_en")).click();
        Thread.sleep(2000);
        //找到输入框
        WebElement input=webDriver.findElement(new By.ById("dictautodd_c"));
        //循环整个词库
        for(int j=startPage;j<totalPages;j++)
        {
            PageRequest pageRequest1=PageRequest.of(j,wordsPerPage);
            Page<Word> page1=wordServ.findAll(pageRequest1);
            List<Word> words=page1.getContent();

            for(int i=0;i<words.size();i++)
            {
                Word w=words.get(i);

                CharSequence ss=w.getWord();
                input.clear();
                input.sendKeys(ss);
                List<WebElement> divs=webDriver.findElements(new By.ByXPath("//div[@role='button']"));
                divs.get(1).click();
                Thread.sleep(2000);

                WebElement audio;
                String mp3url;
                while (true)
                {
                    try{
                        audio=webDriver.findElement(new By.ByXPath("//audio[@preload='none']"));
                        mp3url=audio.getAttribute("src");
                        savePronunciation_US(w.getWord(),mp3url);
                        break;
                    }catch (NoSuchElementException e)
                    {
                        break;
                    }
                    catch (TimeoutException e)
                    {

                    }
                }



            }
        }



    }

    public void savePronunciation_US(String word,String urlString) throws IOException, InterruptedException {

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

                return;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return;
            }
        }

        byte[] pronun= IOUtils.toByteArray(inputStream);
        inputStream.close();
        Pronunciation_US_2 pronunciation_us_2 =new Pronunciation_US_2(word,pronun);
        try{
            pronunciation_2_serv.save(pronunciation_us_2);
        }
        catch (Exception e){
        }

    }


}
