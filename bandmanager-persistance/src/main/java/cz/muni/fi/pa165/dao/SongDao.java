package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Song;
import java.util.List;

/**
 *
 * @author Miroslav Kadlec
 */
public interface SongDao {
    public Song findById(Long id);

    public void create(Song song);
    
    public void delete(Song song);
    
    public void update(Song song);

    public List<Song> findAll();

    public List<Song> findByName(String name);

    public List<Song> findByBand(Band band);
    
    public List<Song> findByAlbum(Album album);
}
