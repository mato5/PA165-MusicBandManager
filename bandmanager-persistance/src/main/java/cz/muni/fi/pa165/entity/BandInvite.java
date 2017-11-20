package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Iurii xkuznetc Kuznetcov
 */
@Entity
public class BandInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Manager manager;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Member invitedMember;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Band band;

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public Member getInvitedMember() {
        return invitedMember;
    }

    public void setInvitedMember(Member invitedMember) {
        this.invitedMember = invitedMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Manager)) return false;

        BandInvite that = (BandInvite) o;

        if (manager != null ? !manager.equals(that.manager) : that.manager != null) return false;
        if (invitedMember != null ? !invitedMember.equals(that.invitedMember) : that.invitedMember != null)
            return false;
        return band != null ? band.equals(that.band) : that.band == null;
    }

    @Override
    public int hashCode() {
        int result = manager != null ? manager.hashCode() : 0;
        result = 31 * result + (invitedMember != null ? invitedMember.hashCode() : 0);
        result = 31 * result + (band != null ? band.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BandInvite{" +
                "createdAt=" + createdAt +
                ", manager=" + manager +
                ", invitedMember=" + invitedMember +
                ", band=" + band +
                '}';
    }
}
