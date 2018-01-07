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
 */
@Repository
public class BandDaoImpl implements BandDao {


    @PersistenceContext
    private EntityManager em;


    @Override
    public Band findById(Long id) {
        return em.find(Band.class, id);
    }


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


    @Override
    public void delete(Band band) {

        if (band == null) {
            System.out.println("Band je null.");
            throw new IllegalArgumentException("Band is null");
        }
        em.remove(band);
    }


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


    @Override
    public List<Band> findAll() {
        return em.createQuery("select b from Band b", Band.class)
                .getResultList();
    }


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

    // not working, bad query
    @Override
    public List<Band> findByMember(Member member) {
        try {
            TypedQuery<Band> query = em.createQuery(
                    "Select b " +
                    "from Band b " +
                    "where Select member from b.members where member = :memberId",
                    Band.class);

            query.setParameter("memberId", member);
            return query.getResultList();

        } catch (NoResultException nrf) {
            return null;
        }
    }
}
