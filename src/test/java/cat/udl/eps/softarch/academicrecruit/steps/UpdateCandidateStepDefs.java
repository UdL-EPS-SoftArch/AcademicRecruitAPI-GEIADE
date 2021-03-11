package cat.udl.eps.softarch.academicrecruit.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



import cat.udl.eps.softarch.academicrecruit.domain.Candidate;
import cat.udl.eps.softarch.academicrecruit.repository.CandidateRepository;
import io.cucumber.java.en.And;
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

    public UpdateCandidateStepDefs(StepDefs stepDefs, CandidateRepository candidateRepository) {
        this.stepDefs = stepDefs;
        this.candidateRepository = candidateRepository;
    }

    @When("I change the name of the candidate with name {string} ")
    public void iChangeNameOfCandidateTo(String name, String dni) throws Throwable {
        newResourceUri = "/candidate/"+ name + dni;
        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((new JSONObject().put("name", name)).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("The previously updated candidate has now name {string}")
    public void thePreviouslyUpdatedCandidateHasNowName(String newName) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(newName)));
    }
}