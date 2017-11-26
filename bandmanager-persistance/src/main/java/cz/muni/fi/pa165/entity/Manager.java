package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Iurii xkuznetc Kuznetcov
 */
@Entity
public class Manager extends User {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
    private Set<Band> bands = new HashSet<Band>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
    private Set<Tour> tours = new HashSet<Tour>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
    private Set<BandInvite> bandInvites = new HashSet<BandInvite>();

    public void addBandInvite(BandInvite bandInvite) {
        this.bandInvites.add(bandInvite);
        bandInvite.setManager(this);
    }

    public Set<BandInvite> getBandInvites() {
        return Collections.unmodifiableSet(bandInvites);
    }

    public void removeBandInvite(BandInvite bandInvite) {
        bandInvites.remove(bandInvite);
    }

    public void addTour(Tour tour) {
        this.tours.add(tour);
        tour.setManager(this);
    }

    public Set<Tour> getTours() {
        return Collections.unmodifiableSet(tours);
    }

    public void removeTour(Tour tour) {
        tours.remove(tour);
    }

    public void addBand(Band band) {
        this.bands.add(band);
        band.setManager(this);
    }

    public Set<Band> getBands() {
        return Collections.unmodifiableSet(bands);
    }

    public void removeBand(Band band) {
        this.bands.remove(band);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Manager) {
            Manager manager = (Manager) o;
            if ((Objects.equals(this.getEmail(), manager.getEmail()))
                    && (Objects.equals(manager.getName(), this.getName()))) {
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
        return "Manager{" +
                "bands=" + bands +
                '}';
    }
}
