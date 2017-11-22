package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Song;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@Service
public interface AlbumService {

    public Album findById(Long id);

    public Album create(Album album);

    public void delete(Album album);

    public List<Album> findAll();

    public List<Album> findByBand(Band band);

    public Album addSong(Album album, Song song);

    public Album deleteSong(Album album, Song song);
}
