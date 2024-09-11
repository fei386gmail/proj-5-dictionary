package com.example.dictionary;

import com.example.dictionary.model.Picture;
import com.example.dictionary.model.Word;
import com.example.dictionary.orm.PictureServ;
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
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@SpringBootTest
public class Webspider_picture {
    @Autowired
    private WordServ wordServ;
    @Autowired
    private PictureServ pictureServ;
    private WebDriver webDriver;

    @Test
    public void getPic() throws InterruptedException, IOException {
        //参数
        int startPage=1012;
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
                findPic(w.getWord());
            }
        }


    }
    private void findPic(String word) throws InterruptedException, IOException {
        //找到输入框,输入单词
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

        //找到图片，得到url，保存图片
        WebElement img;
        try {
             img=webDriver.findElement(new By.ById("picUgcImg"));
        }
        catch (NoSuchElementException e)
        {
            return;
        }
        byte[] pic=getPic(img);
        if(pic==null) return;
        try{
            pictureServ.save(new Picture(word,pic));
        }
        catch (Exception e)
        {

        }

    }
    private byte[] getPic(WebElement w) throws IOException {

        String c= w.getAttribute("src");
        URL url;
        try {
              url=new URL(c);
        }
        catch (MalformedURLException e)
        {
            return null;
        }

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
