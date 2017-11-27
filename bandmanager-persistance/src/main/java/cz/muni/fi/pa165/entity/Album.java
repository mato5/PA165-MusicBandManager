package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Miroslav Kadlec
 */
@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    private String coverURI;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Band band;

    @ManyToMany
    private Set<Song> songs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCoverURI() {
        return coverURI;
    }

    public Band getBand() {
        return band;
    }

    public Set<Song> getSongs() {
        return Collections.unmodifiableSet(songs);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoverURI(String coverURI) {
        this.coverURI = coverURI;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public void deleteSong(Song song) {
        this.songs.remove(song);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Album)) return false;

        Album album = (Album) o;

        if (id != null ? !id.equals(album.id) : album.id != null) return false;
        if (name != null ? !name.equals(album.name) : album.name != null) return false;
        return band != null ? band.equals(album.band) : album.band == null;
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
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coverURI='" + coverURI + '\'' +
                ", band=" + band +
                ", songs=" + songs +
                '}';
    }
}
