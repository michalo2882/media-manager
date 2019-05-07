package meme.demo.controller;

import meme.demo.component.AuthenticationDetailsProvider;
import meme.demo.model.MediaFile;
import meme.demo.model.UploadStatus;
import meme.demo.model.User;
import meme.demo.repository.MediaFileRepository;
import meme.demo.repository.MediaFileResourceAssembler;
import meme.demo.repository.UserRepository;
import meme.demo.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static meme.demo.utils.Utils.createRandomUuidString;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/v1/mediaFiles", produces = "application/json")
public class MediaFileController {

    @Autowired
    MediaFileRepository mediaFileRepository;

    @Autowired
    MediaFileResourceAssembler mediaFileResourceAssembler;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationDetailsProvider authenticationDetailsProvider;

    @GetMapping("")
    public Resources<Resource<MediaFile>> getRecent() {
        User user = authenticationDetailsProvider.getCurrentUser();

        List<Resource<MediaFile>> mediaFiles;
        if (user != null) {
            mediaFiles =  mediaFileRepository
                .findByOwner(user).stream()
                .map(mediaFileResourceAssembler::toResource)
                .collect(Collectors.toList());
        }
        else {
            mediaFiles = new ArrayList<>();
        }

        return new Resources<>(mediaFiles,
            linkTo(methodOn(MediaFileController.class).getRecent()).withSelfRel());
    }

    @GetMapping("/{id}")
    public Resource<MediaFile> getDetails(@PathVariable Long id) {
        User user = authenticationDetailsProvider.getCurrentUser();

        MediaFile mediaFile =  mediaFileRepository.findByIdAndOwner(id, user)
                .orElseThrow(() -> new MediaFileNotFoundException(id));
        return mediaFileResourceAssembler.toResource(mediaFile);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        MediaFile mediaFile =  mediaFileRepository.findById(id)
                .orElseThrow(() -> new MediaFileNotFoundException(id));
        mediaFileRepository.delete(mediaFile);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    @Secured("ROLE_USER")
    public Resource<MediaFile> create(@RequestBody MediaFile mediaFile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByName(authentication.getName());
        mediaFile.setUuid(createRandomUuidString());
        mediaFile.setOwner(user);
        return mediaFileResourceAssembler.toResource(
                mediaFileRepository.save(mediaFile));
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
