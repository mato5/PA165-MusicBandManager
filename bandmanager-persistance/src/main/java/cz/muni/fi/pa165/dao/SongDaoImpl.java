package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Song;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Miroslav Kadlec
 */
@Repository
public class SongDaoImpl implements SongDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Song findById(Long id) {
        return this.em.find(Song.class, id);
    }

    @Override
    public void create(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("Given song is null");
        }
        if (song.getBand() == null) {
            throw new IllegalArgumentException();
        }
        this.em.persist(song);
    }

    @Override
    public void delete(Song song) {
        this.em.remove(song);
    }

    @Override
    public void update(Song song)  {
        if (song == null) {
            throw new IllegalArgumentException("Given song is null");
        }
        if (song.getId() == null) {
            throw new IllegalArgumentException("Given song has id = null");
        }
        if (song.getBand() == null) {
            throw new IllegalArgumentException();
        }
        this.em.merge(song);
    }
    
    @Override
    public List<Song> findAll() {
        return em.createQuery("select s from Song s", Song.class)
                .getResultList();
    }
    
    @Override
    public List<Song> findByName(String name) {
        try {
            TypedQuery<Song> q = em.createQuery("select s from Song s where name = :name",
                            Song.class).setParameter("name", name);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    @Override
    public List<Song> findByBand(Band band) {
        try {
            TypedQuery<Song> q = em.createQuery("select s from Song s where band = :band",
                            Song.class).setParameter("band", band);
            return q.getResultList();

        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public List<Song> findByAlbum(Album album) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
