package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.SelectionProcess;
import cat.udl.eps.softarch.academicrecruit.repository.SelectionProcessRepository;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ReadSelectionProcessStepDefs {

    final StepDefs stepDefs;
    final SelectionProcessRepository selectionProcessRepository;
    private String newResourceUri;

    public ReadSelectionProcessStepDefs(StepDefs stepDefs, SelectionProcessRepository selectionProcessRepository) {
        this.stepDefs = stepDefs;
        this.selectionProcessRepository = selectionProcessRepository;
    }

    @When("I read the selection process I have just created with vacancy {string}")
    public void iReadANewSelectionProcessWithVacancy(String vacancy) throws Throwable {
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");

        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.vacancy", is(vacancy)));


    }
}