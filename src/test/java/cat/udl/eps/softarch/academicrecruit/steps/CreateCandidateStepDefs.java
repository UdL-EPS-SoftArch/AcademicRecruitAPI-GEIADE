package cat.udl.eps.softarch.academicrecruit.steps;
import cat.udl.eps.softarch.academicrecruit.domain.Candidate;
import cat.udl.eps.softarch.academicrecruit.repository.CandidateRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateCandidateStepDefs {
    final StepDefs stepDefs;
    final CandidateRepository candidateRepository;
    private String newResourceUri;

    public CreateCandidateStepDefs(StepDefs stepDefs, CandidateRepository candidateRepository) {
        this.stepDefs = stepDefs;
        this.candidateRepository = candidateRepository;
    }

    @When("I create a participant with role {int}")
    public void iCreateANewDatasetWithTitleAndDescription(int role) throws Throwable {
        Candidate candidate = new Candidate();
        candidate.setRole(Candidate.Role.values()[role]);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(participant))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been created a participant with role {int}")
    public void itHasBeenCreatedAParticipantWithRoleAndIsProvidedBy(int role) throws Throwable {
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.role", is(Participant.Role.values()[role].toString())));
    }
}

