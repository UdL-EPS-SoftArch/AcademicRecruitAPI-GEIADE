package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.repository.DocumentRepository;
import cat.udl.eps.softarch.academicrecruit.repository.SelectionProcessRepository;
import cat.udl.eps.softarch.academicrecruit.service.FileStorageService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UploadDocumentStepDefs {
    final StepDefs stepDefs;
    final DocumentRepository documentRepository;
    final SelectionProcessRepository selectionProcessRepository;
    final FileStorageService fileStorageService;

    private Resource deletedFile;

    public UploadDocumentStepDefs(StepDefs stepDefs, DocumentRepository documentRepository, SelectionProcessRepository selectionProcessRepository, FileStorageService fileStorageService) {
        this.stepDefs = stepDefs;
        this.documentRepository = documentRepository;
        this.selectionProcessRepository = selectionProcessRepository;
        this.fileStorageService = fileStorageService;
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
        deletedFile = fileStorageService.load(document);
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

        /* Verifying file has been uploaded and saved to the disk with the same content */
        Resource resource = fileStorageService.load(document);
        Assert.assertNotNull(resource);
        Assert.assertTrue(resource.exists());
        Assert.assertEquals(Files.readString(resource.getFile().toPath()), "A text plain file example");

        /* Verifying the GET request downloads the file */
        stepDefs.result = stepDefs.mockMvc.perform(
                MockMvcRequestBuilders.get("/files/" + documentId)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes("A text plain file example".getBytes()))
                .andExpect(status().is2xxSuccessful());
    }

    @And("The file of document with with id {int} doesn't exist anymore")
    public void theFileIsntOnDiskAnymore(int documentId) throws Throwable {
        Document document = documentRepository.findById((long) documentId).get();

        Assert.assertFalse(deletedFile.exists());
        Assert.assertNull(document.getPath());
    }

}