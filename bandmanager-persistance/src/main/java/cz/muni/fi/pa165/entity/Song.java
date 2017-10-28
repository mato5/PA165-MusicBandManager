package cz.muni.fi.pa165.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Miroslav Kadlec
 */
@Entity
public class Song implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;
    
    private Long duration;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Band band;
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Long getDuration() {
        return this.duration;
    }
    
    public void setDuration(Long duration) {
        this.duration = duration;
    }
    
    public Band getBand() {
        return this.band;
    }
    
    public void setBand(Band band) {
        this.band = band; 
    }
    
    public Song() {
    }
    
    public Song(Long id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Song)) return false;

        Song song = (Song) o;

        if (this.name.equals(song.getName()) 
                    && this.duration.equals(song.getDuration())
                    && this.band.equals(song.getBand())) {
            return true;
        } else {
            return false;
        }
        
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Song{" +
                "id = " + id +
                ", name='" + this.name + '\'' +
                ", duration='" + this.duration + '\'' +
                ", band=" + this.band.getName() +
                '}';
    }
}
