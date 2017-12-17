package cz.muni.fi.pa165.service;


import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.TourDTO;
import cz.muni.fi.pa165.entity.Tour;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Autowired
    private Mapper dozer;

    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, mapToClass));
        }
        return mappedCollection;
    }

    public <T> T mapTo(Object u, Class<T> mapToClass) {
        return dozer.map(u, mapToClass);
    }

    public Mapper getMapper() {
        return dozer;
    }

    @Override
    public TourDTO mapTour(Tour tour, Class<TourDTO> tourDTOClass) {
        TourDTO tourDTO = new TourDTO();
        tourDTO.setBand(mapTo(tour.getBand(), BandDTO.class));
        tourDTO.setCityName(tour.getCityName());
        tourDTO.setDatetime(tour.getDatetime());
        tourDTO.setName(tour.getName());
        tourDTO.setId(tour.getId());
        tourDTO.setManagerId(tour.getManager().getId());
        return tourDTO;
    }

    @Override
    public Collection<TourDTO> mapTours(Collection<Tour> tours, Class<TourDTO> tourDTOClass) {
        List<TourDTO> mappedCollection = new ArrayList<>();
        for (Tour tour : tours) {
            mappedCollection.add(mapTour(tour, TourDTO.class));
        }
        return mappedCollection;
    }
}
