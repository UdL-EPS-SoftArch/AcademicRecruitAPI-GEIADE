package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.Candidate;
import cat.udl.eps.softarch.academicrecruit.domain.Participant;
import cat.udl.eps.softarch.academicrecruit.repository.CandidateRepository;
import cat.udl.eps.softarch.academicrecruit.repository.ParticipantRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

public class UpdateCandidateStepDefs {

    final StepDefs stepDefs;
    final CandidateRepository candidateRepository;
    private String newResourceUri;
    private Candidate candidate = new Candidate();

    public UpdateCandidateStepDefs(StepDefs stepDefs, CandidateRepository candidateRepository) {
        this.stepDefs = stepDefs;
        this.candidateRepository = candidateRepository;
    }


    @Given("There is a candidate with name {string}")
    public void thereIsACandidateWithName(String name){

        candidate.setName(name);
        candidateRepository.save(candidate);
    }

    @When("I change the name of the candidate to {string}")
    public void iChangeNameOfCandidateTo(String name) throws Throwable {

        newResourceUri = "/candidates/"+ candidate.getId();

        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((new JSONObject().put("name",name)).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("The previously updated candidate has now name {string}")
    public void thePreviouslyUpdatedCandidateHasNowName(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)));
    }
}
