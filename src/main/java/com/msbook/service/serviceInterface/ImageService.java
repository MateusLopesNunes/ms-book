package com.msbook.service.serviceInterface;

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
public interface ImageService {

    public void init();

    public String save(MultipartFile file);

    public Resource load(String filename);

    public boolean delete(String filename);
}
