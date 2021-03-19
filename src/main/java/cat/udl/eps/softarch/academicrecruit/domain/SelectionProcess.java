package cat.udl.eps.softarch.academicrecruit.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class SelectionProcess extends UriEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    @Length(max = 256)
    private String vacancy;
}
