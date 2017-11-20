package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Band;

import java.util.List;

/**
 * DAO interface for the Album entity
 * @author Matej Sojak 433294
 */
public interface AlbumDao {
    /**
     * Persists a freshly created album
     * @param album album to be persisted
     */
    public void create(Album album);

    /**
     * Deletes a specified album
     * @param album album to be deleted
     */
    public void delete(Album album);

    /**
     * Updates an existing album
     * @param album album to be updated
     */
    public void update(Album album);

    /**
     * Finds an album by its ID
     * @param id id of an album
     * @return Album entity
     */
    public Album findById(Long id);

    /**
     * Finds all existing albums
     * @return list of all existing albums
     */
    public List<Album> findAll();

    /**
     * Finds all albums of a specified band
     * @param band the specified band
     * @return all of the band's albums
     */
    public List<Album> findByBand(Band band);
}
