package meme.demo.controller;

import meme.demo.model.MediaFile;
import meme.demo.model.UploadStatus;
import meme.demo.model.User;
import meme.demo.repository.MediaFileRepository;
import meme.demo.repository.UserRepository;
import meme.demo.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static meme.demo.utils.Utils.createRandomUuidString;

@RestController
@RequestMapping("/api/v1/mediaFiles")
public class MediaFileController {

    @Autowired
    MediaFileRepository mediaFileRepository;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public List<MediaFile> getRecent() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        return mediaFileRepository.findByOrderByIdDesc(pageRequest);
    }

    @PostMapping("")
    @Secured("ROLE_USER")
    public MediaFile create(@RequestBody MediaFile mediaFile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByName(authentication.getName());
        mediaFile.setUuid(createRandomUuidString());
        mediaFile.setOwner(user);
        return mediaFileRepository.save(mediaFile);
    }

    @PostMapping("/upload")
    @Secured("ROLE_USER")
    public UploadStatus uploadFile(@RequestParam("uuid") String uuid, @RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String storageLocation = fileStorageService.store(file, uuid);

        MediaFile mediaFile = mediaFileRepository.findByUuid(uuid);
        mediaFile.setFileStorageLocation(storageLocation);
        mediaFile.setServeUrl("/serve/" + uuid + "/" + fileName);
        mediaFileRepository.save(mediaFile);

        UploadStatus status = new UploadStatus();
        status.setOk(true);
        status.setMessage("Success");
        return status;
    }
}
