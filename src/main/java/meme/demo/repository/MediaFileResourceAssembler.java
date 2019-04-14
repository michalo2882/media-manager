package meme.demo.repository;

import meme.demo.controller.MediaFileController;
import meme.demo.model.MediaFile;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class MediaFileResourceAssembler implements ResourceAssembler<MediaFile, Resource<MediaFile>> {

    @Override
    public Resource<MediaFile> toResource(MediaFile mediaFile) {
        return new Resource<>(mediaFile,
                linkTo(methodOn(MediaFileController.class).getDetails(mediaFile.getId())).withSelfRel());
    }
}
