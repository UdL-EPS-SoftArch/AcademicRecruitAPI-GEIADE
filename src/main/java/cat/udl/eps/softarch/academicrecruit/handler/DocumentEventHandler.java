package cat.udl.eps.softarch.academicrecruit.handler;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.domain.User;
import cat.udl.eps.softarch.academicrecruit.exception.ForbiddenException;
import cat.udl.eps.softarch.academicrecruit.repository.DocumentRepository;
import cat.udl.eps.softarch.academicrecruit.repository.DocumentRepository;
import cat.udl.eps.softarch.academicrecruit.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@RepositoryEventHandler
public class DocumentEventHandler {

    final Logger logger = LoggerFactory.getLogger(Document.class);

    final DocumentRepository documentRepository;

    @PersistenceContext
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());

        User curr_user = ((User)authentication.getPrincipal());
        document.setUser(curr_user);
    }

    @HandleBeforeSave
    public void handleDocumentPreSave(Document document) {
        logger.info("Before updating: {}", document.toString());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());

        User curr_user = ((User)authentication.getPrincipal());

        entityManager.detach(document); //detach entity from entitymanager, so it can be retrieved
        Document oldDocument = documentRepository.findById(document.getId()).get();

        if(!oldDocument.getUser().getId().equals(curr_user.getId()) || !document.getUser().getId().equals(curr_user.getId())) {
            throw new ForbiddenException(); //document shall only be edited by the same user who created it, and the creator shouldn't be changed
        }

        if(oldDocument.getPath() != null && !oldDocument.getPath().equals(document.getPath())) {
            if(document.getPath().contains("/"))
                throw new ForbiddenException(); //this should be the just name of the file, without any path symbols
            // which could affect our security due to the fact that we use this to delete files, and there could be relative paths!

            fileStorageService.delete(oldDocument, false); //delete previous file which has another name
        }
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
        if(o instanceof User) {
            throw new ForbiddenException(); //createdUser shall not be changed
        }
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
