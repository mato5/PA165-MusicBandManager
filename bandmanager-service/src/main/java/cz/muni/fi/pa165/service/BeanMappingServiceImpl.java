package cz.muni.fi.pa165.service;


import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.BandInviteDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.dto.MemberDTO;
import cz.fi.muni.pa165.dto.TourDTO;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.BandInvite;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.entity.Tour;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BeanMappingServiceImpl implements BeanMappingService {
    
    final static Logger log = LoggerFactory.getLogger(BeanMappingServiceImpl.class);
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
    public BandInvite mapBandInvite(BandInviteDTO biDTO, Class<BandInvite> biClass) {
        BandInvite bi = new BandInvite();
        bi.setBand(mapTo(biDTO.getBand(), Band.class));
        bi.setInvitedMember(mapTo(biDTO.getMember(), Member.class));
        bi.setManager(mapTo(biDTO.getManager(), Manager.class));
        return bi;
    }
    
    @Override
    public BandInviteDTO mapBandInviteDTO(BandInvite bi, Class<BandInviteDTO> viDTOClass){
        BandInviteDTO biDTO = new BandInviteDTO();
        biDTO.setId(bi.getId());
        biDTO.setBand(mapTo(bi.getBand(), BandDTO.class));
        biDTO.setManager(mapTo(bi.getManager(), ManagerDTO.class));
        biDTO.setMember(mapTo(bi.getInvitedMember(), MemberDTO.class));
        return biDTO;
    }
    
    @Override
    public Collection<BandInviteDTO> mapBandInviteDTOs(Collection<BandInvite> bis, Class<BandInviteDTO> biDTOClass) {
        List<BandInviteDTO> mappedCollection = new ArrayList<>();
        for (BandInvite bi : bis) {
            mappedCollection.add(mapBandInviteDTO(bi, BandInviteDTO.class));
            
        }
        return mappedCollection;
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
