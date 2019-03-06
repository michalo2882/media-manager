package meme.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Entity(name = "media_file")
@Table(name = "media_file")
public class MediaFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(unique = true)
    private String uuid;

    private String fileStorageLocation;
    private String serveUrl;
    private int duration;
    private int views;

    @ManyToMany(mappedBy = "mediaFileSet")
    Set<Tag> tagSet;
}
