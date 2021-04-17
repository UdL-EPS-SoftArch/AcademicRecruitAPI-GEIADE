package cat.udl.eps.softarch.academicrecruit.service;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface IFileStorageService {

    void init();

    boolean save(Document document, MultipartFile file);

    Resource load(Document document);

    boolean delete(Document document, boolean removeParentFolder);
}
