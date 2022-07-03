package com.example.dictionary.control;

import com.example.dictionary.model.Picture;
import com.example.dictionary.model.Pronunciation_US_1;
import com.example.dictionary.orm.PictureServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@Controller
public class PictureControl {
    @Autowired
    private PictureServ pictureServ;
    @RequestMapping("/picture/{word}.png")
    public void getPronunciation(@PathVariable("word")String word, HttpServletResponse response) throws IOException {
        Picture p=pictureServ.getPic(word);
        byte[] picture= p.getPicture();
        // 配置文件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("fileName", "UTF-8"));
        OutputStream os = response.getOutputStream();
        int i = picture.length;
        os.write(picture, 0, i);
        os.close();
    }
}
