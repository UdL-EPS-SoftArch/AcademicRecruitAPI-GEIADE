package cat.udl.eps.softarch.academicrecruit.handler;

import cat.udl.eps.softarch.academicrecruit.domain.ProcessStage;
import cat.udl.eps.softarch.academicrecruit.exception.ForbiddenException;
import cat.udl.eps.softarch.academicrecruit.repository.ProcessStageRepository;
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
public class ProcessStageEventHandler {

    final Logger logger = LoggerFactory.getLogger(ProcessStage.class);

    final ProcessStageRepository processStageRepository;

    @PersistenceContext
    EntityManager entityManager;

    public ProcessStageEventHandler(ProcessStageRepository processStageRepository) {
        this.processStageRepository = processStageRepository;
    }

    @HandleBeforeCreate
    public void handleProcessStagePreCreate(ProcessStage processStage) throws JsonProcessingException {
        logger.info("Before creating: {}", new ObjectMapper().writeValueAsString(processStage));

        if(processStage.getBeginDate() == null)
            processStage.setBeginDate(new Date()); //define a begin date if not set

        if(processStage.getEndDate() == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, 14); //add 14 days
            processStage.setEndDate(cal.getTime()); //define a end date if not set
        }

        if(processStage.getBeginDate().compareTo(processStage.getEndDate()) > 0) {
            throw new ForbiddenException(); //begin date should be a date that is before end date
        }

        if(processStage.getSelectionProcess() == null) {
            throw new ForbiddenException(); //it can't be null
        }

        List<ProcessStage> processStages = processStageRepository.findBySelectionProcessAndStep(processStage.getSelectionProcess(), processStage.getStep());
        if(processStages.size() > 0) {
            //There is already an existing processStage for the same selectionprocess and step
            throw new ForbiddenException();
        }
    }

    @HandleBeforeSave
    public void handleProcessStagePreSave(ProcessStage processStage) throws JsonProcessingException {
        logger.info("Before updating: {}", new ObjectMapper().writeValueAsString(processStage));

        if(processStage.getBeginDate() == null)
            processStage.setBeginDate(new Date()); //define a begin date if not set

        if(processStage.getEndDate() == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, 14); //add 14 days
            processStage.setEndDate(cal.getTime()); //define a end date if not set
        }

        if(processStage.getBeginDate().compareTo(processStage.getEndDate()) > 0) {
            throw new ForbiddenException(); //begin date should be a date that is before end date
        }

        if(processStage.getSelectionProcess() == null) {
            throw new ForbiddenException(); //it can't be null
        }

        entityManager.detach(processStage); //detach entity from entitymanager, so it can be retrieved
        ProcessStage oldProcessStage = processStageRepository.findById(processStage.getId()).get();

        if(!oldProcessStage.getSelectionProcess().getId().equals(processStage.getSelectionProcess().getId())) {
            throw new ForbiddenException(); //selectionProcess link has changed, and it shouldn't be changed
        }

        if(oldProcessStage.getStep() != processStage.getStep()) {
            throw new ForbiddenException(); //selectionProcess step has changed, and it shouldn't be changed
        }
    }

    @HandleBeforeDelete
    public void handleProcessStagePreDelete(ProcessStage processStage) throws JsonProcessingException {
        logger.info("Before deleting: {}", new ObjectMapper().writeValueAsString(processStage));
    }

    @HandleBeforeLinkSave
    public void handleProcessStagePreLinkSave(ProcessStage processStage, Object o) throws JsonProcessingException {
        logger.info("Before linking: {} to {}", new ObjectMapper().writeValueAsString(processStage), new ObjectMapper().writeValueAsString(o));
        throw new ForbiddenException(); //It must be defined on the create and the link can't be changed
    }

    @HandleAfterCreate
    public void handleProcessStagePostCreate(ProcessStage processStage) throws JsonProcessingException {
        logger.info("After creating: {}", new ObjectMapper().writeValueAsString(processStage));
    }

    @HandleAfterSave
    public void handleProcessStagePostSave(ProcessStage processStage) throws JsonProcessingException {
        logger.info("After updating: {}", new ObjectMapper().writeValueAsString(processStage));
    }

    @HandleAfterDelete
    public void handleProcessStagePostDelete(ProcessStage processStage) throws JsonProcessingException {
        logger.info("After deleting: {}", new ObjectMapper().writeValueAsString(processStage));
    }

    @HandleAfterLinkSave
    public void handleProcessStagePostLinkSave(ProcessStage processStage, Object o) throws JsonProcessingException {
        logger.info("After linking: {} to {}", new ObjectMapper().writeValueAsString(processStage), new ObjectMapper().writeValueAsString(o));
    }
}
