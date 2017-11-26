package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Tour;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Matej Sojak 433294
 */
@Repository
public class TourDaoImpl implements TourDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Tour tour) {
        if(tour==null){
            throw new IllegalArgumentException("Tour is null.");
        }
        if(tour.getDatetime()==null){
            throw new IllegalArgumentException("Tour's date is null");
        }
        if(tour.getCityName()==null){
            throw new IllegalArgumentException("Tour's city is null");
        }
        if(tour.getBand()==null){
            throw new IllegalArgumentException("Tour's band is null");
        }
        if(tour.getManager()==null){
            throw new IllegalArgumentException("Tour's manager is null");
        }
        em.persist(tour);
    }

    @Override
    public void delete(Tour tour) {
        if(tour==null){
            throw new IllegalArgumentException("Tour is null.");
        }
        if(tour.getDatetime()==null){
            throw new IllegalArgumentException("Tour's date is null");
        }
        if(tour.getCityName()==null){
            throw new IllegalArgumentException("Tour's city is null");
        }
        if(tour.getBand()==null){
            throw new IllegalArgumentException("Tour's band is null");
        }
        if(tour.getManager()==null){
            throw new IllegalArgumentException("Tour's manager is null");
        }
        if(tour.getId()==null){
            throw new IllegalArgumentException("Tour's ID is null");
        }
        em.remove(tour);
    }

    @Override
    public void update(Tour tour) {
        if(tour==null){
            throw new IllegalArgumentException("Tour is null.");
        }
        if(tour.getDatetime()==null){
            throw new IllegalArgumentException("Tour's date is null");
        }
        if(tour.getCityName()==null){
            throw new IllegalArgumentException("Tour's city is null");
        }
        if(tour.getBand()==null){
            throw new IllegalArgumentException("Tour's band is null");
        }
        if(tour.getManager()==null){
            throw new IllegalArgumentException("Tour's manager is null");
        }
        if(tour.getId()==null){
            throw new IllegalArgumentException("Tour's ID is null");
        }
        em.merge(tour);
    }

    @Override
    public Tour findById(Long id) {
        return em.find(Tour.class,id);
    }

    @Override
    public List<Tour> findAll() {
        return em.createQuery("select t from Tour t", Tour.class)
                .getResultList();
    }

    @Override
    public List<Tour> findByManager(Manager manager) {
        try {
            return em
                    .createQuery("select t from Tour t where manager = :manager",
                            Tour.class).setParameter("manager", manager).getResultList();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public List<Tour> findByBand(Band band) {
        try {
            return em
                    .createQuery("select t from Tour t where band = :band",
                            Tour.class).setParameter("band", band).getResultList();
        } catch (NoResultException nrf) {
            return null;
        }
    }
}
