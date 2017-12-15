package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.TourCreateDTO;
import cz.fi.muni.pa165.dto.TourDTO;

import java.util.Collection;
import java.util.Date;

public interface TourFacade {
    Long create(TourCreateDTO tourCreateDTO);

    Collection<TourDTO> getAllTours();

    TourDTO findById(Long id);

    Collection<TourDTO> findByManager(Long managerId);

    Collection<TourDTO> findByBand(Long bandId);

    TourDTO setName(Long tourId, String name);

    TourDTO setCity(Long tourId, String name);

    TourDTO setManager(Long tourId, Long managerId);

    TourDTO setBand(Long tourId, Long bandId);

    TourDTO setDatetime(Long tourId, Date dateTime);

    void delete(Long tourId);
}
