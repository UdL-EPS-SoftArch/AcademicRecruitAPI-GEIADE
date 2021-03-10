package cat.udl.eps.softarch.academicrecruit.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "AcademicRecruitCandidate") //Avoid collision with system table User in Postgres
@Data
@EqualsAndHashCode(callSuper = true)

public class Candidate extends User {

    @NotNull
    @NotBlank
    @Length(max = 32)
    private String name;

    @Id
    @NotNull
    @NotBlank
    @Column(unique = true)
    @Length(max = 32)
    private String dni;

    @Id
    @GeneratedValue
    private String id;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) { this.id = id; }
}





