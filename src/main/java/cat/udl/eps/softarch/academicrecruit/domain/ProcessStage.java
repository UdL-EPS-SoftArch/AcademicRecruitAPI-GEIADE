package cat.udl.eps.softarch.academicrecruit.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessStage extends UriEntity<Long> {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(min = 4, max = 256)
    private String name;

    @NotNull
    @Min(1)
    @Max(3)
    private int step;

    private Date beginDate;
    private Date endDate;

    @ManyToOne
    private SelectionProcess selectionProcess;

    @OneToOne(mappedBy = "activeProcessStage")
    private SelectionProcess selectionProcessBeingActive;
}