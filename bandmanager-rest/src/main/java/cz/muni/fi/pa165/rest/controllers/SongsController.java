package cz.muni.fi.pa165.rest.controllers;


import cz.fi.muni.pa165.dto.SongCreateDTO;
import cz.fi.muni.pa165.dto.SongDTO;
import cz.fi.muni.pa165.facade.SongFacade;
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
@RequestMapping(ApiURIPaths.ROOT_URI_SONGS)
public class SongsController {

    final static Logger logger = LoggerFactory.getLogger(SongsController.class);

    @Inject
    private SongFacade songFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<SongDTO> getSongs() {
        logger.debug("REST: getSongs().");
        return (List<SongDTO>) songFacade.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final SongDTO getSongById(@PathVariable("id") long id) {
        logger.debug("REST: getSongById(" + String.valueOf(id) + ").");
        SongDTO songDTO = songFacade.findById(id);
        if (songDTO == null) {
            logger.warn("REST: getSongById(" + String.valueOf(id) + ") returns Not found.");
            throw new ResourceNotFoundException();
        }
        return songDTO;
    }

    @RequestMapping(value = "by_band_id/{band_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<SongDTO> getSongsByBand(@PathVariable("band_id") long band_id) {
        logger.debug("REST: getSongsByBand(" + String.valueOf(band_id) + ").");
        try {
            Collection<SongDTO> byBand = songFacade.findByBand(band_id);
            return (List<SongDTO>) byBand;
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "by_name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<SongDTO> getSongsByName(@PathVariable("name") String name) {
        logger.debug("REST: getSongsByName(" + name + ").");
        try {
            Collection<SongDTO> byName = songFacade.findByName(name);
            return (List<SongDTO>) byName;
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteSong(@PathVariable("id") long id) throws Exception {
        logger.debug("REST: deleteSong(" + String.valueOf(id) + ").");
        try {
            songFacade.delete(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final SongDTO createSong(@RequestBody SongCreateDTO songCreateDTO) throws Exception {
        logger.debug("REST: createSong(" + songCreateDTO.toString() + ").");
        try {
            Long id = songFacade.createSong(songCreateDTO);
            return songFacade.findById(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(value = "change_duration/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changeDuraton(@PathVariable("id") long id, @RequestBody Long newDuration) {
        logger.debug("REST: changeDuraton(" + String.valueOf(id) + ", newDuration = " + String.valueOf(newDuration) + ").");
        try {
            songFacade.changeDuration(id, newDuration);
        } catch (Exception ex) {
            throw new ResourceNotModifiedException();
        }
    }

    @RequestMapping(value = "change_band/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changeBand(@PathVariable("id") long id, @RequestBody Long newBandId) {
        logger.debug("REST: changeBand(" + String.valueOf(id) + ", newBandId = " + String.valueOf(newBandId) + ").");
        try {
            songFacade.changeBand(id, newBandId);
        } catch (Exception ex) {
            throw new ResourceNotModifiedException();
        }
    }
}
