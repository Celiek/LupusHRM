package com.pracownikService.demo.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PracownikZdjecieServiceTest {

    @Mock
    private S3Client s3;

    private PracownikZdjecieService service;

    @BeforeEach
    void setUp() {
        service = new PracownikZdjecieService(s3, "pracownicy");
    }

    @Test
    void shouldUploadPhotoAndReturnLink() throws RuntimeException{
        MultipartFile file = new MockMultipartFile(
                "file",
                "photo1.png",
                "image.png",
                "test".getBytes()
        );

        when(s3.putObject(
                any(PutObjectRequest.class),
                any(RequestBody.class)
        )).thenReturn(PutObjectResponse.builder().build());

        String link = service.uploadZdjecie(10L,file);


        assertTrue(link.endsWith(".png"));
    }
}