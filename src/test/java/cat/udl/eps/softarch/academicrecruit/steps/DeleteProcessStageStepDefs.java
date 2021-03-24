package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.repository.ProcessStageRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteProcessStageStepDefs {

    final StepDefs stepDefs;
    final ProcessStageRepository processStageRepository;
    private String newResourceUri;

    public DeleteProcessStageStepDefs(StepDefs stepDefs, ProcessStageRepository processStageRepository) {
        this.stepDefs = stepDefs;
        this.processStageRepository = processStageRepository;
    }

    @When("I delete the process stage with id {string}")
    public void iDeleteTheProcessStageWithId(String id) throws Throwable {
        newResourceUri = "/processStages/"+ id;

        stepDefs.result = stepDefs.mockMvc.perform(
                delete(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject()
                                    .put("id", id)
                                    .toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("The previously deleted process stage doesn't exist")
    public void thePreviouslyDeletedProcessStageDoesntExist() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
