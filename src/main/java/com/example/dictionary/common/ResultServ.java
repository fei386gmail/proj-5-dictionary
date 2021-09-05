package com.example.dictionary.common;


import com.example.dictionary.model.Word;
import com.example.dictionary.orm.AntonymServ;
import com.example.dictionary.orm.CollocationServ;
import com.example.dictionary.orm.SynonymServ;
import com.example.dictionary.orm.WordServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultServ {

    @Autowired
    private WordServ wordServ;
    @Autowired
    private SynonymServ synonymServ;
    @Autowired
    private AntonymServ antonymServ;
    @Autowired
    private CollocationServ collocationServ;

    public Result getResult(String id)
    {
        Word word=wordServ.findOneById(id);
        String synonym=synonymServ.findSynonym(id);
        String antonym=antonymServ.findAntonym(id);
        String collocation=collocationServ.findCollations(id);

        return new Result(word.getWord(),word.getTranslation(),synonym,antonym,collocation);
    }
}
