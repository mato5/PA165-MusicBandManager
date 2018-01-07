package cz.muni.fi.pa165.dao;


import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.BandInvite;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Iurii xkuznetc Kuznetcov
 */
@Repository
public class BandInviteDaoImpl implements BandInviteDao {
final static Logger log = LoggerFactory.getLogger(BandInviteDaoImpl.class);
    @PersistenceContext
    private EntityManager em;

    @Override
    public BandInvite findById(long id) {
        return em.find(BandInvite.class, id);
    }

    @Override
    public void create(BandInvite bandInvite) {
        if (bandInvite == null) {
            throw new IllegalArgumentException("BandInvite is null.");
        }

        if (bandInvite.getBand() == null) {
            throw new IllegalArgumentException("BandInvite has no Band assigned.");
        }

        if (bandInvite.getInvitedMember() == null) {
            throw new IllegalArgumentException("BandInvite has no Member assigned.");
        }

        if (bandInvite.getManager() == null) {
            throw new IllegalArgumentException("BandInvite has no Manager assigned.");

        }
        em.persist(bandInvite);
    }

    @Override
    public void delete(BandInvite bandInvite) {
        if (bandInvite == null) {
            throw new IllegalArgumentException("BandInvite is null");
        }
        em.remove(bandInvite);
    }

    @Override
    public void update(BandInvite bandInvite) {
        if (bandInvite == null) {
            throw new IllegalArgumentException("BandInvite is null.");
        }

        if (bandInvite.getBand() == null) {
            throw new IllegalArgumentException("BandInvite has no Band assigned.");
        }

        if (bandInvite.getInvitedMember() == null) {
            throw new IllegalArgumentException("BandInvite has no Member assigned.");
        }

        if (bandInvite.getManager() == null) {
            throw new IllegalArgumentException("BandInvite has no Manager assigned.");

        }
        em.merge(bandInvite);
    }

    @Override
    public List<BandInvite> findAll() {
        return em.createQuery("select bi from BandInvite bi", BandInvite.class)
                .getResultList();
    }

    @Override
    public List<BandInvite> findByManager(Manager manager) {
        try {
            TypedQuery<BandInvite> query = em.createQuery(
                    "Select bi from BandInvite bi where bi.manager = :managerId",
                    BandInvite.class);

            query.setParameter("managerId", manager);
            return query.getResultList();

        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public List<BandInvite> findByBand(Band band) {
        try {
            TypedQuery<BandInvite> query = em.createQuery(
                    "Select bi from BandInvite bi where bi.band = :bandId",
                    BandInvite.class);

            query.setParameter("bandId", band);
            return query.getResultList();

        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public List<BandInvite> findByMember(Member member) {
        try {
            TypedQuery<BandInvite> query = em.createQuery(
                    "Select bi from BandInvite bi where bi.invitedMember = :memberId",
                    BandInvite.class);

            query.setParameter("memberId", member);
            return query.getResultList();

        } catch (NoResultException nrf) {
            return null;
        }
    }
}
