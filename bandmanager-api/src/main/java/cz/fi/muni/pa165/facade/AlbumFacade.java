package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.AlbumCreateDTO;
import cz.fi.muni.pa165.dto.AlbumDTO;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.SongDTO;

import java.util.Collection;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

public interface AlbumFacade {

    public AlbumDTO findAlbumById(Long id);

    public Collection<AlbumDTO> findAll();

    public Collection<AlbumDTO> findByBand(BandDTO bandDTO);

    public void addSong(AlbumDTO albumDTO, SongDTO songDTO);

    public void deleteSong(AlbumDTO albumDTO, SongDTO songDTO);

    public void deleteAlbum(AlbumDTO albumDTO);

    public Long createAlbum(AlbumCreateDTO albumCreateDTO);
}
