package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Song;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Miroslav Kadlec
 */
public class SongDaoImpl implements SongDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Song findById(Long id) {
        return this.em.find(Song.class, id);
    }

    @Override
    public void create(Song song) {
        this.em.persist(song);
    }

    @Override
    public void delete(Song song) {
        this.em.remove(song);
    }

    @Override
    public List<Song> findAll() {
        return em.createQuery("select s from Song s", Song.class)
                .getResultList();
    }
    
    @Override
    public Song findByName(String name) {
        try {
            TypedQuery<Song> q = em.createQuery("select s from Song s where name = :name",
                            Song.class).setParameter("name", name);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    @Override
    public List<Song> findByBand(Band band) {
        try {
            TypedQuery<Song> q = em.createQuery(
                    "select b from Song s where s.band = :bandId",
                    Song.class);

            q.setParameter("managerId", band);
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
