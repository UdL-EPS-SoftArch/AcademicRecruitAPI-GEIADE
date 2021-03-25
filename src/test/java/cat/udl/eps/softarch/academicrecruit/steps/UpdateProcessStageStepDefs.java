package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.SelectionProcess;
import cat.udl.eps.softarch.academicrecruit.repository.ProcessStageRepository;
import cat.udl.eps.softarch.academicrecruit.repository.SelectionProcessRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateProcessStageStepDefs {

    final StepDefs stepDefs;
    final ProcessStageRepository processStageRepository;
    final SelectionProcessRepository selectionProcessRepository;
    private String newResourceUri;

    public UpdateProcessStageStepDefs(StepDefs stepDefs, ProcessStageRepository processStageRepository, SelectionProcessRepository selectionProcessRepository) {
        this.stepDefs = stepDefs;
        this.processStageRepository = processStageRepository;
        this.selectionProcessRepository = selectionProcessRepository;
    }

    @When("I change the step of the process stage with id {string} to {int}")
    public void iChangeStepOfProcessStageWithId(String id, int step) throws Throwable {
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
    public void thePreviouslyUpdatedProcessStageHasNowStep(int newStep) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.step", is(newStep)));
    }

    @And("I associate the processStage with id {int} as active into to the selection process with vacancy {string}")
    public void associateProcessStageActiveToSelectionProcess(int id, String vacancy) throws Throwable {
        SelectionProcess selectionProcess = selectionProcessRepository.findByVacancy(vacancy).get(0);
        newResourceUri = "/selectionProcesses/"+ selectionProcess.getId() + "/activeProcessStage";

        stepDefs.result = stepDefs.mockMvc.perform(
                put(newResourceUri)
                        .contentType("text/uri-list")
                        .characterEncoding("utf-8")
                        .content(processStageRepository.findById((long) id).get().getUri())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print())
        .andExpect(status().is2xxSuccessful());
    }

    @Then("The previously updated selection process has now an active process stage with id {int}")
    public void thePreviouslyUpdatedSelectionProessHasActiveProcessStageWithId(int id) throws Throwable {
        newResourceUri = newResourceUri + "/activeProcessStage";
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.uri", is("/processStages/" + id)));
    }
}
