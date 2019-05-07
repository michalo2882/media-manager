package mediamanager.controller;

import mediamanager.repository.MediaFileRepository;
import mediamanager.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileUploadController {

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    MediaFileRepository mediaFileRepository;

}
