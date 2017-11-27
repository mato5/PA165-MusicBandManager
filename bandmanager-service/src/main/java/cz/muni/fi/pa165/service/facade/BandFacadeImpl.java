package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.AlbumDTO;
import cz.fi.muni.pa165.dto.BandCreateDTO;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.facade.BandFacade;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.enums.Genre;
import cz.muni.fi.pa165.service.BandService;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.ManagerService;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kami
 */
public class BandFacadeImpl implements BandFacade {
    
    final static Logger logger = LoggerFactory.getLogger(SongFacadeImpl.class);
    
    @Inject
    private BandService bandService;
    
    @Inject
    private ManagerService managerService;
    
    @Inject
    private BeanMappingService beanMappingService;
    
    @Override
    public BandDTO findById(Long id) {
        Band band = this.bandService.findById(id);
        return (band == null) ? null : beanMappingService.mapTo(band, BandDTO.class);
    }


    @Override
    public Long create(BandCreateDTO bandCreateDTO) {
        Band band = new Band();
        band.setName(bandCreateDTO.getName());
        band.setLogoURI(bandCreateDTO.getLogoURI());
        band.setManager(this.managerService.findManagerById(bandCreateDTO.getManagerId()));
        band.setGenre(band.getGenre());
        this.bandService.create(band);
        return band.getId();
    }
    
    @Override
    public void delete(BandDTO bandDTO) {
        this.bandService.delete(this.bandService.findById(bandDTO.getId()));
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
    public List<BandDTO> findByGenre(Genre genre) {
        return beanMappingService.mapTo(this.bandService.findByGenre(genre), BandDTO.class);
    }

    @Override
    public void changeManager(BandDTO band, ManagerDTO managerDTO) {
        this.bandService.changeManager( this.bandService.findById(band.getId()), 
                                        this.managerService.findManagerById(managerDTO.getId()));
    }

    @Override
    public void changeGenre(BandDTO band, Genre genre) {
        this.bandService.changeGenre(this.bandService.findById(band.getId()), 
                                        genre);
    }
    
}