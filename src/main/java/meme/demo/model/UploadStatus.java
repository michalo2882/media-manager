package meme.demo.model;

import lombok.Data;
import lombok.Setter;

@Data
public class UploadStatus {
    private boolean isOk;
    private String message;
}
