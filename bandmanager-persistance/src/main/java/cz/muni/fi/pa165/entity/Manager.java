package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Iurii xkuznetc Kuznetcov
 * <p>
 * Stub class for entity Manager
 */
@Entity
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
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

    public Set<Band> getBands() {
        return Collections.unmodifiableSet(bands);
    }

    public void removeBand(Band band) {
        this.bands.remove(band);
    }

    public Manager() {
    }

    public Manager(Long id) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Manager manager = (Manager) o;

        return id == manager.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", bands=" + bands +
                '}';
    }
}
