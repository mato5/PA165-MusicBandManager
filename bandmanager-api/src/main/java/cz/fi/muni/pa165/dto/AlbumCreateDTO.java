package cz.fi.muni.pa165.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author Iurii xkuznetc Kuznetcov
 */

public class AlbumCreateDTO {

    @NotNull
    @Size(min = 5, max = 50)
    private String name;

    private String coverURI;

    @NotNull
    private Long bandId;

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

    public Long getBandId() {
        return bandId;
    }

    public void setBandId(Long bandId) {
        this.bandId = bandId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof AlbumCreateDTO)) return false;

        AlbumCreateDTO that = (AlbumCreateDTO) o;

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
        return "AlbumCreateDTO{" +
                "name='" + name + '\'' +
                ", coverURI='" + coverURI + '\'' +
                ", bandId=" + bandId +
                '}';
    }
}
