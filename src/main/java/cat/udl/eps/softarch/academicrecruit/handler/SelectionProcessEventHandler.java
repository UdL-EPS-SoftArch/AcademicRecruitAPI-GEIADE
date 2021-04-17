package cat.udl.eps.softarch.academicrecruit.handler;

import cat.udl.eps.softarch.academicrecruit.domain.SelectionProcess;
import cat.udl.eps.softarch.academicrecruit.exception.ForbiddenException;
import cat.udl.eps.softarch.academicrecruit.repository.SelectionProcessRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@RepositoryEventHandler
public class SelectionProcessEventHandler {

    final Logger logger = LoggerFactory.getLogger(SelectionProcess.class);

    final SelectionProcessRepository selectionProcessRepository;

    public SelectionProcessEventHandler(SelectionProcessRepository selectionProcessRepository) {
        this.selectionProcessRepository = selectionProcessRepository;
    }

    @HandleBeforeCreate
    public void handleSelectionProcessPreCreate(SelectionProcess selectionProcess) throws JsonProcessingException {
        logger.info("Before creating: {}", new ObjectMapper().writeValueAsString(selectionProcess));

        if(selectionProcess.getActiveProcessStage() != null) {
            throw new ForbiddenException(); //It can't have an active process stage on create
        }
    }

    @HandleBeforeSave
    public void handleSelectionProcessPreSave(SelectionProcess selectionProcess) throws JsonProcessingException {
        logger.info("Before updating: {}", new ObjectMapper().writeValueAsString(selectionProcess));

        if(selectionProcess.getActiveProcessStage() != null) {
            if(!selectionProcess.getActiveProcessStage().getSelectionProcess().getId().equals(selectionProcess.getId()))
                throw new ForbiddenException(); //The active process stage must be from the same selection process
        }
    }

    @HandleBeforeDelete
    public void handleSelectionProcessPreDelete(SelectionProcess selectionProcess) throws JsonProcessingException {
        logger.info("Before deleting: {}", new ObjectMapper().writeValueAsString(selectionProcess));
    }

    @HandleBeforeLinkSave
    public void handleSelectionProcessPreLinkSave(SelectionProcess selectionProcess, Object o) throws JsonProcessingException {
        logger.info("Before linking: {} to {}", new ObjectMapper().writeValueAsString(selectionProcess), new ObjectMapper().writeValueAsString(o));

        if(selectionProcess.getActiveProcessStage() != null) {
            if(!selectionProcess.getActiveProcessStage().getSelectionProcess().getId().equals(selectionProcess.getId()))
                throw new ForbiddenException(); //The active process stage must be from the same selection process
        }
    }

    @HandleAfterCreate
    public void handleSelectionProcessPostCreate(SelectionProcess selectionProcess) throws JsonProcessingException {
        logger.info("After creating: {}", new ObjectMapper().writeValueAsString(selectionProcess));
    }

    @HandleAfterSave
    public void handleSelectionProcessPostSave(SelectionProcess selectionProcess) throws JsonProcessingException {
        logger.info("After updating: {}", new ObjectMapper().writeValueAsString(selectionProcess));
    }

    @HandleAfterDelete
    public void handleSelectionProcessPostDelete(SelectionProcess selectionProcess) throws JsonProcessingException {
        logger.info("After deleting: {}", new ObjectMapper().writeValueAsString(selectionProcess));
    }

    @HandleAfterLinkSave
    public void handleSelectionProcessPostLinkSave(SelectionProcess selectionProcess, Object o) throws JsonProcessingException {
        logger.info("After linking: {} to {}", new ObjectMapper().writeValueAsString(selectionProcess), new ObjectMapper().writeValueAsString(o));
    }
}
