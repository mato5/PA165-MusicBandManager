package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Band;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AlbumDaoImpl implements AlbumDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Album album) {
        if (album == null) {
            throw new IllegalArgumentException("Album is null");
        }
        if (album.getName() == null) {
            throw new IllegalArgumentException("Album's name is null");
        }
        if (album.getBand() == null) {
            throw new IllegalArgumentException("Album's band is null");
        }
        em.persist(album);
    }

    @Override
    public void delete(Album album) {
        if (album == null) {
            throw new IllegalArgumentException("Album is null");
        }
        if (album.getId() == null) {
            throw new IllegalArgumentException("Album's ID is null");
        }
        if (album.getName() == null) {
            throw new IllegalArgumentException("Album's name is null");
        }
        if (album.getBand() == null) {
            throw new IllegalArgumentException("Album's band is null");
        }
        em.remove(album);
    }

    @Override
    public void update(Album album) {
        if (album == null) {
            throw new IllegalArgumentException("Album is null");
        }
        if (album.getId() == null) {
            throw new IllegalArgumentException("Album's ID is null");
        }
        if (album.getName() == null) {
            throw new IllegalArgumentException("Album's name is null");
        }
        if (album.getBand() == null) {
            throw new IllegalArgumentException("Album's band is null");
        }
        em.merge(album);
    }

    @Override
    public Album findById(Long id) {
        return em.find(Album.class, id);
    }

    @Override
    public List<Album> findAll() {
        return em.createQuery("select a from Album a", Album.class)
                .getResultList();
    }

    @Override
    public List<Album> findByBand(Band band) {
        try {
            return em
                    .createQuery("select a from Album a where band = :band",
                            Album.class).setParameter("band", band).getResultList();
        } catch (NoResultException nrf) {
            return null;
        }
    }
}
