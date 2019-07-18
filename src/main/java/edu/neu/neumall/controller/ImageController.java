package edu.neu.neumall.controller;


import edu.neu.neumall.service.ImageFileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/img")
public class ImageController {

    private ImageFileService imageFileService;

    public ImageController(ImageFileService imageFileService) {
        this.imageFileService = imageFileService;
    }

    @PostMapping
    public String uploadImage(@RequestParam("image") MultipartFile file) {
        String path = imageFileService.store(file);
        return "{\"path\":" + path + "}";
    }

}
