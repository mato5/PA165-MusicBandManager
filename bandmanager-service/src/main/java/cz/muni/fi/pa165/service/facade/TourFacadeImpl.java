package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.TourCreateDTO;
import cz.fi.muni.pa165.dto.TourDTO;
import cz.fi.muni.pa165.facade.TourFacade;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Tour;
import cz.muni.fi.pa165.service.BandService;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.ManagerService;
import cz.muni.fi.pa165.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Date;

@Service
public class TourFacadeImpl implements TourFacade {

    final static Logger logger = LoggerFactory.getLogger(TourFacadeImpl.class);

    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private TourService tourService;

    @Inject
    private ManagerService managerService;

    @Inject
    private BandService bandService;

    @Override
    public Long create(TourCreateDTO tourCreateDTO) {
        Tour tour = new Tour();
        tour.setName(tourCreateDTO.getName());
        tour.setCityName(tourCreateDTO.getCityName());
        tour.setManager(managerService.findManagerById(tourCreateDTO.getManagerId()));
        tour.setBand(bandService.findById(tourCreateDTO.getBandId()));
        tour.setDatetime(tourCreateDTO.getDatetime());
        Tour newTour = tourService.create(tour);
        return newTour.getId();
    }

    @Override
    public Collection<TourDTO> getAllTours() {
        return beanMappingService.mapTo(tourService.findAll(), TourDTO.class);
    }

    @Override
    public TourDTO findById(Long id) {
        Tour tour = tourService.findById(id);
        return (tour == null) ? null : beanMappingService.mapTo(tour, TourDTO.class);
    }

    @Override
    public Collection<TourDTO> findByManager(Long managerId) {
        return beanMappingService.mapTo(tourService.findByManager(managerService.findManagerById(managerId)), TourDTO.class);
    }

    @Override
    public Collection<TourDTO> findByBand(Long bandId) {
        return beanMappingService.mapTo(tourService.findByBand(bandService.findById(bandId)), TourDTO.class);
    }

    @Override
    public TourDTO setName(Long tourId, String tourName) {
        Tour tour = tourService.findById(tourId);
        tourService.setName(tour, tourName);
        return beanMappingService.mapTo(tour, TourDTO.class);
    }

    @Override
    public TourDTO setCity(Long tourId, String cityName) {
        Tour tour = tourService.findById(tourId);
        tourService.setCity(tour, cityName);
        return beanMappingService.mapTo(tour, TourDTO.class);
    }

    @Override
    public TourDTO setManager(Long tourId, Long managerId) {
        Tour tour = tourService.findById(tourId);
        Manager manager = managerService.findManagerById(managerId);
        tourService.setManager(tour, manager);
        return beanMappingService.mapTo(tour, TourDTO.class);
    }

    @Override
    public TourDTO setBand(Long tourId, Long bandId) {
        Tour tour = tourService.findById(tourId);
        Band band = bandService.findById(bandId);
        tourService.setBand(tour, band);
        return beanMappingService.mapTo(tour, TourDTO.class);
    }

    @Override
    public TourDTO setDatetime(Long tourId, Date dateTime) {
        Tour tour = tourService.findById(tourId);
        tourService.setDatetime(tour, dateTime);
        return beanMappingService.mapTo(tour, TourDTO.class);
    }

    @Override
    public void delete(Long tourId) {
        tourService.delete(tourService.findById(tourId));
    }
}
