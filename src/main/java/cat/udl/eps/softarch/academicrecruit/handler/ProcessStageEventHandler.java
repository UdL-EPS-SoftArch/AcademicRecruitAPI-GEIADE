package cat.udl.eps.softarch.academicrecruit.handler;

import cat.udl.eps.softarch.academicrecruit.domain.ProcessStage;
import cat.udl.eps.softarch.academicrecruit.domain.ProcessStage;
import cat.udl.eps.softarch.academicrecruit.exception.ForbiddenException;
import cat.udl.eps.softarch.academicrecruit.repository.ProcessStageRepository;
import cat.udl.eps.softarch.academicrecruit.repository.ProcessStageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
@RepositoryEventHandler
public class ProcessStageEventHandler {

    final Logger logger = LoggerFactory.getLogger(ProcessStage.class);

    final ProcessStageRepository processStageRepository;

    public ProcessStageEventHandler(ProcessStageRepository processStageRepository) {
        this.processStageRepository = processStageRepository;
    }

    @HandleBeforeCreate
    public void handleProcessStagePreCreate(ProcessStage processStage) {
        logger.info("Before creating: {}", processStage.toString());

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

    }

    @HandleBeforeSave
    public void handleProcessStagePreSave(ProcessStage processStage) {
        logger.info("Before updating: {}", processStage.toString());

        //How to know if fields have changed?
    }

    @HandleBeforeDelete
    public void handleProcessStagePreDelete(ProcessStage processStage) {
        logger.info("Before deleting: {}", processStage.toString());
    }

    @HandleBeforeLinkSave
    public void handleProcessStagePreLinkSave(ProcessStage processStage, Object o) {
        logger.info("Before linking: {} to {}", processStage.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleProcessStagePostCreate(ProcessStage processStage) {
        logger.info("After creating: {}", processStage.toString());
    }

    @HandleAfterSave
    public void handleProcessStagePostSave(ProcessStage processStage) {
        logger.info("After updating: {}", processStage.toString());
    }

    @HandleAfterDelete
    public void handleProcessStagePostDelete(ProcessStage processStage) {
        logger.info("After deleting: {}", processStage.toString());
    }

    @HandleAfterLinkSave
    public void handleProcessStagePostLinkSave(ProcessStage processStage, Object o) {
        logger.info("After linking: {} to {}", processStage.toString(), o.toString());
    }
}
