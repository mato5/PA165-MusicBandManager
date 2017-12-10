package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.SongCreateDTO;
import cz.fi.muni.pa165.dto.SongDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@Service
public interface SongFacade {

    SongDTO findById(Long id);

    Collection<SongDTO> findAll();

    Collection<SongDTO> findByName(String name);

    Collection<SongDTO> findByBand(BandDTO band);

    void delete(Long id);

    Long createSong(SongCreateDTO songCreateDTO);

    void changeDuration(Long id, Long newsongDuration);

    void changeBand(Long id, Long newBandId);

}
