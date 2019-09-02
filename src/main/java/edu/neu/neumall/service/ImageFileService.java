package edu.neu.neumall.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class ImageFileService {

    // TODO: 2019/7/24 specify the location of static image files
    private Path staticRoot;

    @PostConstruct
    private void initRootPath() {
        var currentPath = Paths.get(System.getProperty("user.dir"));
        var staticPath = Paths.get(currentPath.toString(), "static");
        var location = Paths.get(staticPath.toString(), "img");
        this.staticRoot = location;
    }

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
            var filePath = Paths.get(staticRoot.toString(), fileName);
            System.out.println("Creating file in: " + filePath);
            File target = new File(filePath.toString());

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