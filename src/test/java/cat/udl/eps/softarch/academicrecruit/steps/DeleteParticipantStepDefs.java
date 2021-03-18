package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.Participant;
import cat.udl.eps.softarch.academicrecruit.repository.ParticipantRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteParticipantStepDefs {
    final StepDefs stepDefs;
    final ParticipantRepository participantRepository;
    private String newResourceUri;

    public DeleteParticipantStepDefs(StepDefs stepDefs, ParticipantRepository participantRepository) {
        this.stepDefs = stepDefs;
        this.participantRepository = participantRepository;
    }


    @When("I delete a participant with id {string}")
    public void iDeleteParticipantTo(String id) throws Throwable {

        Participant participant = new Participant();
        participant.setId(Long.valueOf(id));

        newResourceUri = "/participants/"+ id;

        stepDefs.result = stepDefs.mockMvc.perform(
                delete(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(participant))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("The previously deleted participant doesn't exist")
    public void thePreviouslyDeletedParticipantNotInDataBase() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get (newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
