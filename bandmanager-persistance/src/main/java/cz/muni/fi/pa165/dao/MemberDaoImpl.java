package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Matej Sojak 433294
 */
public class MemberDaoImpl implements MemberDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    @Override
    public void create(Member m) {
        if (m == null) {
            throw new IllegalArgumentException("Member is null.");
        }

        if (m.getId() == null) {
            throw new IllegalArgumentException("ID of member is null.");
        }

        if (m.getEmail() == null) {
            throw new IllegalArgumentException("Member has no email assigned.");
        }

        if (m.getName() == null) {
            throw new IllegalArgumentException("Member has no name assigned.");
        }
        em.persist(m);
    }

    @Override
    public void delete(Member m) {
        if (m == null) {
            throw new IllegalArgumentException("Member is null.");
        }

        if (m.getId() == null) {
            throw new IllegalArgumentException("ID of member is null.");
        }

        if (m.getEmail() == null) {
            throw new IllegalArgumentException("Member has no email assigned.");
        }

        if (m.getName() == null) {
            throw new IllegalArgumentException("Member has no name assigned.");
        }
        em.remove(m);
    }

    @Override
    public void update(Member m) {
        if (m == null) {
            throw new IllegalArgumentException("Member is null.");
        }

        if (m.getId() == null) {
            throw new IllegalArgumentException("ID of member is null.");
        }

        if (m.getEmail() == null) {
            throw new IllegalArgumentException("Member has no email assigned.");
        }

        if (m.getName() == null) {
            throw new IllegalArgumentException("Member has no name assigned.");
        }
        em.merge(m);
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    @Override
    public List<Member> findByName(String name) {
        try {
            return em
                    .createQuery("select m from Member m where name = :name",
                            Member.class).setParameter("name", name).getResultList();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public Member findByEmail(String email) {
        try {
            return em
                    .createQuery("select m from Member m where email = :email",
                            Member.class).setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }
}


