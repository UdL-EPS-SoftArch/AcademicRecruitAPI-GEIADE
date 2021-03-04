package cat.udl.eps.softarch.academicrecruit.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Document extends UriEntity<String>{
    public enum DocumentTypes{
        RESOLUCIO,
        CRITERIS_AVALUACIO,
        ALTRES
    }

    @Id
    private String name;

    public String docType;

    private String path;

    private int length;

    private String mime;




    public void setDocType(DocumentTypes type){
        switch (type){
            case ALTRES:
                this.docType = "Altres";
                break;

            case RESOLUCIO:
                this.docType = "Resolució";
                break;

            case CRITERIS_AVALUACIO:
                this.docType = "Criteris d'avaluació";
                break;
        }

    }

    public String getPath(){
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLength(){
        return this.length;
    }


    @Override
    public String getId() {
        return this.name;
    }
}
