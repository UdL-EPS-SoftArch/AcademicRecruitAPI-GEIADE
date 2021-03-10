package cat.udl.eps.softarch.academicrecruit.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "AcademicRecruitSelectionProcess") //Avoid collision with system table User in Postgres
@Data
@EqualsAndHashCode(callSuper = true)
public class SelectionProcess extends UriEntity<String>{

    @NotNull
    @NotBlank
    @Length(max = 256)
    private String vacancy;

    @Id
    @GeneratedValue
    private String id;

    @Override
    public String getId() {
        return this.id;
    }

}
