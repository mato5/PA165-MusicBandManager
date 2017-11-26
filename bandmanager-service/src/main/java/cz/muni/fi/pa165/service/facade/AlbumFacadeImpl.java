package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.AlbumCreateDTO;
import cz.fi.muni.pa165.dto.AlbumDTO;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.SongDTO;
import cz.fi.muni.pa165.facade.AlbumFacade;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.service.AlbumService;
import cz.muni.fi.pa165.service.BandService;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@Service
@Transactional
public class AlbumFacadeImpl implements AlbumFacade {

    final static Logger logger = LoggerFactory.getLogger(AlbumFacadeImpl.class);

    @Inject
    private SongService songService;

    @Inject
    private AlbumService albumService;

    @Inject
    private BandService bandService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public AlbumDTO findAlbumById(Long id) {
        Album album = albumService.findById(id);
        return (album == null) ? null : beanMappingService.mapTo(album, AlbumDTO.class);
    }

    @Override
    public Collection<AlbumDTO> findAll() {
        return beanMappingService.mapTo(albumService.findAll(), AlbumDTO.class);
    }

    @Override
    public Collection<AlbumDTO> findByBand(BandDTO bandDTO) {
        return beanMappingService.mapTo(albumService.findByBand(bandService.findById(bandDTO.getId())), AlbumDTO.class);
    }

    @Override
    public void addSong(AlbumDTO albumDTO, SongDTO songDTO) {
        Album album = albumService.findById(albumDTO.getId());
        albumService.addSong(album, songService.findById(songDTO.getId()));
    }

    @Override
    public void deleteSong(AlbumDTO albumDTO, SongDTO songDTO) {
        Album album = albumService.findById(albumDTO.getId());
        albumService.deleteSong(album, songService.findById(songDTO.getId()));
    }

    @Override
    public void deleteAlbum(AlbumDTO albumDTO) {
        albumService.delete(albumService.findById(albumDTO.getId()));
    }

    @Override
    public Long createAlbum(AlbumCreateDTO albumCreateDTO) {
        Album album = new Album();
        album.setCoverURI(albumCreateDTO.getCoverURI());
        album.setName(albumCreateDTO.getName());
        album.setBand(bandService.findById(albumCreateDTO.getBandId()));
        Album newAlbum = albumService.create(album);
        return newAlbum.getId();
    }
}
