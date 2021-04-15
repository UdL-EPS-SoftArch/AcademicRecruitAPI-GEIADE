package cat.udl.eps.softarch.academicrecruit.handler;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.domain.ProcessStage;
import cat.udl.eps.softarch.academicrecruit.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class DocumentUserHandler {

    final Logger logger = LoggerFactory.getLogger(ProcessStage.class);

    @HandleBeforeCreate
    public void handleProviderPreUpdate(Document document) {
        logger.info("Before save: {}", document.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());

        User curr_user = ((User)authentication.getPrincipal());
        document.setUser(curr_user);

    }
}
