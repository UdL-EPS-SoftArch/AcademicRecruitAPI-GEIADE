package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.repository.DocumentRepository;
import cat.udl.eps.softarch.academicrecruit.repository.SelectionProcessRepository;
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
    final SelectionProcessRepository selectionProcessRepository;
    private String newResourceUri;

    public CreateDocumentStepDefs(StepDefs stepDefs, DocumentRepository documentRepository, SelectionProcessRepository selectionProcessRepository) {
        this.stepDefs = stepDefs;
        this.documentRepository = documentRepository;
        this.selectionProcessRepository = selectionProcessRepository;
    }

    @When("I create a document with title {string} associated to selection process with vacancy {string}")
    public void iCreateANewDocumentWithTitle(String title, String vacancy) throws Throwable {
        Document document = new Document();
        document.setTitle(title);
        document.setSelectionProcess(selectionProcessRepository.findByVacancy(vacancy).get(0));

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(document))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been created a document with title {string} associated to a selection process with vacancy {string}")
    public void itHasBeenCreatedADocumentWithTitle(String title) throws Throwable {
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(title)));
    }
}