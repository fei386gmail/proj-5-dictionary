package com.example.dictionary.control;

import com.example.dictionary.common.DetailResult;
import com.example.dictionary.common.DetailResultServ;
import com.example.dictionary.common.Tools;
import com.example.dictionary.common.WordResult;
import com.example.dictionary.model.Frequency;
import com.example.dictionary.model.Word;
import com.example.dictionary.orm.FrequencyServ;
import com.example.dictionary.orm.Pronunciation_2_Serv;
import com.example.dictionary.orm.Pronunciation_US_1_Serv;
import com.example.dictionary.orm.WordServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class WordControl {
    List<WordResult> nullWordResults;
    @Autowired
    private Tools tools;
    @Autowired
    private WordServ wordServ;
    @Autowired
    private DetailResultServ detailResultServ;
    @Autowired
    private Pronunciation_US_1_Serv pronunciation_1Serv;
    @Autowired
    private Pronunciation_2_Serv pronunciation_2_serv;
    @Autowired
    private FrequencyServ frequencyServ;
    public WordControl()
    {
        WordResult nullWordResult=new WordResult("...","...",false,false,0,false);
        nullWordResults=new ArrayList<>();
        nullWordResults.add(nullWordResult);
    }


    @RequestMapping("/dic")
    public String index()
    {
        return "index.html";
    }

    @RequestMapping("/api/getDetail")
    @ResponseBody
    public DetailResult getResult(@RequestParam("ID") String id)
    {
        return detailResultServ.getResult(id);
    }
    @RequestMapping("/api/remember")
    @ResponseBody
    public void getRemember(@RequestParam("ID") String id,@RequestParam("status") Boolean status){
        System.out.println("word"+id);
        System.out.println("staut"+status);
        Word w=wordServ.findOneById(id);
        w.setRemember(!status);
        wordServ.save(w);
    }

    @RequestMapping("/api/getData")
    @ResponseBody
    public List<WordResult> getData(@RequestParam("ID") String id,@RequestParam("highFrequentCheck") Boolean highFrequentCheck) throws InterruptedException {
        List<Word> words=new ArrayList<>();

        //去掉空格
        id=id.replace(" ","");
        //如果有中文字符，则模糊查找解释
        // 如果高频选中，则从高频表中查找单词
        if(isContainChinese(id))
        {
            if(highFrequentCheck==true)
            {
                List<Word> findWordWithTranslation=wordServ.findWordWithTranslation(id);
                words=frequencyServ.getWordFromFrequency(findWordWithTranslation);
            }
            else {
                words=wordServ.findWordWithTranslation(id);
            }
            List<WordResult> wordResults= new ArrayList<>();
            for (Word w: words
            ) {
                wordResults.add(new WordResult(w.getWord(),w.getTranslation(), pronunciation_1Serv.havePronunciation(w.getWord()),pronunciation_2_serv.havePronunciation(w.getWord()),frequencyServ.getFrequency(w.getWord()),w.getRemember()));
            }
            if(wordResults.size()==0) return nullWordResults ;
            return wordResults;        }
        //如果没有中文字符，则查找英文
        //如果高频框选中，则从高频表中查找单词
        //如果英文不包含*，则进行精确查找
        if(!id.contains("*"))
        {   Word word=wordServ.findOneById(id);
            if(word==null) return nullWordResults;
            words.add(word);

        }
        //包含*，则进行模糊查找
        if(id.contains("*")) {
            if(id.startsWith("*") && id.endsWith("*"))  // 如果以*开头和结尾
            {

                if(highFrequentCheck==true)
                {
                    List<Frequency> frequencies=frequencyServ.findFrequencyContain(id);
                    if(frequencies==null || frequencies.size()==0  ) return  nullWordResults;
                    words=wordServ.findWordsFromFrequencies(frequencies);
                }
                else {
                    words=wordServ.findWordsContains(id);
                }
            }
            else if (id.startsWith("*"))  // 如果仅以*开头
            {
                id=id.substring(1,id.length());
                if(highFrequentCheck==true)
                {
                    List<Frequency> frequencies=frequencyServ.findFrenquencyWithSuffix(id);
                    if(frequencies==null) return nullWordResults;
                    words=wordServ.findWordsFromFrequencies(frequencies);
                }
                else {
                    words=wordServ.findWordWithSuffix(id);
                }
            }
            else if (id.endsWith("*"))   //如果以*结尾
            {
                id=id.substring(0,id.length()-1);
                if(highFrequentCheck==true) {
                    List<Frequency> frequencies=frequencyServ.findFrenquencyWithPrefix(id);
                    if(frequencies==null) return nullWordResults;
                    words=wordServ.findWordsFromFrequencies(frequencies);
                }
                else {
                    words=wordServ.findWordWithPrefix(id);
                }
            }
            //中间有星
            else if (id.contains("*") && !id.endsWith("*") && !id.startsWith("*"))
            {
                if(highFrequentCheck==true) {
                    List<Frequency> frequencies=frequencyServ.findFrequenciesByStartAndEnd(id);
                    if(frequencies==null) return nullWordResults;
                    words=wordServ.findWordsFromFrequencies(frequencies);
                }
                else {
                    words=wordServ.findWithStartAndEnd(id);
                }
            }
            //其他情况
            else {
                if(highFrequentCheck==true) {
                    List<Frequency> frequencies=frequencyServ.findFrequencyLike(id);
                    if(frequencies==null) return nullWordResults;
                    words=wordServ.findWordsFromFrequencies(frequencies);
                }
                else {
                    words=wordServ.findWordsLike(id);
                }
            }
        }
        //
        List<WordResult> wordResults= new ArrayList<>();
        if(words==null || words.size()==0 ) return nullWordResults ;

        wordResults= tools.copyWords2WordResults(words);

        return wordResults;
    }
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
