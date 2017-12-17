package cz.fi.muni.pa165.dto;

import cz.muni.fi.pa165.enums.Genre;

public class BandGengreDTO {

    private Long id;
    private Genre genre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
