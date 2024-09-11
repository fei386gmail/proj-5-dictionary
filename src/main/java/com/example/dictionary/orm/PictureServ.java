package com.example.dictionary.orm;

import com.example.dictionary.model.Picture;
import org.openqa.selenium.remote.Augmentable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureServ {
    @Autowired
    private PictureRepo pictureRepo;

    public void save(Picture picture)
    {
        pictureRepo.save(picture);
    }

    public Picture getPic(String word)
    {
        return pictureRepo.findByWord(word);
    }
    public boolean hasPic(String word)
    {
        if (getPic(word)==null)
        {
            return false;
        }
        else {
            return true;
        }
    }
}
