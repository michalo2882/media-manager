package meme.demo.repository;

import meme.demo.model.MediaFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MediaFileRepository extends CrudRepository<MediaFile, Long> {

    @Query("select f from MediaFile f where f.uuid = :uuid")
    MediaFile findByUuid(@Param("uuid") String uuid);
}
