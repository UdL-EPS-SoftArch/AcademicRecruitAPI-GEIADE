package cat.udl.eps.softarch.academicrecruit.steps;

import cat.udl.eps.softarch.academicrecruit.domain.Participant;
import cat.udl.eps.softarch.academicrecruit.domain.ProcessStage;
import cat.udl.eps.softarch.academicrecruit.domain.SelectionProcess;
import cat.udl.eps.softarch.academicrecruit.repository.ParticipantRepository;
import cat.udl.eps.softarch.academicrecruit.repository.UserRepository;
import cat.udl.eps.softarch.academicrecruit.repository.SelectionProcessRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import java.net.URI;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateParticipantStepDefs {

    final StepDefs stepDefs;
    final ParticipantRepository participantRepository;
    final SelectionProcessRepository selectionProcessRepository;
    final UserRepository userRepository;
    String newResourceUri;

    public CreateParticipantStepDefs(StepDefs stepDefs, ParticipantRepository participantRepository, UserRepository userRepository, SelectionProcessRepository selectionProcessRepository) {
        this.stepDefs = stepDefs;
        this.participantRepository = participantRepository;
        this.selectionProcessRepository = selectionProcessRepository;
        this.userRepository = userRepository;
    }

    @When("I create a participant with role {string}")
    public void iCreateANewDatasetWithTitleAndDescription(String role) throws Throwable {
        Participant participant = new Participant();
        participant.setRole(Participant.Role.valueOf(role));

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(participant))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been created a participant with role {string}")
    public void itHasBeenCreatedAParticipantWithRoleAndIsProvidedBy(String role) throws Throwable {
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.role", is(role)));
    }

    @And("I associate the previous created participant to user with username {string}")
    public void associatedAParticipantToUserWithUsername(String username) throws Throwable {
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");

        URI uri = new URI(newResourceUri);
        String path = uri.getPath();
        String idStr = path.substring(path.lastIndexOf('/') + 1);
        Long id = Long.parseLong(idStr);

        Participant participant = participantRepository.findById(id).get();
        participant.setUser(userRepository.findById(username).get());

        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(stepDefs.mapper.writeValueAsString(participant))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been created a participant associated to user with username {string}")
    public void itHasBeenAssociatedAParticipantToUserWithUsername(String username) throws Throwable {
        newResourceUri = newResourceUri + "/user";
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(username)));
    }



    @When("I create a participant with with role {string} associated to selection process with vacancy {string}")
    public void iCreateANewParticipantWithRoleAndVacancy(String role, String vacancy) throws Throwable {
        Participant participant = new Participant();
        participant.setRole(Participant.Role.valueOf(role));
        participant.setSelectionProcess(selectionProcessRepository.findByVacancy(vacancy).get(0));

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(participant))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been created a participant associated to selection process with vacancy {string}")
    public void itHasBeenCreatedAParticipantWithRoleAndVacancy(String vacancy) throws Throwable {
        newResourceUri = newResourceUri + "/selectionProcess";
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.vacancy", is(vacancy)));
    }
}
