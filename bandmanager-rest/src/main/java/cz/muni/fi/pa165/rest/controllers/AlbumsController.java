package cz.muni.fi.pa165.rest.controllers;

import cz.fi.muni.pa165.dto.AlbumCreateDTO;
import cz.fi.muni.pa165.dto.AlbumDTO;
import cz.fi.muni.pa165.facade.AlbumFacade;
import cz.muni.fi.pa165.rest.ApiURIPaths;
import cz.muni.fi.pa165.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotModifiedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@RestController
@RequestMapping(ApiURIPaths.ROOT_URI_ALBUMS)
public class AlbumsController {

    final static Logger logger = LoggerFactory.getLogger(AlbumsController.class);

    @Inject
    private AlbumFacade albumFacade;


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AlbumDTO> getAlbums() {
        logger.debug("REST: getAlbums().");
        return (List<AlbumDTO>) albumFacade.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final AlbumDTO getAlbumById(@PathVariable("id") long id) {
        logger.debug("REST: getAlbumById(" + String.valueOf(id) + ").");
        AlbumDTO albumDTO = albumFacade.findById(id);
        if (albumDTO == null) {
            logger.warn("REST: getSongById(" + String.valueOf(id) + ") returns Not found.");
            throw new ResourceNotFoundException();
        }
        return albumDTO;
    }

    @RequestMapping(value = "by_band_id/{band_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AlbumDTO> getAlbumsByBand(@PathVariable("band_id") long band_id) {
        logger.debug("REST: getAlbumsByBand(" + String.valueOf(band_id) + ").");
        try {
            Collection<AlbumDTO> byBand = albumFacade.findByBand(band_id);
            return (List<AlbumDTO>) byBand;
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteAlbum(@PathVariable("id") long id) throws Exception {
        logger.debug("REST: deleteAlbum(" + String.valueOf(id) + ").");
        try {
            albumFacade.deleteAlbum(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final AlbumDTO createAlbum(@RequestBody AlbumCreateDTO albumCreateDTO) throws Exception {
        logger.debug("REST: createAlbum(" + albumCreateDTO.toString() + ").");
        try {
            Long id = albumFacade.createAlbum(albumCreateDTO);
            return albumFacade.findById(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(value = "/{id}/songs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final AlbumDTO addSong(@PathVariable("id") long id, @RequestBody Long songId) throws Exception {
        logger.debug("REST: addSong(" + String.valueOf(id) + ", songId = " + String.valueOf(songId) + " ).");
        try {
            albumFacade.addSong(id, songId);
            return albumFacade.findById(id);
        } catch (Exception ex) {
            throw new ResourceNotModifiedException();
        }
    }
    
    @RequestMapping(value = "/{id}/songs", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final AlbumDTO deleteSong(@PathVariable("id") long id, @RequestBody Long songId) throws Exception {
        logger.debug("REST: deleteSong(" + String.valueOf(id) + ", songId = " + String.valueOf(songId) + " ).");
        try {
            albumFacade.deleteSong(id, songId);
            return albumFacade.findById(id);
        } catch (Exception ex) {
            throw new ResourceNotModifiedException();
        }
    }
}
