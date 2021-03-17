package cat.udl.eps.softarch.academicrecruit.handler;

import cat.udl.eps.softarch.academicrecruit.domain.Participant;
import cat.udl.eps.softarch.academicrecruit.domain.ProcessStage;
import cat.udl.eps.softarch.academicrecruit.repository.ParticipantRepository;
import cat.udl.eps.softarch.academicrecruit.repository.ProcessStageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class ProcessStageEventHandler {

    final Logger logger = LoggerFactory.getLogger(ProcessStage.class);

    final ProcessStageRepository processStageRepository;

    public ProcessStageEventHandler(ProcessStageRepository processStageRepository) {
        this.processStageRepository = processStageRepository;
    }

    @HandleBeforeCreate
    public void handleParticipantPreCreate(ProcessStage processStage) {
        logger.info("Before creating: {}", processStage.toString());
    }

    @HandleBeforeSave
    public void handleParticipantPreSave(ProcessStage processStage) {
        logger.info("Before updating: {}", processStage.toString());
    }

    @HandleBeforeDelete
    public void handleParticipantPreDelete(ProcessStage processStage) {
        logger.info("Before deleting: {}", processStage.toString());
    }

    @HandleBeforeLinkSave
    public void handleParticipantPreLinkSave(ProcessStage processStage, Object o) {
        logger.info("Before linking: {} to {}", processStage.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleParticipantPostCreate(ProcessStage processStage) {
        logger.info("After creating: {}", processStage.toString());
    }

    @HandleAfterSave
    public void handleParticipantPostSave(ProcessStage processStage) {
        logger.info("After updating: {}", processStage.toString());
    }

    @HandleAfterDelete
    public void handleParticipantPostDelete(ProcessStage processStage) {
        logger.info("After deleting: {}", processStage.toString());
    }

    @HandleAfterLinkSave
    public void handleParticipantPostLinkSave(ProcessStage processStage, Object o) {
        logger.info("After linking: {} to {}", processStage.toString(), o.toString());
    }
}
