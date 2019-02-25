package meme.demo.repository;

import meme.demo.model.MediaFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface MediaFileRepository extends CrudRepository<MediaFile, Long> {
}
