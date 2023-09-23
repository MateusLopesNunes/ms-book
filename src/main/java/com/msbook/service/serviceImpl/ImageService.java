package com.msbook.service.serviceImpl;

import com.msbook.dto.exception.ObjectNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {

    private final Path root = Paths.get("src/main/resources/uploads");

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public String save(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            List<String> validExtensions = List.of(".png", ".jpg", ".jpeg");

            if (!validExtensions.contains(extension.toLowerCase())) {
                throw new IllegalArgumentException("Extensão de arquivo inválida");
            }

           String uniqueFileName = UUID.randomUUID().toString() + extension;
           String filePath = root + File.separator + uniqueFileName;
           Files.copy(file.getInputStream(), this.root.resolve(uniqueFileName));

            return uniqueFileName;
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new ObjectNotFoundException("A file of that name already exists.");
            }

            throw new ObjectNotFoundException("A file of that name already exists.");
        }
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ObjectNotFoundException("Esta imagem não existe");
            }
        } catch (MalformedURLException e) {
            throw new ObjectNotFoundException("Url não formatada corretamente");
        }
    }

    public boolean delete(String filename) {
        try {
            Path file = root.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
