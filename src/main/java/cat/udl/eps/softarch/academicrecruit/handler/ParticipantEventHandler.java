package cat.udl.eps.softarch.academicrecruit.handler;

import cat.udl.eps.softarch.academicrecruit.domain.Participant;
import cat.udl.eps.softarch.academicrecruit.domain.User;
import cat.udl.eps.softarch.academicrecruit.repository.ParticipantRepository;
import cat.udl.eps.softarch.academicrecruit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class ParticipantEventHandler {

    final Logger logger = LoggerFactory.getLogger(Participant.class);

    final ParticipantRepository participantRepository;

    public ParticipantEventHandler(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @HandleBeforeCreate
    public void handleParticipantPreCreate(Participant participant) {
        logger.info("Before creating: {}", participant.toString());
    }

    @HandleBeforeSave
    public void handleParticipantPreSave(Participant participant) {
        logger.info("Before updating: {}", participant.toString());
    }

    @HandleBeforeDelete
    public void handleParticipantPreDelete(Participant participant) {
        logger.info("Before deleting: {}", participant.toString());
    }

    @HandleBeforeLinkSave
    public void handleParticipantPreLinkSave(Participant participant, Object o) {
        logger.info("Before linking: {} to {}", participant.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleParticipantPostCreate(Participant participant) {
        logger.info("After creating: {}", participant.toString());
    }

    @HandleAfterSave
    public void handleParticipantPostSave(Participant participant) {
        logger.info("After updating: {}", participant.toString());
    }

    @HandleAfterDelete
    public void handleParticipantPostDelete(Participant participant) {
        logger.info("After deleting: {}", participant.toString());
    }

    @HandleAfterLinkSave
    public void handleParticipantPostLinkSave(Participant participant, Object o) {
        logger.info("After linking: {} to {}", participant.toString(), o.toString());
    }
}
