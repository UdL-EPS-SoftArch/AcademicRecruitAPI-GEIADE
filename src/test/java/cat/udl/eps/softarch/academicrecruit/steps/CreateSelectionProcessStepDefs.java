package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.SelectionProcess;
import cat.udl.eps.softarch.academicrecruit.repository.SelectionProcessRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateSelectionProcessStepDefs {

    final StepDefs stepDefs;
    final SelectionProcessRepository selectionProcessRepository;
    private String newResourceUri;

    public CreateSelectionProcessStepDefs(StepDefs stepDefs, SelectionProcessRepository selectionProcessRepository) {
        this.stepDefs = stepDefs;
        this.selectionProcessRepository = selectionProcessRepository;
    }

    @When("I create a selection process with vacancy {string}")
    public void iCreateANewSelectionProcessWithVacancy(String vacancy) throws Throwable {
        SelectionProcess selectionProcess = new SelectionProcess();
        selectionProcess.setVacancy(vacancy);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/selection-processes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(selectionProcess))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("It has been created a selection process with vacancy {string}")
    public void itHasBeenCreatedASelectionProcessWithVacancy(String vacancy) throws Throwable {
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.vacancy", is(vacancy)));
    }
}