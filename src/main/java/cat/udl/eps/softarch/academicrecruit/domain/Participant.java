package cat.udl.eps.softarch.academicrecruit.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Participant extends UriEntity<Long> {

    public enum Role {
        SECRETARY, PRESIDENT, VOCAL
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private SelectionProcess selectionProcess;

}


