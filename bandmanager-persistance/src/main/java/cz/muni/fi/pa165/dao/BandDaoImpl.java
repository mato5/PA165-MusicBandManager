package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Iurii xkuznetc Kuznetcov
 * <p>
 * Data Access Object for Band entity with basic CRUD operations and some filtering.
 */
@Repository
public class BandDaoImpl implements BandDao {


    @PersistenceContext
    private EntityManager em;

    /**
     * Gets Band object by it's id
     *
     * @param id Long - id of Band object
     * @return Band band object
     */
    @Override
    public Band findById(Long id) {
        return em.find(Band.class, id);
    }

    /**
     * Persist new Band object. Provides some basic fields values validation.
     *
     * @param band Band object to persist
     */
    @Override
    public void create(Band band) {
        if (band == null) {
            throw new IllegalArgumentException("Band is null.");
        }

        if (band.getName() == null || "".equals(band.getName())) {
            throw new IllegalArgumentException("Band has no Name assigned.");
        }

        if (band.getManager() == null) {
            throw new IllegalArgumentException("Band has no Manager assigned.");
        }
        em.persist(band);
    }

    /**
     * Delete existing Band object.
     *
     * @param band Band object to delete.
     */
    @Override
    public void delete(Band band) {

        if (band == null) {
            throw new IllegalArgumentException("Band is null");
        }
        em.remove(band);
    }

    /**
     * Merge changes in Band object and persist modified object. Provides some basic fields values validation.
     *
     * @param band
     */
    @Override
    public void update(Band band) {
        if (band == null) {
            throw new IllegalArgumentException("Band is null.");
        }

        if (band.getName() == null || "".equals(band.getName())) {
            throw new IllegalArgumentException("Band has no name assigned.");
        }

        if (band.getManager() == null) {
            throw new IllegalArgumentException("Band has no Manager assigned.");
        }
        em.merge(band);
    }

    /**
     * Get all persistent Band objects.
     *
     * @return List<Band> - list of stored Band objects.
     */
    @Override
    public List<Band> findAll() {
        return em.createQuery("select b from Band b", Band.class)
                .getResultList();
    }

    /**
     * Get Band object by it's name.
     *
     * @param name String - band's name
     * @return Band object if band with given name exists or null if not.
     */
    @Override
    public Band findByName(String name) {
        try {
            return em
                    .createQuery("select b from Band b where name = :name",
                            Band.class).setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    /**
     * Get all Band objects by their Manager.
     *
     * @param manager Manager object which owns a band.
     * @return List<Band> - list of stored Band objects with given Manager
     * or null if such an objects doesn't exist.
     */
    @Override
    public List<Band> findByManager(Manager manager) {
        try {
            TypedQuery<Band> query = em.createQuery(
                    "Select b from Band b where b.manager = :managerId",
                    Band.class);

            query.setParameter("managerId", manager);
            return query.getResultList();

        } catch (NoResultException nrf) {
            return null;
        }
    }
}
