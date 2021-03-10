package cat.udl.eps.softarch.academicrecruit.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;


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


}





