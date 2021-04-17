package cat.udl.eps.softarch.academicrecruit.handler;

import cat.udl.eps.softarch.academicrecruit.domain.SelectionProcess;
import cat.udl.eps.softarch.academicrecruit.exception.ForbiddenException;
import cat.udl.eps.softarch.academicrecruit.repository.SelectionProcessRepository;
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
    public void handleSelectionProcessPreCreate(SelectionProcess selectionProcess) {
        logger.info("Before creating: {}", selectionProcess.toString());

        if(selectionProcess.getActiveProcessStage() != null) {
            throw new ForbiddenException(); //It can't have an active process stage on create
        }
    }

    @HandleBeforeSave
    public void handleSelectionProcessPreSave(SelectionProcess selectionProcess) {
        logger.info("Before updating: {}", selectionProcess.toString());

        if(selectionProcess.getActiveProcessStage() != null) {
            if(!selectionProcess.getActiveProcessStage().getSelectionProcess().getId().equals(selectionProcess.getId()))
                throw new ForbiddenException(); //The active process stage must be from the same selection process
        }
    }

    @HandleBeforeDelete
    public void handleSelectionProcessPreDelete(SelectionProcess selectionProcess) {
        logger.info("Before deleting: {}", selectionProcess.toString());
    }

    @HandleBeforeLinkSave
    public void handleSelectionProcessPreLinkSave(SelectionProcess selectionProcess, Object o) {
        logger.info("Before linking: {} to {}", selectionProcess.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleSelectionProcessPostCreate(SelectionProcess selectionProcess) {
        logger.info("After creating: {}", selectionProcess.toString());
    }

    @HandleAfterSave
    public void handleSelectionProcessPostSave(SelectionProcess selectionProcess) {
        logger.info("After updating: {}", selectionProcess.toString());
    }

    @HandleAfterDelete
    public void handleSelectionProcessPostDelete(SelectionProcess selectionProcess) {
        logger.info("After deleting: {}", selectionProcess.toString());
    }

    @HandleAfterLinkSave
    public void handleSelectionProcessPostLinkSave(SelectionProcess selectionProcess, Object o) {
        logger.info("After linking: {} to {}", selectionProcess.toString(), o.toString());
    }
}
