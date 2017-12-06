package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.dto.TourDTO;
import cz.fi.muni.pa165.facade.TourFacade;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Tour;
import cz.muni.fi.pa165.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

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
    public TourDTO create(TourDTO t) {
        Tour tour = beanMappingService.mapTo(t, Tour.class);
        tourService.create(tour);
        return (tour == null) ? null : beanMappingService.mapTo(tour, TourDTO.class);
    }

    @Override
    public Collection<TourDTO> getAllTours() { return beanMappingService.mapTo(tourService.findAll(), TourDTO.class); }

    @Override
    public TourDTO findById(Long id) {
        Tour tour = tourService.findById(id);
        return (tour == null) ? null : beanMappingService.mapTo(tour, TourDTO.class);
    }

    @Override
    public Collection<TourDTO> findByManager(ManagerDTO manager) {
        return beanMappingService.mapTo(tourService.findByManager(managerService.findManagerById(manager.getId())), TourDTO.class);
    }

    @Override
    public Collection<TourDTO> findByBand(BandDTO band) {
        return beanMappingService.mapTo(tourService.findByBand(bandService.findById(band.getId())), TourDTO.class);
    }

    @Override
    public TourDTO setName(TourDTO t, String name) {
        Tour tour = tourService.findById(t.getId());
        tourService.setName(tour, name);
        return beanMappingService.mapTo(tour, TourDTO.class);
    }

    @Override
    public TourDTO setCity(TourDTO t, String name) {
        Tour tour = tourService.findById(t.getId());
        tourService.setCity(tour, name);
        return beanMappingService.mapTo(tour, TourDTO.class);
    }

    @Override
    public TourDTO setManager(TourDTO t, ManagerDTO m) {
        Tour tour = tourService.findById(t.getId());
        Manager manager = managerService.findManagerById(m.getId());
        tourService.setManager(tour, manager);
        return beanMappingService.mapTo(tour, TourDTO.class);
    }

    @Override
    public TourDTO setBand(TourDTO t, BandDTO b) {
        Tour tour = tourService.findById(t.getId());
        Band band = bandService.findById(b.getId());
        tourService.setBand(tour, band);
        return beanMappingService.mapTo(tour, TourDTO.class);
    }

    @Override
    public void delete(TourDTO tour) {
        tourService.delete(tourService.findById(tour.getId()));
    }
}
