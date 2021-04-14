package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.domain.Participant;
import cat.udl.eps.softarch.academicrecruit.repository.DocumentRepository;
import cat.udl.eps.softarch.academicrecruit.repository.ParticipantRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UpdateDocumentStepDefs {

    final StepDefs stepDefs;
    final DocumentRepository documentRepository;
    private String newResourceUri;

    public UpdateDocumentStepDefs(StepDefs stepDefs, DocumentRepository documentRepository) {
        this.stepDefs = stepDefs;
        this.documentRepository = documentRepository;
    }

    @When("I change the title of the document with id {string} to {string}")
    public void iChangeTitleOfDocumentTo(String id, String title) throws Throwable {

        Document doc = documentRepository.findById(Long.valueOf(id)).get();
        doc.setTitle(title);
        documentRepository.save(doc);

        newResourceUri = "/participants/"+ doc.getId().toString();

        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(doc))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("The previously updated document has now title {string}")
    public void thePreviouslyUpdatedDocumentHasNowTitle(String newTitle) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(newTitle)));
    }
}
