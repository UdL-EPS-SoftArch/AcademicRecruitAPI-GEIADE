package cat.udl.eps.softarch.academicrecruit.controller;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.domain.User;
import cat.udl.eps.softarch.academicrecruit.repository.DocumentRepository;
import cat.udl.eps.softarch.academicrecruit.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;

@Controller
public class FileStorageController {

  @Autowired
  FileStorageService storageService;

  @Autowired
  DocumentRepository documentRepository;

  @PostMapping("/files/{fileid}")
  public @ResponseBody
  ResponseEntity<Resource> uploadFile(@PathVariable Long fileid, @RequestParam("file") MultipartFile file) {
    Optional<Document> documentOptional = documentRepository.findById(fileid);

    if(documentOptional.isPresent()) {
      Document document = documentOptional.get();
      if (storageService.save(document, file)) {
        document.setPath(file.getOriginalFilename());
        document.setMime(file.getContentType());
        document.setLength((int) file.getSize());

        documentRepository.save(document);

        return ResponseEntity.status(HttpStatus.OK).body(null);
      }
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
  }

  @GetMapping("/files/{fileid}")
  @ResponseBody
  public ResponseEntity<Resource> getFile(@PathVariable Long fileid) {
    Optional<Document> document = documentRepository.findById(fileid);
    Resource resource;

    if(document.isPresent() && (resource = storageService.load(document.get())) != null) {
      return ResponseEntity.ok()
              .header("Content-Disposition", "attachment; filename=" + resource.getFilename())
              .contentType(MediaType.APPLICATION_OCTET_STREAM)
              .body(resource);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    /*File file = resource.getFile();
    final long resourceLength = file.length();
    final long lastModified = file.lastModified();

    return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=" + file.getName())
            .contentLength(resourceLength)
            .lastModified(lastModified)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(new InputStreamResource(resource.getInputStream()));*/
  }

  @DeleteMapping("/files/{fileid}")
  @ResponseBody
  public ResponseEntity<Resource> deleteFile(@PathVariable Long fileid) {
    Optional<Document> document = documentRepository.findById(fileid);

    if(document.isPresent() && document.get().getPath() != null) {
      if(storageService.delete(document.get(), true)) {
        document.get().setPath(null);
        document.get().setMime(null);
        document.get().setLength(0);
        documentRepository.save(document.get());
        return ResponseEntity.status(HttpStatus.OK).body(null);
      }

    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
  }
}
