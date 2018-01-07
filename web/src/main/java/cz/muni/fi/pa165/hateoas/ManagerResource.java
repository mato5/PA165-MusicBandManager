package cz.muni.fi.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.BandInviteDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.dto.TourDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Set;

@Relation(value = "manager", collectionRelation = "managers")
public class ManagerResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;
    private String name;
    private String email;
    private String password;
    private Set<BandDTO> bands;
    private Set<TourDTO> tours;
    private Set<BandInviteDTO> bandInvites;

    public ManagerResource(ManagerDTO managerDTO){
        this.dtoId = managerDTO.getId();
        this.name = managerDTO.getName();
        this.email = managerDTO.getEmail();
        this.password = managerDTO.getPassword();
        this.bands = managerDTO.getBands();
        this.tours = managerDTO.getTours();
        this.bandInvites = managerDTO.getBandInvites();
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

    public Set<BandInviteDTO> getBandInvites() {
        return bandInvites;
    }

    public void setBandInvites(Set<BandInviteDTO> bandInvites) {
        this.bandInvites = bandInvites;
    }
}
