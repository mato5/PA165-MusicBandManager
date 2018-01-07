package cz.muni.fi.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.BandInviteDTO;
import cz.fi.muni.pa165.dto.MemberDTO;
import cz.muni.fi.pa165.enums.Role;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Set;

@Relation(value = "member", collectionRelation = "members")
@JsonPropertyOrder({"id", "name"})
public class MemberResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;
    private String name;
    private String email;
    private String password;
    private Role role;
    private BandDTO band;
    private Set<BandInviteDTO> bandInvites;

    public MemberResource(MemberDTO memberDTO){
        dtoId = memberDTO.getId();
        name = memberDTO.getName();
        email = memberDTO.getEmail();
        password = memberDTO.getPassword();
        role = memberDTO.getRole();
        band = memberDTO.getBand();
        bandInvites = memberDTO.getBandInvites();
    }

    public long getDtoId() {
        return dtoId;
    }

    public void setDtoId(long dtoId) {
        this.dtoId = dtoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BandDTO getBand() {
        return band;
    }

    public void setBand(BandDTO band) {
        this.band = band;
    }

    public Set<BandInviteDTO> getBandInvites() {
        return bandInvites;
    }

    public void setBandInvites(Set<BandInviteDTO> bandInvites) {
        this.bandInvites = bandInvites;
    }
}
