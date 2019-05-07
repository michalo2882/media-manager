package mediamanager.service;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class LocalFileStorageService implements FileStorageService {

    @Override
    public String store(MultipartFile file, String subDirectory) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String path = "C:\\media\\" + subDirectory;
        File subDirectoryFile = new File(path);
        subDirectoryFile.mkdirs();

        path += "\\" + fileName;
        Path destination = Paths.get(path);
        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }
}
