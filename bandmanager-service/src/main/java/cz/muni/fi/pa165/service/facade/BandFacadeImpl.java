package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.BandCreateDTO;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.exceptions.UserServiceException;
import cz.fi.muni.pa165.facade.BandFacade;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.enums.Genre;
import cz.muni.fi.pa165.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author kami
 */
@Service
@Transactional
public class BandFacadeImpl implements BandFacade {

    final static Logger logger = LoggerFactory.getLogger(SongFacadeImpl.class);

    @Inject
    private BandService bandService;

    @Inject
    private ManagerService managerService;

    @Inject
    private SongService songService;

    @Inject
    private TourService tourService;

    @Inject
    private AlbumService albumService;

    @Inject
    private BandInviteService bandInviteService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public BandDTO findById(Long bandId) {
        Band band = this.bandService.findById(bandId);
        return (band == null) ? null : beanMappingService.mapTo(band, BandDTO.class);
    }


    @Override
    public Long create(BandCreateDTO bandCreateDTO) {
        Band band = new Band();
        band.setName(bandCreateDTO.getName());
        band.setLogoURI(bandCreateDTO.getLogoURI());
        band.setManager(this.managerService.findManagerById(bandCreateDTO.getManagerId()));
        band.setGenre(band.getGenre());
        Band newBand = this.bandService.create(band);
        return newBand.getId();
    }

    @Override
    public void delete(Long bandId) {
        Band toBeDeleted = bandService.findById(bandId);
        bandService.disbandBand(toBeDeleted);
    }

    @Override
    public List<BandDTO> findAll() {
        return beanMappingService.mapTo(this.bandService.findAll(), BandDTO.class);
    }

    @Override
    public List<BandDTO> findByManager(ManagerDTO managerDTO) {
        return beanMappingService.mapTo(this.bandService
                .findByManager(this.managerService.findManagerById(managerDTO.getId())), BandDTO.class);
    }

    @Override
    public List<BandDTO> findByManagerId(Long managerId) {
        return beanMappingService.mapTo(this.bandService
                .findByManager(this.managerService.findManagerById(managerId)), BandDTO.class);
    }

    @Override
    public List<BandDTO> findByGenre(Genre genre) {
        return beanMappingService.mapTo(this.bandService.findByGenre(genre), BandDTO.class);
    }

    @Override
    public void changeManager(BandDTO band, ManagerDTO managerDTO) {
        this.bandService.changeManager(this.bandService.findById(band.getId()),
                this.managerService.findManagerById(managerDTO.getId()));
    }

    @Override
    public void changeGenre(BandDTO band, Genre genre) {
        this.bandService.changeGenre(this.bandService.findById(band.getId()),
                genre);
    }

}
