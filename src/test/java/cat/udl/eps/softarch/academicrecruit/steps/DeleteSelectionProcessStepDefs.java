package cat.udl.eps.softarch.academicrecruit.steps;
import static org.junit.Assert.*;

import cat.udl.eps.softarch.academicrecruit.domain.SelectionProcess;
import cat.udl.eps.softarch.academicrecruit.repository.SelectionProcessRepository;

import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DeleteSelectionProcessStepDefs {

    final StepDefs stepDefs;
    final SelectionProcessRepository selectionProcessRepository;
    private String newResourceUri;

    public DeleteSelectionProcessStepDefs(StepDefs stepDefs, SelectionProcessRepository selectionProcessRepository) {
        this.stepDefs = stepDefs;
        this.selectionProcessRepository = selectionProcessRepository;
    }

    @When("I delete the selection process I have just created with vacancy {string}")
    public void iDeleteANewSelectionProcessWithVacancy(String vacancy) throws Throwable {
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");

        stepDefs.result = stepDefs.mockMvc.perform(
                delete(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        assertEquals(selectionProcessRepository.findByVacancy(vacancy), Collections.emptyList());

    }
}
