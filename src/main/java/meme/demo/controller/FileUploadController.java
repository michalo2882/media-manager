package meme.demo.controller;

import meme.demo.service.FileStorageService;
import meme.demo.model.MediaFile;
import meme.demo.model.UploadStatus;
import meme.demo.repository.MediaFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    MediaFileRepository mediaFileRepository;

}
