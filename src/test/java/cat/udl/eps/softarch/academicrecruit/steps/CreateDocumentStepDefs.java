package cat.udl.eps.softarch.academicrecruit.steps;
import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.repository.CandidateRepository;
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
    final CandidateRepository candidateRepository;
    final SelectionProcessRepository selectionProcessRepository;
    private String newResourceUri;

    public CreateDocumentStepDefs(StepDefs stepDefs, DocumentRepository documentRepository, SelectionProcessRepository selectionProcessRepository,  CandidateRepository candidateRepository) {
        this.stepDefs = stepDefs;
        this.documentRepository = documentRepository;
        this.selectionProcessRepository = selectionProcessRepository;
        this.candidateRepository = candidateRepository;

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
    @When("I create the document titled {string} with the candidate named {string} assigned")
    public void iAssignCandidateToDocument(String doc_title, String cand_name) throws Throwable {
        Document document = new Document();
        document.setTitle(doc_title);
        document.setCandidate(candidateRepository.findByName(cand_name).get(0));

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(document))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @And("It has been assigned the Candidate named {string} to the document")
    public void itHasBeenAssignedACandidateToADocument(String cand_name) throws Throwable {
        newResourceUri = newResourceUri + "/candidate";
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(cand_name)));
    }


    @When("I create a document with title {string} associated to selection process with vacancy {string}")
    public void iCreateANewDocumentWithTitleAndVacancy(String title, String vacancy) throws Throwable {
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
    @And("It has been created a document associated to selection process with vacancy {string}")
    public void itHasBeenCreatedADocumentWithTitleAndVacancy(String vacancy) throws Throwable {
        newResourceUri = newResourceUri + "/selectionProcess";
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.vacancy", is(vacancy)));
    }

    @When("A Document is created with title {string}")
    public void aDocumentIsCreatedByUser(String title) throws Throwable {
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

    @And("It has been created a Document by User with username {string}")
    public void itHasBeenCreatedADocumentWithUser(String username) throws Throwable {
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location") + "/user";
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(username)));
    }


}






