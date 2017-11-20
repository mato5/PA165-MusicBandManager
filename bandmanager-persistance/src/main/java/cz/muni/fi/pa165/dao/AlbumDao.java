package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Band;

import java.util.List;

/**
 * @author Matej Sojak 433294
 */
public interface AlbumDao {
    public void create(Album album);

    public void delete(Album album);

    public void update(Album album);

    public Album findById(Long id);

    public List<Album> findAll();

    public List<Album> findByBand(Band band);
}
