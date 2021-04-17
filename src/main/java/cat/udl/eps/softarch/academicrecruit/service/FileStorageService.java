package cat.udl.eps.softarch.academicrecruit.service;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileStorageService implements IFileStorageService {
    private final Path root = Paths.get("uploads");

    @Override
    public void init() {
        try {
            if(!Files.exists(root))
                Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public boolean save(Document document, MultipartFile file) {
        try {
            Path path = this.root.resolve(String.valueOf(document.getId())).resolve(file.getOriginalFilename());
            if(!Files.exists(path.getParent()))
                Files.createDirectories(path.getParent());

            Files.deleteIfExists(path);
            Files.copy(file.getInputStream(), path);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Resource load(Document document) {

        if(document.getPath() == null)
            return null;

        try {
            Path file = this.root.resolve(String.valueOf(document.getId())).resolve(document.getPath());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            //throw new RuntimeException("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(Document document, boolean removeParentFolder) {
        try {
            Path file = this.root.resolve(String.valueOf(document.getId())).resolve(document.getPath());
            if(removeParentFolder)
                FileSystemUtils.deleteRecursively(file.getParent());
            else
                Files.delete(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
