package cz.fi.muni.pa165.dto;

import cz.muni.fi.pa165.enums.Genre;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Miroslav Kadlec
 */
public class BandCreateDTO {
    
    @NotNull
    @Size(min = 5, max = 50)
    private String name;
    
    private String logoURI;
    
    private Genre genre;
    
    @NotNull
    private Long managerId;

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

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        BandCreateDTO other = (BandCreateDTO) obj;
        if (this.getManagerId()== null) {
            if (other.getManagerId() != null)
                return false;
        } else if (!this.getManagerId().equals(other.getManagerId()))
            return false;
        if (this.getName() == null) {
            if (other.getName() != null)
                return false;
        } else if (!this.getName().equals(other.getName()))
            return false;
        if (this.getGenre()== null) {
            if (other.getGenre() != null)
                return false;
        } else if (!this.getGenre().equals(other.getGenre()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String result = "BandDTO {"
                + ",name=" + this.getName()
                + ",logoURI" + this.getLogoURI()
                + ",genre" + this.getGenre().name()
                + "manager_id=" + this.getManagerId();
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 51;
        hash = hash * 31 + (this.getName()!= null ? this.getName().hashCode() : 0);
        hash = hash * 13 + (this.getLogoURI()!= null ? this.getLogoURI().hashCode() : 0);
        hash = hash * 37 + (this.getGenre()!= null ? this.getGenre().hashCode() : 0);
        hash = hash * 17 + (this.getManagerId()!= null ? this.getManagerId().hashCode() : 0);
        return hash;
    }

}
