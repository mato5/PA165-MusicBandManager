package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Tour;

import java.util.List;

/**
 * @author Matej Sojak 433294
 */
public interface TourDao {
    public void create(Tour tour);

    public void delete(Tour tour);

    public void update(Tour tour);

    public Tour findById(Long id);

    public List<Tour> findAll();

    public List<Tour> findByManager(Manager manager);

    public List<Tour> findByBand(Band band);
}
