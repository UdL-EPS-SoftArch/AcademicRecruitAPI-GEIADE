package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.domain.User;
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
    @When("A Document is created by User {string}")
    public void aDocumentIsCreatedByUser(User user) throws Throwable {
        Document document = new Document();
        document.setUser(user);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(document))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been created a Document by User {string}")
    public void itHasBeenCreatedADocumentWithUser(Document document) throws Throwable {
        newResourceUri = newResourceUri + "/document";
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.document", is(document)));
    }

}