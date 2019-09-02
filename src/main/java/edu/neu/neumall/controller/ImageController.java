package edu.neu.neumall.controller;

import edu.neu.neumall.entity.User;
import edu.neu.neumall.service.ImageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageFileService imageFileService;

    @Autowired
    public ImageController(ImageFileService imageFileService) {
        this.imageFileService = imageFileService;
    }

    /**
     * upload a file, and return its location
     *
     * @param file user uploaded file
     * @param user current user
     * @return url of the location of static file
     */
    @PostMapping
    public String uploadImage(@Valid @RequestParam("image") MultipartFile file
            , @AuthenticationPrincipal User user) {
        if (user == null) {
            return "not login";
        }
        String path = imageFileService.store(file);
        return "{\"path\":" + path + "}";
    }
}
