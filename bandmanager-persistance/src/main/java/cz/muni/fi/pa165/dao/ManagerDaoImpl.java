package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Manager;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Alexander Kromka
 */
@Repository
public class ManagerDaoImpl implements ManagerDao {


    @PersistenceContext
    private EntityManager em;

    @Override
    public Manager findById(Long id) {
        return em.find(Manager.class, id);
    }


    @Override
    public void create(Manager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("Manager is null.");
        }

        if (manager.getName() == null || "".equals(manager.getName())) {
            throw new IllegalArgumentException("Manager has no Name assigned.");
        }
        em.persist(manager);
    }


    @Override
    public void delete(Manager manager) {

        if (manager == null) {
            throw new IllegalArgumentException("Manager is null");
        }
        em.remove(manager);
    }


    @Override
    public void update(Manager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("Manager is null.");
        }

        if (manager.getName() == null || "".equals(manager.getName())) {
            throw new IllegalArgumentException("Manager has no name assigned.");
        }

        em.merge(manager);
    }


    @Override
    public List<Manager> findAll() {
        return em.createQuery("select m from Manager m", Manager.class)
                .getResultList();
    }


    @Override
    public Manager findByName(String name) {
        try {
            return em
                    .createQuery("select m from Manager m where name = :name",
                            Manager.class).setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }

}
