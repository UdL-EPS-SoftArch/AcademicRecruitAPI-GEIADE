package cat.udl.eps.softarch.academicrecruit.handler;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.domain.User;
import cat.udl.eps.softarch.academicrecruit.exception.ForbiddenException;
import cat.udl.eps.softarch.academicrecruit.repository.DocumentRepository;
import cat.udl.eps.softarch.academicrecruit.repository.DocumentRepository;
import cat.udl.eps.softarch.academicrecruit.service.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void handleDocumentPreCreate(Document document) throws JsonProcessingException {
        logger.info("Before creating: {}", new ObjectMapper().writeValueAsString(document));

        if(document.getPath() != null) {
            throw new ForbiddenException(); //path should be read-only, and declared only on update
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());

        User curr_user = ((User)authentication.getPrincipal());
        document.setUser(curr_user);
    }

    @HandleBeforeSave
    public void handleDocumentPreSave(Document document) throws JsonProcessingException {
        logger.info("Before updating: {}", new ObjectMapper().writeValueAsString(document));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());

        User curr_user = ((User)authentication.getPrincipal());

        entityManager.detach(document); //detach entity from entitymanager, so it can be retrieved
        Document oldDocument = documentRepository.findById(document.getId()).get();

        if(oldDocument.getPath() != null && !oldDocument.getPath().equals(document.getPath())) {
            if(document.getPath().contains("/"))
                throw new ForbiddenException(); //this should be the just name of the file, without any path symbols
            // which could affect our security due to the fact that we use this to delete files, and there could be relative paths!

            fileStorageService.delete(oldDocument, false); //delete previous file which has another name
        }
    }

    @HandleBeforeDelete
    public void handleDocumentPreDelete(Document document) throws JsonProcessingException {
        logger.info("Before deleting: {}", new ObjectMapper().writeValueAsString(document));

        if(document.getPath() != null) {
            fileStorageService.delete(document, true); //delete uploaded file
        }
    }

    @HandleBeforeLinkSave
    public void handleDocumentPreLinkSave(Document document, Object o) throws JsonProcessingException {
        logger.info("Before linking: {} to {}", new ObjectMapper().writeValueAsString(document), new ObjectMapper().writeValueAsString(o));
        if(o instanceof User) {
            throw new ForbiddenException(); //createdUser shall not be changed
        }
    }

    @HandleAfterCreate
    public void handleDocumentPostCreate(Document document) throws JsonProcessingException {
        logger.info("After creating: {}", new ObjectMapper().writeValueAsString(document));
    }

    @HandleAfterSave
    public void handleDocumentPostSave(Document document) throws JsonProcessingException {
        logger.info("After updating: {}", new ObjectMapper().writeValueAsString(document));
    }

    @HandleAfterDelete
    public void handleDocumentPostDelete(Document document) throws JsonProcessingException {
        logger.info("After deleting: {}", new ObjectMapper().writeValueAsString(document));
    }

    @HandleAfterLinkSave
    public void handleDocumentPostLinkSave(Document document, Object o) throws JsonProcessingException {
        logger.info("After linking: {} to {}", new ObjectMapper().writeValueAsString(document), new ObjectMapper().writeValueAsString(o));
    }
}
