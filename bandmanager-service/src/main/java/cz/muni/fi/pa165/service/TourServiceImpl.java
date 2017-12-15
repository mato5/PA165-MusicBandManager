package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.TourDao;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Tour;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Service
public class TourServiceImpl implements TourService {

    @Inject
    private TourDao tourDao;

    @Override
    public Tour create(Tour tour) {
        tourDao.create(tour);
        return tour;
    }

    @Override
    public List<Tour> findAll() {
        return this.tourDao.findAll();
    }

    @Override
    public Tour findById(Long id) {
        return this.tourDao.findById(id);
    }

    @Override
    public List<Tour> findByManager(Manager manager) {
        return this.tourDao.findByManager(manager);
    }

    @Override
    public List<Tour> findByBand(Band band) {
        return this.tourDao.findByBand(band);
    }

    @Override
    public Tour setName(Tour tour, String name) {
        tour.setName(name);
        this.tourDao.update(tour);
        return tour;
    }

    @Override
    public Tour setCity(Tour tour, String name) {
        tour.setCityName(name);
        this.tourDao.update(tour);
        return tour;
    }

    @Override
    public Tour setManager(Tour tour, Manager manager) {
        tour.setManager(manager);
        this.tourDao.update(tour);
        return tour;
    }

    @Override
    public Tour setBand(Tour tour, Band band) {
        tour.setBand(band);
        this.tourDao.update(tour);
        return tour;
    }

    @Override
    public void delete(Tour tour) {
        this.tourDao.delete(tour);
    }

    @Override
    public Tour setDatetime(Tour tour, Date dateTime) {
        tour.setDatetime(dateTime);
        this.tourDao.update(tour);
        return tour;
    }
}
