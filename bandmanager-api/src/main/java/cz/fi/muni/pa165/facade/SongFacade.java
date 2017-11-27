package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.SongCreateDTO;
import cz.fi.muni.pa165.dto.SongDTO;

import java.util.Collection;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

public interface SongFacade {

    public SongDTO findSongById(Long id);

    public Collection<SongDTO> findAll();

    public Collection<SongDTO> findSongsByName(String name);

    public Collection<SongDTO> findSongsByBand(BandDTO band);

    public void delete(SongDTO songDTO);

    public Long createSong(SongCreateDTO songCreateDTO);

    public void changeDuration(SongDTO songDTO, Long newsongDuration);

    public void changeBand(SongDTO songDTO, BandDTO newBandDTO);

}
