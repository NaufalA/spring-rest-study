package com.enigmacamp.restapiintro.repositories;

import com.enigmacamp.restapiintro.repositories.interfaces.FileRepository;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Repository
public class FileRepositoryImpl implements FileRepository {
    private final Path path;

    public FileRepositoryImpl(@Value("${file.upload.path}") String uploadPath) {
        this.path = Paths.get(uploadPath);
    }

    @Override
    public String store(MultipartFile file) {
        try {
//            Date currentDateTime = new Date(System.currentTimeMillis());
//            Path savePath = path.resolve(
//                    file.getOriginalFilename() +
//                            "-" +
//                            new SimpleDateFormat("yyyyMMdd_HHmmss").format(currentDateTime));
            Path savePath = path.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), savePath);
            return savePath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource load(String fileName) {
        Path targetPath = path.resolve(fileName);
        try {
            Resource resource = new UrlResource(targetPath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new NotFoundException("File Not Found");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
