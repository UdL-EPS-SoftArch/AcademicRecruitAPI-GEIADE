package cat.udl.eps.softarch.academicrecruit.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Data


public class Candidate extends UriEntity<String>{


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

    public Candidate() {

    }


    @Override
    public String getId() {
        return this.dni;

    }
    public Candidate(String dni){
        this.dni= dni;

    }
}





