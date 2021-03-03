package cat.udl.eps.softarch.academicrecruit.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Participant extends UriEntity<UUID> {

    public enum Role {
        SECRETARY, PRESIDENT, VOCAL
    }

    @Id
    private UUID id;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}


