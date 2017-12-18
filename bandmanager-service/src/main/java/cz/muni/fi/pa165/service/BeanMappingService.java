package cz.muni.fi.pa165.service;


import cz.fi.muni.pa165.dto.BandInviteDTO;
import cz.fi.muni.pa165.dto.TourDTO;
import cz.muni.fi.pa165.entity.BandInvite;
import cz.muni.fi.pa165.entity.Tour;
import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;


public interface BeanMappingService {

    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    public <T> T mapTo(Object u, Class<T> mapToClass);

    public Mapper getMapper();

    BandInvite mapBandInvite(BandInviteDTO biDTO, Class<BandInvite> biClass);
    
    BandInviteDTO mapBandInviteDTO(BandInvite bi, Class<BandInviteDTO> biDTOClass);
    
    Collection<BandInviteDTO> mapBandInviteDTOs(Collection<BandInvite> bis, Class<BandInviteDTO> biDTOClass);
    
    TourDTO mapTour(Tour tour, Class<TourDTO> tourDTOClass);

    Collection<TourDTO> mapTours(Collection<Tour> tours, Class<TourDTO> tourDTOClass);
}
