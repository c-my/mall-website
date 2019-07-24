package edu.neu.neumall.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
public class ImageFileService {

    private final String staticRoot = this.getClass().getClassLoader().getResource("static/img").getPath() + "/";

    public String store(MultipartFile file) {
        System.out.println("root:" + staticRoot);
        if (file.isEmpty() || Objects.requireNonNull(file.getOriginalFilename()).contains("..")) {
            return "";
        }
        String suffix = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = System.currentTimeMillis()
                + RandomStringUtils.randomAlphanumeric(10)
                + "."
                + suffix;

        try (InputStream inputStream = file.getInputStream()) {
            File target = new File(staticRoot + fileName);

            System.out.println(target.getAbsolutePath());
            if (target.createNewFile()) {
                FileOutputStream fout = new FileOutputStream(target);
                fout.write(file.getBytes());
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return "/img/" + fileName;
    }
}
