package com.example.dictionary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class WebSpider {

    public static  void main(String[] args)
    {
        System.setProperty("webdriver.chrome.driver","/Users/chenfei/OneDrive/IDEAProject/proj 5 dictionary/lib/chromedriver");
        WebDriver webDriver=new ChromeDriver();
        webDriver.get("https://cn.bing.com/dict/search?q=reverse");
        By by1=new By.ByXPath("//div[@id='synoid']/div/div");
        By by2=new By.ById("synoid");
        List<WebElement> elements=webDriver.findElements(by1);
        WebElement element=webDriver.findElement(by2);

        List<WebElement> w3=webDriver.findElements(new By.ByXPath("//div[@class='de_title1 b_dictHighlight']"));
        for (WebElement w: w3
             ) {
            System.out.println(w.getText());

        }


    }

}
