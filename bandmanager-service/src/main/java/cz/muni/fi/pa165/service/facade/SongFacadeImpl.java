package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.SongCreateDTO;
import cz.fi.muni.pa165.dto.SongDTO;
import cz.fi.muni.pa165.facade.SongFacade;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Song;
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
public class SongFacadeImpl implements SongFacade {

    final static Logger logger = LoggerFactory.getLogger(SongFacadeImpl.class);

    @Inject
    private SongService songService;

    @Inject
    private BandService bandService;

    @Inject
    private BeanMappingService beanMappingService;


    @Override
    public SongDTO findSongById(Long id) {
        Song song = songService.findById(id);
        return (song == null) ? null : beanMappingService.mapTo(song, SongDTO.class);
    }

    @Override
    public Collection<SongDTO> findAll() {
        return beanMappingService.mapTo(songService.findAll(), SongDTO.class);
    }

    @Override
    public Collection<SongDTO> findSongsByName(String name) {
        return beanMappingService.mapTo(songService.findByName(name), SongDTO.class);
    }

    @Override
    public Collection<SongDTO> findSongsByBand(BandDTO band) {
        return beanMappingService.mapTo(songService.findByBand(bandService.findById(band.getId())), SongDTO.class);
    }

    @Override
    public void detete(SongDTO songDTO) {
        songService.deleteSong(songService.findById(songDTO.getId()));
    }

    @Override
    public Long createSong(SongCreateDTO songCreateDTO) {
        Song song = new Song();
        song.setName(songCreateDTO.getName());
        song.setDuration(songCreateDTO.getDuration());
        song.setBand(bandService.findById(songCreateDTO.getBandId()));
        Song newSong = songService.createSong(song);
        return newSong.getId();
    }

    @Override
    public void changeDuration(SongDTO songDTO, Long newsongDuration) {

        Song song = songService.findById(songDTO.getId());
        songService.changeDuration(song, newsongDuration);
    }

    @Override
    public void changeBand(SongDTO songDTO, BandDTO newBandDTO) {
        Song song = songService.findById(songDTO.getId());
        Band band = bandService.findById(newBandDTO.getId());
        songService.changeBand(song, band);
    }


}
