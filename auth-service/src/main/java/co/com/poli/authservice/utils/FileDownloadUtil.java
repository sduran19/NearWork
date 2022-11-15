package co.com.poli.authservice.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class FileDownloadUtil {
    private Path foundFile;
    public Resource getFileAsResource(String fileCode) throws IOException {
        Path dirPath = Paths.get("user-photos/1");

        Files.list(dirPath).forEach(file -> {
            System.out.println(file.getFileName());
            if (file.getFileName().toString().startsWith(fileCode)) {
                foundFile = file;
                return;
            }
        });

        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }

        return null;
    }
}