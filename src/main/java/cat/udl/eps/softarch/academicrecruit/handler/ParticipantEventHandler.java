package cat.udl.eps.softarch.academicrecruit.handler;

import cat.udl.eps.softarch.academicrecruit.domain.Participant;
import cat.udl.eps.softarch.academicrecruit.domain.User;
import cat.udl.eps.softarch.academicrecruit.repository.ParticipantRepository;
import cat.udl.eps.softarch.academicrecruit.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void handleParticipantPreCreate(Participant participant) throws JsonProcessingException {
        logger.info("Before creating: {}", new ObjectMapper().writeValueAsString(participant));
    }

    @HandleBeforeSave
    public void handleParticipantPreSave(Participant participant) throws JsonProcessingException {
        logger.info("Before updating: {}", new ObjectMapper().writeValueAsString(participant));
    }

    @HandleBeforeDelete
    public void handleParticipantPreDelete(Participant participant) throws JsonProcessingException {
        logger.info("Before deleting: {}", new ObjectMapper().writeValueAsString(participant));
    }

    @HandleBeforeLinkSave
    public void handleParticipantPreLinkSave(Participant participant, Object o) throws JsonProcessingException {
        logger.info("Before linking: {} to {}", new ObjectMapper().writeValueAsString(participant), new ObjectMapper().writeValueAsString(o));
    }

    @HandleAfterCreate
    public void handleParticipantPostCreate(Participant participant) throws JsonProcessingException {
        logger.info("After creating: {}", new ObjectMapper().writeValueAsString(participant));
    }

    @HandleAfterSave
    public void handleParticipantPostSave(Participant participant) throws JsonProcessingException {
        logger.info("After updating: {}", new ObjectMapper().writeValueAsString(participant));
    }

    @HandleAfterDelete
    public void handleParticipantPostDelete(Participant participant) throws JsonProcessingException {
        logger.info("After deleting: {}", new ObjectMapper().writeValueAsString(participant));
    }

    @HandleAfterLinkSave
    public void handleParticipantPostLinkSave(Participant participant, Object o) throws JsonProcessingException {
        logger.info("After linking: {} to {}", new ObjectMapper().writeValueAsString(participant), new ObjectMapper().writeValueAsString(o));
    }
}
