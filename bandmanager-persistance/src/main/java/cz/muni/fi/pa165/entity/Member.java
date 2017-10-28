package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.enums.Role;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Matej Sojak 433294
 */
@Entity
public class Member extends User {

    @Enumerated
    @NotNull
    private Role role;

    @ManyToOne
    private Band band;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Member) {
            Member member = (Member) o;
            if ((Objects.equals(this.getEmail(), member.getEmail()))
                    && (Objects.equals(member.getName(), this.getName()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getEmail() == null) ? 0 : this.getEmail().hashCode());
        return result;
    }
}
