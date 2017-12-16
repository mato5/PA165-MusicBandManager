package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invitedMember")
    private Set<BandInvite> bandInvites = new HashSet<BandInvite>();

    public void addBandInvite(BandInvite bandInvite) {
        this.bandInvites.add(bandInvite);
        bandInvite.setInvitedMember(this);
    }

    public Set<BandInvite> getBandInvites() {
        return Collections.unmodifiableSet(bandInvites);
    }

    public void removeBandInvite(BandInvite bandInvite) {
        bandInvites.remove(bandInvite);
    }

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

    @Override
    public String toString() {
        return "Member{" +
                getEmail() +
                '}';
    }
}
