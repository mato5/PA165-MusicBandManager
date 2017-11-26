package cz.fi.muni.pa165.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

public class SongCreateDTO {

    @NotNull
    @Size(min = 5, max = 50)
    private String name;

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

    public Long getBandId() {
        return bandId;
    }

    public void setBandId(Long bandId) {
        this.bandId = bandId;
    }

    @NotNull
    @Min(1)
    private Long duration;

    @NotNull
    private Long bandId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof SongCreateDTO)) return false;

        SongCreateDTO that = (SongCreateDTO) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return bandId != null ? bandId.equals(that.bandId) : that.bandId == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (bandId != null ? bandId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SongCreateDTO{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", bandId=" + bandId +
                '}';
    }
}
