package mediamanager.repository;

import mediamanager.model.MediaFile;
import mediamanager.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MediaFileRepository extends CrudRepository<MediaFile, Long> {

    @Query("select f from MediaFile f where f.uuid = :uuid")
    MediaFile findByUuid(@Param("uuid") String uuid);

    List<MediaFile> findByOrderByIdDesc();
    List<MediaFile> findByOwner(User owner);

    Optional<MediaFile> findByIdAndOwner(Long id, User owner);
}
