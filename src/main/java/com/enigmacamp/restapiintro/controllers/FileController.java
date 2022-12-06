package com.enigmacamp.restapiintro.controllers;

import com.enigmacamp.restapiintro.models.dtos.requests.FormDataWithFile;
import com.enigmacamp.restapiintro.services.interfaces.FileService;
import com.enigmacamp.restapiintro.shared.classes.CommonResponse;
import com.enigmacamp.restapiintro.shared.classes.SuccessResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file-storage")
public class FileController {
    private final FileService fileService;
    private List<String> allowedContentTypes;

    public FileController(FileService fileService, @Value("${file.upload.allowed-types}") String allowedTypes) {
        this.fileService = fileService;
        allowedContentTypes = Arrays
                .stream(allowedTypes.split(","))
                .filter(s -> !s.equals(""))
                .collect(Collectors.toList());
    }

    @PostMapping("/upload")
    public ResponseEntity<CommonResponse> uploadFile(FormDataWithFile formDataWithFile) throws IOException {
        MultipartFile file = formDataWithFile.getFile();
        String fileContentType = file.getContentType();

        boolean allowed = allowedContentTypes.size() == 0;
        if (!allowed) {
            for (String allowedContentType : allowedContentTypes) {
                assert fileContentType != null;
                if (fileContentType.equals(allowedContentType)) {
                    allowed = true;
                    break;
                }
            }
        }

        if (allowed) {
            String savedPath = fileService.upload(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                    HttpStatus.CREATED.toString(),
                    "Upload File Success",
                    savedPath
            ));
        } else {
            throw new RuntimeException("Content Type not allowed");
        }

    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam("fileName") String fileName) throws IOException {
        Resource file = fileService.download(fileName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
