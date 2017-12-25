package cz.muni.fi.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.BandInviteDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.dto.TourDTO;
import cz.muni.fi.pa165.enums.Genre;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Set;

@Relation(value = "band", collectionRelation = "bands")
public class BandResource extends ResourceSupport {

    @JsonProperty("id")
    private Long dtoId;
    private String name;
    private String logoURI;
    private Genre genre;

    public BandResource(BandDTO bandDTO){
        this.dtoId = bandDTO.getId();
        this.name = bandDTO.getName();
        this.logoURI = bandDTO.getLogoURI();
        this.genre = bandDTO.getGenre();
    }

    public Long getDtoId() {
        return dtoId;
    }

    public void setDtoId(Long dtoId) {
        this.dtoId = dtoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoURI() {
        return logoURI;
    }

    public void setLogoURI(String logoURI) {
        this.logoURI = logoURI;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
