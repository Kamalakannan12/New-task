package com.example.ecommerce.configuration;
import java.io.File;
import org.springframework.web.multipart.MultipartFile;

public class ImageSaveThread extends Thread {

    private MultipartFile image;
    private String uploadDir;
    private String fileName;

    public ImageSaveThread(MultipartFile image, String uploadDir, String fileName) {
        this.image = image;
        this.uploadDir = uploadDir;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            File file = new File(uploadDir + fileName);
            image.transferTo(file);
            System.out.println("Image saved successfully (Thread)");
        } catch (Exception e) {
            System.out.println("Image save failed: " + e.getMessage());
        }
    }
}

