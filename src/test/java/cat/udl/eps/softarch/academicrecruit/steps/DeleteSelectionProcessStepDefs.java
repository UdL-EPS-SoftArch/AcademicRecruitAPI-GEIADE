package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.SelectionProcess;
import cat.udl.eps.softarch.academicrecruit.repository.SelectionProcessRepository;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class DeleteSelectionProcessStepDefs {

    final StepDefs stepDefs;
    final SelectionProcessRepository selectionProcessRepository;
    private String newResourceUri;

    public DeleteSelectionProcessStepDefs(StepDefs stepDefs, SelectionProcessRepository selectionProcessRepository) {
        this.stepDefs = stepDefs;
        this.selectionProcessRepository = selectionProcessRepository;
    }

    @When("I create a selection process with vacancy {string}")
    public void iCreateANewSelectionProcessWithVacancy(String vacancy) throws Throwable {
        SelectionProcess selectionProcess = new SelectionProcess();
        selectionProcess.setVacancy(vacancy);

        newResourceUri = "/selectionProcesses";
        stepDefs.result = stepDefs.mockMvc.perform(
                post(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(selectionProcess))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @Then("I delete the selection process I have just created with vacancy {string}")
    public void iDeleteANewSelectionProcessWithVacancy(String vacancy) throws Throwable {
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");

        stepDefs.result = stepDefs.mockMvc.perform(
                delete(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

}
