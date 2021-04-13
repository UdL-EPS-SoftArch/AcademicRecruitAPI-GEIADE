package cat.udl.eps.softarch.academicrecruit.handler;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.domain.ProcessStage;
import cat.udl.eps.softarch.academicrecruit.repository.DocumentRepository;
import cat.udl.eps.softarch.academicrecruit.repository.ProcessStageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class DocumentUserHandler {

    final Logger logger = LoggerFactory.getLogger(ProcessStage.class);

    @HandleBeforeSave
    public void handleProviderPreUpdate(Document document) {
        logger.info("Before save: {}", document.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());

        Document curr_document = ((Document)authentication.getPrincipal());

        if (!curr_document.getId().equals(document.getId())){
            throw new ForbiddenException();
        }
    }
}
