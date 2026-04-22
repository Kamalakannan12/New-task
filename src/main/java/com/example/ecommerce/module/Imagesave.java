package com.example.ecommerce.configuration;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
            image.transferTo(file);
            System.out.println("Image saved successfully (Thread)");
        } catch (Exception e) {
            System.out.println("Image save failed: " + e.getMessage());
        }
    }
}
