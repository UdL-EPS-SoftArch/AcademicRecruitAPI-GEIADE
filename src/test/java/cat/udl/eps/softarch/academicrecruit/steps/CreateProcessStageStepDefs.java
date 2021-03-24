package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.Participant;
import cat.udl.eps.softarch.academicrecruit.domain.ProcessStage;
import cat.udl.eps.softarch.academicrecruit.repository.ParticipantRepository;
import cat.udl.eps.softarch.academicrecruit.repository.ProcessStageRepository;
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

    public CreateProcessStageStepDefs(StepDefs stepDefs, ProcessStageRepository processStageRepository) {
        this.stepDefs = stepDefs;
        this.processStageRepository = processStageRepository;
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
}
