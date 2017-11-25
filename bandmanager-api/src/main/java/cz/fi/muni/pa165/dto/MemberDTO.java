package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.utils.Role;

import java.util.Set;

/**
 * @author Matej Sojak 433294
 */
public class MemberDTO extends UserDTO {

    private Role role;
    private BandDTO bandDTO;
    private Set<BandInviteDTO> bandInviteDTO;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BandDTO getBandDTO() {
        return bandDTO;
    }

    public void setBandDTO(BandDTO bandDTO) {
        this.bandDTO = bandDTO;
    }

    public Set<BandInviteDTO> getBandInviteDTO() {
        return bandInviteDTO;
    }

    public void setBandInviteDTO(Set<BandInviteDTO> bandInviteDTO) {
        this.bandInviteDTO = bandInviteDTO;
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
