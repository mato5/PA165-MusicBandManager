package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Iurii xkuznetc Kuznetcov
 * <p>
 * Stub class for entity Member
 */
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "members")
    private Set<Band> bands = new HashSet<Band>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addBand(Band band) {
        this.bands.add(band);
    }

    public void removeBand(Band band) {
        this.bands.remove(band);
    }

    public Set<Band> getBands() {
        return Collections.unmodifiableSet(bands);
    }

    public Member() {
    }

    public Member(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        return id != null ? id.equals(member.id) : member.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", bands=" + bands +
                '}';
    }
}
