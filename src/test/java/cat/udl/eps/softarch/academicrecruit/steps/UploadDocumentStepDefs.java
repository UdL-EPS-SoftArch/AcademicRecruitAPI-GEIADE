package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.repository.DocumentRepository;
import cat.udl.eps.softarch.academicrecruit.repository.SelectionProcessRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UploadDocumentStepDefs {
    final StepDefs stepDefs;
    final DocumentRepository documentRepository;
    final SelectionProcessRepository selectionProcessRepository;

    public UploadDocumentStepDefs(StepDefs stepDefs, DocumentRepository documentRepository, SelectionProcessRepository selectionProcessRepository) {
        this.stepDefs = stepDefs;
        this.documentRepository = documentRepository;
        this.selectionProcessRepository = selectionProcessRepository;
    }

    @When("I upload a empty text file to document with id {int}")
    public void iUploadANewDocumentWithId(int documentId) throws Throwable {
        Document document = documentRepository.findById((long) documentId).get();

        MockMultipartFile exampleFile = new MockMultipartFile("file", "filename.txt", "text/plain", "A text plain file example".getBytes());

        stepDefs.result = stepDefs.mockMvc.perform(
                MockMvcRequestBuilders.multipart("/files/" + document.getId())
                        .file(exampleFile)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("I delete the text file of document with with id {int}")
    public void iDeleteTheDocumentWithId(int documentId) throws Throwable {
        Document document = documentRepository.findById((long) documentId).get();

        stepDefs.result = stepDefs.mockMvc.perform(
                MockMvcRequestBuilders.delete("/files/" + document.getId())
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been uploaded a document id {int}")
    public void itHasBeenUploadedADocumentWithId(int documentId) throws Throwable {
        Document document = documentRepository.findById((long) documentId).get();
        System.out.println(document.getId());
        System.out.println(document.getPath());
        stepDefs.result = stepDefs.mockMvc.perform(
                MockMvcRequestBuilders.get("/files/" + documentId)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().is2xxSuccessful());
    }

}