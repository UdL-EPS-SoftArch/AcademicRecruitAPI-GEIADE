package cat.udl.eps.softarch.academicrecruit.steps;

        import cat.udl.eps.softarch.academicrecruit.domain.Candidate;
        import cat.udl.eps.softarch.academicrecruit.domain.Participant;
        import cat.udl.eps.softarch.academicrecruit.repository.CandidateRepository;
        import cat.udl.eps.softarch.academicrecruit.repository.ParticipantRepository;
        import io.cucumber.java.en.And;
        import io.cucumber.java.en.When;
        import org.springframework.http.MediaType;

        import static org.hamcrest.Matchers.is;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
        import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DeleteCandidateStepDefs {
    final StepDefs stepDefs;
    final CandidateRepository candidateRepository;
    private String newResourceUri;

    public DeleteCandidateStepDefs(StepDefs stepDefs, CandidateRepository candidateRepository) {
        this.stepDefs = stepDefs;
        this.candidateRepository = candidateRepository;
    }


    @When("I delete a candidate with name {string}")
    public void iDeleteCandidateTo(String name) throws Throwable {

        Candidate candidate = new Candidate();
        candidate.setName(name);
        candidateRepository.save(candidate);

        newResourceUri = "/candidates/"+ candidate.getId();

        stepDefs.result = stepDefs.mockMvc.perform(
                delete(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(candidate))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("The previously deleted candidate doesn't exist")
    public void thePreviouslyDeletedCandidateNotInDataBase() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get (newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
