package com.example.dictionary.control;

import com.example.dictionary.model.Pronunciation_US_1;
import com.example.dictionary.model.Pronunciation_US_2;
import com.example.dictionary.model.Sentence1;
import com.example.dictionary.model.Sentence2;
import com.example.dictionary.orm.Pronunciation_2_Serv;
import com.example.dictionary.orm.Pronunciation_US_1_Serv;
import com.example.dictionary.orm.Sentence1Serv;
import com.example.dictionary.orm.Sentence2Serv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller
public class PronunciationControl {

    @Autowired
    private Pronunciation_US_1_Serv pServ;
    @Autowired
    private Pronunciation_2_Serv pronunciation_2_serv;
    @Autowired
    private Sentence1Serv sentence1Serv;
    @Autowired
    private Sentence2Serv sentence2Serv;


    @RequestMapping("/audio/{word}.mp3")
    public void getPronunciation(@PathVariable("word")String word, HttpServletResponse response) throws IOException {
        Pronunciation_US_1 p=pServ.get(word);
        byte[] mp3= p.getPronunciation();
        // 配置文件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("fileName", "UTF-8"));

        OutputStream os = response.getOutputStream();
        int i = mp3.length;
        os.write(mp3, 0, i);
        os.close();
    }

    @RequestMapping("/audio2/{word}.mp3")
    public void getPronunciation2(@PathVariable("word")String word, HttpServletResponse response) throws IOException {
        Pronunciation_US_2 p=pronunciation_2_serv.findByWord(word);
        byte[] mp3= p.getPronunciation();
        // 配置文件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("fileName", "UTF-8"));

        OutputStream os = response.getOutputStream();
        int i = mp3.length;
        os.write(mp3, 0, i);
        os.close();

    }
    @RequestMapping("/audioSentence1/{word}.mp3")
    public void getSentencePronunciation(@PathVariable("word")String word, HttpServletResponse response) throws IOException {
        Sentence1 sentence= sentence1Serv.findSentenceByWord(word);
        byte[] pronun=sentence.getPronunciation();
        // 配置文件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("fileName", "UTF-8"));

        OutputStream os = response.getOutputStream();
        int i = pronun.length;
        os.write(pronun, 0, i);
        os.close();
    }
    @RequestMapping("/audioSentence2/{word}.mp3")
    public void getSentencePronunciation2(@PathVariable("word")String word, HttpServletResponse response) throws IOException {
        Sentence2 sentence= sentence2Serv.findSentenceByWord(word);
        byte[] pronun=sentence.getPronunciation();
        // 配置文件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("fileName", "UTF-8"));

        OutputStream os = response.getOutputStream();
        int i = pronun.length;
        os.write(pronun, 0, i);
        os.close();
    }



}
