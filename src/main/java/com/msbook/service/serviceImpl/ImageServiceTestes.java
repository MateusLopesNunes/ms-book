package com.msbook.service.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {


//    @Value("${upload.path}") // Defina o diretório de upload em application.properties
    private String uploadPath = "classpath";

    // implementação de upload de imagem no servidor
    public String uploadImage(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        List<String> validExtensions = List.of(".png", ".jpg", ".jpeg");

        if (!validExtensions.contains(extension.toLowerCase())) {
            throw new IllegalArgumentException("Extensão de arquivo inválida");
        }

        String fileName = UUID.randomUUID().toString() + extension;
        String filePath = uploadPath + File.separator + fileName;

        File destinationFile = new File(filePath);

        // Certifique-se de que o diretório de destino existe, se não, crie-o
        File destinationDir = new File(uploadPath);
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        file.transferTo(destinationFile);

        return filePath;
    }
}
