package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.Candidate;
import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.repository.CandidateRepository;
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
    final CandidateRepository candidateRepository;

    private String newResourceUri;

    public CreateDocumentStepDefs(StepDefs stepDefs, DocumentRepository documentRepository, CandidateRepository candidateRepository) {
        this.stepDefs = stepDefs;
        this.documentRepository = documentRepository;
        this.candidateRepository = candidateRepository;

    }

    @When("I create a document with title {string}")
    public void iCreateANewDocumentWithTitle(String title) throws Throwable {
        Document document = new Document();
        document.setTitle(title);
        documentRepository.save(document);

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



    @When("I assign the candidate named {string} to the document titled {string}")
    public void iAssignCandidateToDocument(String cand_name, String doc_title) throws Throwable {
        Candidate candidate = new Candidate();
        candidate.setName(cand_name);
        candidateRepository.save(candidate);

        Document document = new Document();
        document.setTitle(doc_title);
        document.setCandidate(candidate);
        documentRepository.save(document);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(document))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @And("It has been assigned the Candidate named {string} to the Document titled {string}")
    public void itHasBeenAssignedACandidateToADocument(String cand_name, String doc_title) throws Throwable {

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location") + "/candidate";
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(cand_name)));
    }


    }