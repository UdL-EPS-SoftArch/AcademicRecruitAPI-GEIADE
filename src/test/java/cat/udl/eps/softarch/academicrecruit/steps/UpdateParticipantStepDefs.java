package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.Participant;
import cat.udl.eps.softarch.academicrecruit.repository.ParticipantRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

public class UpdateParticipantStepDefs {

    final StepDefs stepDefs;
    final ParticipantRepository participantRepository;
    private String newResourceUri;

    public UpdateParticipantStepDefs(StepDefs stepDefs, ParticipantRepository participantRepository) {
        this.stepDefs = stepDefs;
        this.participantRepository = participantRepository;
    }

    @When("I change the role of the participant with id {string} to {int}")
    public void iChangeRoleOfParticipantTo(String id, int role) throws Throwable {
        Participant participant = new Participant();
        participant.setRole(Participant.Role.values()[role]);
        newResourceUri = "/participants/"+ id;
        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        //.content((new JSONObject().put("role", Participant.Role.values()[role].toString())).toString())
                        .content(stepDefs.mapper.writeValueAsString(participant))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("The previously updated participant has now role {int}")
    public void thePreviouslyUpdatedDatasetHasNowTitle(int newRole) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.role", is(Participant.Role.values()[newRole].toString())));
    }
}
