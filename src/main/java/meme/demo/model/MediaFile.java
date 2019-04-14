package meme.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Entity
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

    @ManyToOne
    User owner;

    public MediaFile() {
    }

    public MediaFile(String uuid, String name, String fileStorageLocation, String serveUrl, int duration, User owner) {
        this.uuid = uuid;
        this.name = name;
        this.fileStorageLocation = fileStorageLocation;
        this.serveUrl = serveUrl;
        this.duration = duration;
        this.owner = owner;
    }
}
