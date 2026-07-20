package com.pracownikService.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class PracownikZdjecieService {

    private final S3Client s3;
    private final String bucket;

    public PracownikZdjecieService(S3Client s3, String bucket) {
        this.s3 = s3;
        this.bucket = bucket;
    }

    public String uploadZdjecie(Long idPracownik, MultipartFile file) throws RuntimeException {

        try {
            String randomName = generateRandomFileName(file.getOriginalFilename());
            String key = "zdjecia/" + idPracownik + "/" + randomName;

            s3.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes())
            );

            return generatePublicLink(key);
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public String generateRandomFileName(String fileName){
        String extension ="";

        int dotIndex = fileName.lastIndexOf('.');

        if(dotIndex != -1) {
            extension = fileName.substring(dotIndex);
        }
        return UUID.randomUUID().toString() + extension;
    }

    private String generatePublicLink(String key) {
        return "http://localhost:9000/" + bucket + "/" + key;
    }
}

