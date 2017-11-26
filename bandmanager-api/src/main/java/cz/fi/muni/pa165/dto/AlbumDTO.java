package cz.fi.muni.pa165.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

public class AlbumDTO {

    private Long id;
    private String name;
    private String coverURI;
    private BandDTO band;
    private Set<SongDTO> songs = new HashSet<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof AlbumDTO)) return false;

        AlbumDTO albumDTO = (AlbumDTO) o;

        if (id != null ? !id.equals(albumDTO.id) : albumDTO.id != null) return false;
        if (name != null ? !name.equals(albumDTO.name) : albumDTO.name != null) return false;
        return band != null ? band.equals(albumDTO.band) : albumDTO.band == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (band != null ? band.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AlbumDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coverURI='" + coverURI + '\'' +
                ", band=" + band +
                ", songs=" + songs +
                '}';
    }
}
