package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.Participant;
import cat.udl.eps.softarch.academicrecruit.domain.ProcessStage;
import cat.udl.eps.softarch.academicrecruit.repository.ProcessStageRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UpdateProcessStageStepDefs {

    final StepDefs stepDefs;
    final ProcessStageRepository processStageRepository;
    private String newResourceUri;

    public UpdateProcessStageStepDefs(StepDefs stepDefs, ProcessStageRepository processStageRepository) {
        this.stepDefs = stepDefs;
        this.processStageRepository = processStageRepository;
    }

    @When("I change the step of the process stage with id {string} to {int}")
    public void iChangeRoleOfParticipantTo(String id, int step) throws Throwable {
        newResourceUri = "/processStages/"+ id;

        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject()
                                    .put("id", id)
                                    .put("step", step)
                                    .toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("The previously updated process stage has now step {int}")
    public void thePreviouslyUpdatedDatasetHasNowTitle(int newStep) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.step", is(newStep)));
    }
}
