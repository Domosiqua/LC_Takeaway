package com.controller;

import com.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 上传文件
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.basePath}")
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> Upload(MultipartFile file){
        log.info("file：{}",file);
        String originalFilename = file.getOriginalFilename();
        String suffix=originalFilename.substring(originalFilename.lastIndexOf('.'));
        String filename = UUID.randomUUID().toString();
        File dir=new File(basePath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(basePath+filename+suffix));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.success(filename+suffix);
    }
    @GetMapping("/download")
    public void download(HttpServletResponse response, String name){
        response.setContentType("image/jpeg");
        try {
            FileInputStream inputStream=new FileInputStream(new File(basePath+name));
            IOUtils.copy(inputStream,response.getOutputStream());
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }


    }
}
