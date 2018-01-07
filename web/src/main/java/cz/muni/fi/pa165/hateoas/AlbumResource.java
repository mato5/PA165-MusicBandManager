package cz.muni.fi.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.fi.muni.pa165.dto.AlbumDTO;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.SongDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@Relation(value = "album", collectionRelation = "albums")
public class AlbumResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;
    private String name;
    private String coverURI;
    private BandDTO band;
    private Set<SongDTO> songs = new HashSet<>();

    public AlbumResource(AlbumDTO albumDTO) {
        this.dtoId = albumDTO.getId();
        this.name = albumDTO.getName();
        this.coverURI = albumDTO.getCoverURI();
        this.band = albumDTO.getBand();
        this.songs = albumDTO.getSongs();
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

    public String getCoverURI() {
        return coverURI;
    }

    public void setCoverURI(String coverURI) {
        this.coverURI = coverURI;
    }

    public BandDTO getBand() {
        return band;
    }

    public void setBand(BandDTO band) {
        this.band = band;
    }

    public Set<SongDTO> getSongs() {
        return songs;
    }

    public void setSongs(Set<SongDTO> songs) {
        this.songs = songs;
    }
}
