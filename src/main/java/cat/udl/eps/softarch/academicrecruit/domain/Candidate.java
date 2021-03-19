package cat.udl.eps.softarch.academicrecruit.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Candidate extends UriEntity<Long> {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

}

