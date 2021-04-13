package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.repository.DocumentRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateDocumentStepDefs {

    final StepDefs stepDefs;
    final DocumentRepository documentRepository;
    private String newResourceUri;

    public CreateDocumentStepDefs(StepDefs stepDefs, DocumentRepository documentRepository) {
        this.stepDefs = stepDefs;
        this.documentRepository = documentRepository;
    }

    @When("I create a document with title {string}")
    public void iCreateANewDocumentWithTitle(String title) throws Throwable {
        Document document = new Document();
        document.setTitle(title);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(document))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been created a document with title {string}")
    public void itHasBeenCreatedADocumentWithTitle(String title) throws Throwable {
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(title)));
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

    @And("It has been created a processStage associated to selection process with vacancy {string}")
    public void itHasBeenCreatedAProcessStageWithStepAndNameAndVacancy(String vacancy) throws Throwable {
        newResourceUri = newResourceUri + "/selectionProcess";
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.vacancy", is(vacancy)));
    }

}