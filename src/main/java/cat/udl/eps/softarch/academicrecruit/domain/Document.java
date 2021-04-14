package cat.udl.eps.softarch.academicrecruit.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Document extends UriEntity<Long> {
    private enum DocumentType {
        RESOLUCIO, CRITERIS_AVALUACIO, ALTRES
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Enumerated(EnumType.ORDINAL)
    public DocumentType docType;

    private String path;

    private int length;

    private String mime;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

}

