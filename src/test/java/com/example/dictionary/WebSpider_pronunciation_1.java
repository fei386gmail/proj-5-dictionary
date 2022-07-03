package com.example.dictionary;

import com.example.dictionary.model.Pronunciation_US_1;
import com.example.dictionary.model.Word;
import com.example.dictionary.orm.Pronunciation_US_1_Serv;
import com.example.dictionary.orm.WordServ;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
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
public class WebSpider_pronunciation_1 {
    @Autowired
    private WordServ wordServ;
    @Autowired
    private Pronunciation_US_1_Serv pronunciation_1Serv;

    private WebDriver webDriver;
    @Test
    public void test() throws IOException, InterruptedException {
        //参数
        int startPage=0;
        int wordsPerPage=100;

        //开始
        System.setProperty("webdriver.chrome.driver","/Users/chenfei/Documents/GitHub/proj-5-dictionary/lib/chromedriver");
        webDriver=new ChromeDriver();

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
                Word w=words.get(i);

                if(pronunciation_1Serv.isExist(w.getWord())) continue;
                System.out.println("query:"+w.getWord());
                while (true){
                    try {
                        webDriver.get("https://cn.bing.com/dict/search?q="+w.getWord()+"&FORM=HDRSC6");
                        break;
                    }
                    catch (TimeoutException e)
                    {
                        e.printStackTrace();
                    }
                    catch (WebDriverException e)
                    {
                        System.out.println("网络断线。");
                    }
                }

                List<WebElement> divs= webDriver.findElements(new By.ByXPath("//div[@class='hd_prUS b_primtxt']"));
                for (WebElement div: divs
                ) {
                    String content=div.getText();
                    if(div.getText().contains("美"))
                    {
                        div.click();
                        savePronunciation_US(w.getWord());
                    }
                }
            }
        }
    }

    public void savePronunciation_US(String word) throws IOException, InterruptedException {
        List<WebElement> as=webDriver.findElements(new By.ByXPath("//a[@title='点击朗读']"));
        if(as.size()==0) return;
        String att= as.get(0).getAttribute("onclick");

        System.out.println(att);
        ArrayList<String> urlStrings=getLinksFromString(att);
        if(urlStrings.size()==0) return;
        String urlString=urlStrings.get(0);
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
        }

        byte[] pronun=IOUtils.toByteArray(inputStream);
        inputStream.close();
        Pronunciation_US_1 pronunciation_usBing =new Pronunciation_US_1(word,pronun);
        try{
            pronunciation_1Serv.save(pronunciation_usBing);
            System.out.println("Save pronunciation of:" +word);
        }
        catch (Exception e){
        }

    }
    //Pull all links from the body for easy retrieval
    private ArrayList<String> getLinksFromString(String text) {
        ArrayList<String> links = new ArrayList();
        if(text==null) return links;

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
