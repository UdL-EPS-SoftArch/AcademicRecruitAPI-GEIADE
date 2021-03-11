package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.repository.SelectionProcessRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

public class UpdateSelectionProcessStepDefs {

    final StepDefs stepDefs;
    final SelectionProcessRepository selectionProcessRepository;
    private String newResourceUri;

    public UpdateSelectionProcessStepDefs(StepDefs stepDefs, SelectionProcessRepository selectionProcessRepository) {
        this.stepDefs = stepDefs;
        this.selectionProcessRepository = selectionProcessRepository;
    }

    @When("I change vacancy of selection process with id {string} to {string}")
    public void iChangeVacancyOfSelectionProcessTo(String id, String vacancy) throws Throwable {
        newResourceUri = "/selectionProcesses/".concat(id);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((new JSONObject().put("vacancy", vacancy)).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("The previously updated selection process has now vacancy {string}")
    public void thePreviouslyUpdatedSelectionProcessHasNowVacancy(String newVacancy) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.vacancy", is(newVacancy)));
    }
}