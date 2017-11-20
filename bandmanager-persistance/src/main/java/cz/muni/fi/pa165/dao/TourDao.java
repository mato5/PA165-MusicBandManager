package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Tour;

import java.util.List;

/**
 * DAO interface for the Tour entity
 * @author Matej Sojak 433294
 */
public interface TourDao {
    /**
     * Persists a new tour
     * @param tour tour to be persisted
     */
    public void create(Tour tour);

    /**
     * Deletes an existing tour
     * @param tour tour to be deleted
     */
    public void delete(Tour tour);

    /**
     * Updates an existing tour
     * @param tour tour to be updated
     */
    public void update(Tour tour);

    /**
     * Finds a tour by its ID
     * @param id id of a tour
     * @return tour entity
     */
    public Tour findById(Long id);

    /**
     * Finds all existing tours
     * @return list of all tours
     */
    public List<Tour> findAll();

    /**
     * Finds all existing tours of a specified manager
     * @param manager the specified manager
     * @return list of all the manager's tours
     */
    public List<Tour> findByManager(Manager manager);

    /**
     * Finds all existing tours of a specified band
     * @param band the specified band
     * @return list of all the band's tours
     */
    public List<Tour> findByBand(Band band);
}
