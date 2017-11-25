package cz.fi.muni.pa165.dto;

import java.util.Set;

/**
 * @author Matej Sojak 433294
 */
public class ManagerDTO extends UserDTO {

    private Set<BandDTO> bands;
    private Set<TourDTO> tours;
    private Set<BandInviteDTO> bandInviteDTOs;

    public Set<BandDTO> getBands() {
        return bands;
    }

    public void setBands(Set<BandDTO> bands) {
        this.bands = bands;
    }

    public Set<TourDTO> getTours() {
        return tours;
    }

    public void setTours(Set<TourDTO> tours) {
        this.tours = tours;
    }

    public Set<BandInviteDTO> getBandInviteDTOs() {
        return bandInviteDTOs;
    }

    public void setBandInviteDTOs(Set<BandInviteDTO> bandInviteDTOs) {
        this.bandInviteDTOs = bandInviteDTOs;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getEmail() == null) ? 0 : this.getEmail().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MemberDTO other = (MemberDTO) obj;
        if (this.getEmail() == null) {
            if (other.getEmail() != null)
                return false;
        } else if (!this.getEmail().equals(other.getEmail()))
            return false;
        return true;
    }
}