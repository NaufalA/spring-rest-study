package com.enigmacamp.restapiintro.services;

import com.enigmacamp.restapiintro.repositories.interfaces.FileRepository;
import com.enigmacamp.restapiintro.services.interfaces.FileService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    private FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public String upload(MultipartFile file) {
        return fileRepository.store(file);
    }

    @Override
    public Resource download(String fileName) {
        return fileRepository.load(fileName);
    }

    @Override
    public String remove(String fileName) throws IOException {
        return fileRepository.delete(fileName);
    }
}
