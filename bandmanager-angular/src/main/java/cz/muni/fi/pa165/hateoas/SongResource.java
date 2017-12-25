package cz.muni.fi.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.SongDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@Relation(value = "song", collectionRelation = "songs")
public class SongResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;

    private String name;
    private Long duration;
    private BandDTO band;

    public SongResource(SongDTO songDTO) {
        this.dtoId = songDTO.getId();
        this.name = songDTO.getName();
        this.duration = songDTO.getDuration();
        this.band = songDTO.getBand();
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

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public BandDTO getBand() {
        return band;
    }

    public void setBand(BandDTO band) {
        this.band = band;
    }
}
