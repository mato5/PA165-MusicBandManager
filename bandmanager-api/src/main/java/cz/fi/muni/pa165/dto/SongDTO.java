package cz.fi.muni.pa165.dto;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

public class SongDTO {

    private Long id;
    private String name;
    private Long duration;
    private BandDTO band;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof SongDTO)) return false;

        SongDTO songDTO = (SongDTO) o;

        return id != null ? id.equals(songDTO.id) : songDTO.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SongDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", band=" + band +
                '}';
    }
}
