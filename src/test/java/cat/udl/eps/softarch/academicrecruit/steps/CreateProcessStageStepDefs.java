package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.ProcessStage;
import cat.udl.eps.softarch.academicrecruit.repository.ProcessStageRepository;
import cat.udl.eps.softarch.academicrecruit.repository.SelectionProcessRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateProcessStageStepDefs {

    final StepDefs stepDefs;
    final ProcessStageRepository processStageRepository;
    final SelectionProcessRepository selectionProcessRepository;

    public CreateProcessStageStepDefs(StepDefs stepDefs, ProcessStageRepository processStageRepository, SelectionProcessRepository selectionProcessRepository) {
        this.stepDefs = stepDefs;
        this.processStageRepository = processStageRepository;
        this.selectionProcessRepository = selectionProcessRepository;
    }

    @When("I create a processStage with name {string} and step {int}")
    public void iCreateANewProcessStageWithNameAndStep(String name, int step) throws Throwable {
        ProcessStage processStage = new ProcessStage();
        processStage.setStep(step);
        processStage.setName(name);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/processStages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(processStage))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been created a processStage with name {string} and step {int}")
    public void itHasBeenCreatedAProcessStageWithStepAndName(String name, int step) throws Throwable {
        String newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.step", is(step)));
    }

    @When("I create a processStage with name {string} and step {int} associated to selection process with vacancy {string}")
    public void iCreateANewProcessStageWithNameAndStepAndVacancy(String name, int step, String vacancy) throws Throwable {
        ProcessStage processStage = new ProcessStage();
        processStage.setStep(step);
        processStage.setName(name);
        processStage.setSelectionProcess(selectionProcessRepository.findByVacancy(vacancy).get(0));

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/processStages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(processStage))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been created a processStage with name {string} and step {int} associated to selection process with vacancy {string}")
    public void itHasBeenCreatedAProcessStageWithStepAndNameAndVacancy(String name, int step, String vacancy) throws Throwable {
        String newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.step", is(step)));
    }
}
