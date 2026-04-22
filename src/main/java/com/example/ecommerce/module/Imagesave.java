package com.example.ecommerce.module;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

public class Imagesave implements Runnable{
    private MultipartFile image;
    private String uploadDir;
    private String fileName;
    public Imagesave(MultipartFile image, String uploadDir, String fileName) {
        this.image = image;
        this.uploadDir = uploadDir;
        this.fileName = fileName;
    }
    public void run(){
        try{
            File file = new File(uploadDir + fileName);
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(image.getBytes());
            fos.close();
            System.out.println("image saved successfully");
        } catch (Exception e) {
             e.printStackTrace();
        }
    }
}
