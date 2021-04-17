package cat.udl.eps.softarch.academicrecruit.handler;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.exception.ForbiddenException;
import cat.udl.eps.softarch.academicrecruit.repository.DocumentRepository;
import cat.udl.eps.softarch.academicrecruit.repository.DocumentRepository;
import cat.udl.eps.softarch.academicrecruit.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@RepositoryEventHandler
public class DocumentEventHandler {

    final Logger logger = LoggerFactory.getLogger(Document.class);

    final DocumentRepository documentRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    FileStorageService fileStorageService;

    public DocumentEventHandler(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @HandleBeforeCreate
    public void handleDocumentPreCreate(Document document) {
        logger.info("Before creating: {}", document.toString());

        if(document.getPath() != null) {
            throw new ForbiddenException(); //path should be read-only, and declared only on update
        }
    }

    @HandleBeforeSave
    public void handleDocumentPreSave(Document document) {
        logger.info("Before updating: {}", document.toString());

        entityManager.detach(document); //detach entity from entitymanager, so it can be retrieved
        Document oldDocument = documentRepository.findById(document.getId()).get();

        if(oldDocument.getPath() != null && !oldDocument.getPath().equals(document.getPath())) {
            if(document.getPath().contains("/"))
                throw new ForbiddenException(); //this should be the just name of the file, without any path symbols
            // which could affect our security due to the fact that we use this to delete files, and there could be relative paths!

            fileStorageService.delete(oldDocument, false); //delete previous file which has another name
        }

        entityManager.merge(document); //attach existing entity to entitymanager again
    }

    @HandleBeforeDelete
    public void handleDocumentPreDelete(Document document) {
        logger.info("Before deleting: {}", document.toString());

        if(document.getPath() != null) {
            fileStorageService.delete(document, true); //delete uploaded file
        }
    }

    @HandleBeforeLinkSave
    public void handleDocumentPreLinkSave(Document document, Object o) {
        logger.info("Before linking: {} to {}", document.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleDocumentPostCreate(Document document) {
        logger.info("After creating: {}", document.toString());
    }

    @HandleAfterSave
    public void handleDocumentPostSave(Document document) {
        logger.info("After updating: {}", document.toString());
    }

    @HandleAfterDelete
    public void handleDocumentPostDelete(Document document) {
        logger.info("After deleting: {}", document.toString());
    }

    @HandleAfterLinkSave
    public void handleDocumentPostLinkSave(Document document, Object o) {
        logger.info("After linking: {} to {}", document.toString(), o.toString());
    }
}
