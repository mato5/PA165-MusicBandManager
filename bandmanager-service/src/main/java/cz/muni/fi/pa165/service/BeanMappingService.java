package cz.muni.fi.pa165.service;


import cz.fi.muni.pa165.dto.TourDTO;
import cz.muni.fi.pa165.entity.Tour;
import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;


public interface BeanMappingService {

    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    public <T> T mapTo(Object u, Class<T> mapToClass);

    public Mapper getMapper();

    TourDTO mapTour(Tour tour, Class<TourDTO> tourDTOClass);

    Collection<TourDTO> mapTours(Collection<Tour> tours, Class<TourDTO> tourDTOClass);
}
