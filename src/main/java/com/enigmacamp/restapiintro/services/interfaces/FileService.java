package com.enigmacamp.restapiintro.services.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String upload(MultipartFile file);
    Resource download(String fileName);

    String remove(String fileName) throws IOException;
}
