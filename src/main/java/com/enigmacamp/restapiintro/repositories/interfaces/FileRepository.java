package com.enigmacamp.restapiintro.repositories.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileRepository {
    String store(MultipartFile file);
    Resource load(String fileName);
}
