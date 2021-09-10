package com.example.dictionary.control;

import com.example.dictionary.common.DetailResult;
import com.example.dictionary.common.DetailResultServ;
import com.example.dictionary.common.WordResult;
import com.example.dictionary.model.Word;
import com.example.dictionary.orm.Pronunciation_USServ;
import com.example.dictionary.orm.WordServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class WordControl {
    @Autowired
    private WordServ wordServ;
    @Autowired
    private DetailResultServ detailResultServ;
    @Autowired
    private Pronunciation_USServ pronunciation_usServ;

    @RequestMapping("/dic")
    public String index()
    {
        return "index.html";
    }

    @RequestMapping("/api/getDetail")
    @ResponseBody
    public DetailResult getResult(@RequestParam("ID") String id)
    {
        return detailResultServ.getResult(id) ;
    }


    @RequestMapping("/api/getData")
    @ResponseBody
    public List<WordResult> getData(@RequestParam("ID") String id) throws InterruptedException {
        List<Word> words=new ArrayList<>();

        //如果有中文字符，则模糊查找解释
        if(isContainChinese(id))
        {
            words=wordServ.findWordWithTranslation(id);

            List<WordResult> wordResults= new ArrayList<>();
            for (Word w: words
            ) {
                wordResults.add(new WordResult(w.getWord(),w.getTranslation(),pronunciation_usServ.havePronunciation(w.getWord())));
            }
            return wordResults;        }

        //如果不包含*，则进行精确查找
        if(!id.contains("*"))
        {
            words.add(wordServ.findOneById(id));
        }
        //包含*，则进行模糊查找
        else {
            if(id.startsWith("*")&& id.endsWith("*"))
            {
                id=id.substring(1,id.length());
                id=id.substring(0,id.length()-1);
                words=wordServ.findWords(id);
            }
            else if (id.startsWith("*"))
            {
                id=id.substring(1,id.length());
                words=wordServ.findWordWithSuffix(id);
            }
            else if (id.endsWith("*"))
            {
                id=id.substring(0,id.length()-1);
                words=wordServ.findWordWithPrefix(id);
            }

            else {
                words=wordServ.findWordWithPrefixAndSuffix(id);
            }
        }
        List<WordResult> wordResults= new ArrayList<>();
        for (Word w: words
             ) {
            wordResults.add(new WordResult(w.getWord(),w.getTranslation(),pronunciation_usServ.havePronunciation(w.getWord())));
        }
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
