package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.AlbumCreateDTO;
import cz.fi.muni.pa165.dto.AlbumDTO;
import cz.fi.muni.pa165.dto.BandDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@Service
public interface AlbumFacade {

    AlbumDTO findById(Long id);

    Collection<AlbumDTO> findAll();

    Collection<AlbumDTO> findByBand(BandDTO bandDTO);

    void addSong(Long albumId, Long songId);

    void deleteSong(Long albumId, Long songId);

    void deleteAlbum(Long id);

    public Long createAlbum(AlbumCreateDTO albumCreateDTO);
}
