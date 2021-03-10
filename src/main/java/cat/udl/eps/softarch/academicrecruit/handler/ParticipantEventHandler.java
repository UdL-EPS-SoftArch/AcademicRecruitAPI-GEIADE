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
    public void handleParticipantPreCreate(Participant player) {
        logger.info("Before creating: {}", player.toString());
    }

    @HandleBeforeSave
    public void handleParticipantPreSave(Participant player) {
        logger.info("Before updating: {}", player.toString());
    }

    @HandleBeforeDelete
    public void handleParticipantPreDelete(Participant player) {
        logger.info("Before deleting: {}", player.toString());
    }

    @HandleBeforeLinkSave
    public void handleParticipantPreLinkSave(Participant player, Object o) {
        logger.info("Before linking: {} to {}", player.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleParticipantPostCreate(Participant player) {
        logger.info("After creating: {}", player.toString());
    }

    @HandleAfterSave
    public void handleParticipantPostSave(Participant player) {
        logger.info("After updating: {}", player.toString());
    }

    @HandleAfterDelete
    public void handleParticipantPostDelete(Participant player) {
        logger.info("After deleting: {}", player.toString());
    }

    @HandleAfterLinkSave
    public void handleParticipantPostLinkSave(Participant player, Object o) {
        logger.info("After linking: {} to {}", player.toString(), o.toString());
    }
}
