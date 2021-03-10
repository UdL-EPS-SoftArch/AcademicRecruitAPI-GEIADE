package cat.udl.eps.softarch.academicrecruit.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Document extends UriEntity<String> {
    private enum DocumentTypes {
        RESOLUCIO,
        CRITERIS_AVALUACIO,
        ALTRES
    }

    @Id
    private int id;

    private String name;

    public String docType;

    private String path;

    private int length;

    private String mime;



    /*************** Setters ****************/
    public void setName(String name){ this.name = name; }

    public void setDocType(DocumentTypes type) {
        switch (type) {
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

    public void setPath(String path) {  this.path = path; }

    public void setLength(int length){ this.length = length;  }

    public void setMime(String mime){ this.mime = mime;  }


    /*************** Getters ****************/
    public String getName(){ return this.name; }

    public String getDocType(){ return this.docType; }

    public String getPath() { return this.path; }

    public int getLength() { return this.length; }

    public String getMime(){ return this.mime; }


}

