package cat.udl.eps.softarch.academicrecruit.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Participant extends UriEntity<Long> {

    public enum Role {
        SECRETARY, PRESIDENT, VOCAL
    }

    @Id @GeneratedValue
    private long id;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}


