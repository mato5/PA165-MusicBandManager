package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Tour;

import java.util.Date;
import java.util.List;

public interface TourService {
    public Tour create(Tour tour);

    public List<Tour> findAll();

    public Tour findById(Long id);

    public List<Tour> findByManager(Manager manager);

    public List<Tour> findByBand(Band band);

    public Tour setName(Tour tour, String name);

    public Tour setCity(Tour tour, String name);

    public Tour setManager(Tour tour, Manager manager);

    public Tour setBand(Tour tour, Band band);

    public void delete(Tour tour);

    Tour setDatetime(Tour tour, Date dateTime);
}
