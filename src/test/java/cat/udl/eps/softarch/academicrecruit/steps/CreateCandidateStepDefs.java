package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.Candidate;
import cat.udl.eps.softarch.academicrecruit.domain.Participant;
import cat.udl.eps.softarch.academicrecruit.repository.CandidateRepository;
import cat.udl.eps.softarch.academicrecruit.repository.ParticipantRepository;
import cat.udl.eps.softarch.academicrecruit.steps.AuthenticationStepDefs;
import cat.udl.eps.softarch.academicrecruit.steps.StepDefs;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateCandidateStepDefs {

    final StepDefs stepDefs;
    final CandidateRepository candidateRepository;

    public CreateCandidateStepDefs(StepDefs stepDefs, CandidateRepository candidateRepository) {
        this.stepDefs = stepDefs;
        this.candidateRepository = candidateRepository;
    }

    @When("I create a candidate with name {string}")
    public void iCreateANewCandidateWithName(String name) throws Throwable {
        Candidate candidate = new Candidate();
        candidate.setName(name);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(candidate))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been created a candidate with name {string}")
    public void itHasBeenCreatedACandidateWithNameAndIsProvidedBy(String name) throws Throwable {
        String newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)));
    }





}
