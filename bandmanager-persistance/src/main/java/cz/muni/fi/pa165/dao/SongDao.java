package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Song;
import java.util.List;

/**
 * DAO for Song entity. Performs CRUD operations and some methods for filtering.
 * @author Miroslav Kadlec
 */
public interface SongDao {
    /**
     * Finds Song by ID
     *
     * @param id Long - id of Song object
     * @return Found Song object
     */
    public Song findById(Long id);
    
    /**
     * Persists given Song object into DB. 
     *
     * @param song Song object to be persisted
     */
    public void create(Song song);
    
    /**
     * Removes given Song object from DB.
     * @param song Song object to be deleted
     */
    public void delete(Song song);
    
    /**
     * Updates existing Song in the DB according to the given one.
     * @param song New song object
     */
    public void update(Song song);
    
    /**
     * Lists all Song objects in the DB
     * @return List of songs found
     */
    public List<Song> findAll();

    /**
     * Looks up all the Songs in DB that match given name
     * @param name String to search the Songs by
     * @return List of songs found
     */
    public List<Song> findByName(String name);

    /**
     * Looks up all the Songs in DB that match given band
     * @param band Band to search the Songs by
     * @return List of songs found
     */
    public List<Song> findByBand(Band band);
    
    /**
     * Looks up all the Songs in DB that belong to given album
     * @param album Album to search the Songs by
     * @return List of songs found
     */
    public List<Song> findByAlbum(Album album);
}
