package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Song;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@Service
public interface SongService {

    public Song findById(Long id);

    public List<Song> findAll();

    public Song create(Song song);

    public void changeDuration(Song song, Long duration);

    public List<Song> findByBand(Band band);

    public List<Song> findByName(String name);

    public void changeBand(Song song, Band band);

    public void delete(Song song);

}
