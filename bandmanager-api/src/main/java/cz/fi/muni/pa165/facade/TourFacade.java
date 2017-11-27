package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.dto.TourDTO;

import java.util.Collection;

public interface TourFacade {
    TourDTO create(TourDTO tour);

    Collection<TourDTO> getAllTours();

    TourDTO findById(Long id);

    Collection<TourDTO> findByManager(ManagerDTO manager);

    Collection<TourDTO> findByBand(BandDTO band);

    TourDTO setName(TourDTO tour, String name);

    TourDTO setCity(TourDTO tour, String name);

    TourDTO setManager(TourDTO tour, ManagerDTO manager);

    TourDTO setBand(TourDTO tour, BandDTO band);

    void delete(TourDTO tour);
}
