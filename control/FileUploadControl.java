package com.example.demo1.control;

import java.io.File;
import java.io.FileOutputStream;

import com.example.demo1.util.WebConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class FileUploadControl {

    //注解获取配置文件属性值
    @Value("${filePath}")
    private String filePath;
	
    @GetMapping("/fileupload")
    public String uploading() {

        return "uploadphotos";
    }

    //处理文件上传
    @PostMapping("/uploading") //@ResponseBody
    public  String uploading(@RequestParam("file") MultipartFile file,Model model) {
        String url= WebConfig.Path+file.getOriginalFilename();
        try {
            uploadFile(file.getBytes(), file.getOriginalFilename());
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("文件上传失败!");
        	return "uploading failure";
        }
        model.addAttribute("url",url);//放入goods表单中提交保存
        System.out.println("文件上传成功!>>>"+url);
        return "uploadphotos";
    }
    
    private void uploadFile(byte[] filebyte, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){    
            targetFile.mkdirs();
        }       
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(filebyte);
        out.flush();
        out.close();
//        System.out.println("targetFile.getAbsolutePath()!>>>"+targetFile.getAbsolutePath());
    }
    
}