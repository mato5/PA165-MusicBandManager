package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.SongCreateDTO;
import cz.fi.muni.pa165.dto.SongDTO;
import cz.fi.muni.pa165.dto.TourDTO;
import cz.fi.muni.pa165.facade.SongFacade;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.hateoas.SongResource;
import cz.muni.fi.pa165.restapi.hateoas.SongResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@ExposesResourceFor(SongDTO.class)
@RequestMapping("/songs")
public class SongsRestController {

    private final static Logger logger = LoggerFactory.getLogger(SongsRestController.class);

    private SongFacade songFacade;
    private SongResourceAssembler songResourceAssembler;

    public SongsRestController(@Autowired SongFacade songFacade,
                               @Autowired SongResourceAssembler songResourceAssembler) {
        this.songFacade = songFacade;
        this.songResourceAssembler = songResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<SongResource>> getAlbums() {
        logger.debug("REST: getSongs().");
        List<SongResource> resourceCollection = songResourceAssembler
                .toResources(songFacade.findAll());
        Resources<SongResource> songResources = new Resources<>(resourceCollection);
        return new ResponseEntity<>(songResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<SongResource> getTourById(@PathVariable("id") long id) throws Exception {
        logger.debug("REST: getSongById(" + String.valueOf(id) + ").");
        SongDTO songDTO = songFacade.findById(id);
        if (songDTO == null) {
            logger.warn("REST: getSongById(" + String.valueOf(id) + ") returns Not found.");
            throw new ResourceNotFoundException("Song with id " + id + " not found.");
        }
        SongResource resource = songResourceAssembler.toResource(songDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "by_band_id/{id}", method = RequestMethod.GET)
    public final HttpEntity<Resources<SongResource>> getTourByBand(@PathVariable("band_id") long band_id) throws Exception {
        logger.debug("REST: getSongsByBand(" + String.valueOf(band_id) + ").");
        List<SongResource> resourceCollection = songResourceAssembler
                .toResources(songFacade.findByBand(band_id));
        Resources<SongResource> songResources = new Resources<>(resourceCollection);
        return new ResponseEntity<>(songResources, HttpStatus.OK);
    }

    @RequestMapping(value = "by_name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resources<SongResource>> getSongsByName(@PathVariable("name") String name) {
        logger.debug("REST: getSongsByName(" + name + ").");
        List<SongResource> resourceCollection = songResourceAssembler
                .toResources(songFacade.findByName(name));
        Resources<SongResource> songResources = new Resources<>(resourceCollection);
        return new ResponseEntity<>(songResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteSong(@PathVariable("id") long id) throws Exception {
        logger.debug("REST: deleteSong(" + String.valueOf(id) + ").");
        try {
            songFacade.delete(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Song with id " + id + " not found.");
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<SongResource> createSong(@RequestBody @Valid SongCreateDTO songCreateDTO, BindingResult bindingResult) throws Exception {
        logger.debug("REST: createSong(" + songCreateDTO.toString() + ").");
        if (bindingResult.hasErrors()) {
            logger.error("Failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = songFacade.createSong(songCreateDTO);
        SongResource resource = songResourceAssembler.toResource(songFacade.findById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "change_duration/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changeDuraton(@PathVariable("id") long id, @RequestBody Long newDuration) {
        logger.debug("REST: changeDuraton(" + String.valueOf(id) + ", newDuration = " + String.valueOf(newDuration) + ").");
        try {
            songFacade.changeDuration(id, newDuration);
        } catch (Exception ex) {
            throw new InvalidRequestException("");
        }
    }

    @RequestMapping(value = "change_band/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changeBand(@PathVariable("id") long id, @RequestBody Long newBandId) {
        logger.debug("REST: changeBand(" + String.valueOf(id) + ", newBandId = " + String.valueOf(newBandId) + ").");
        try {
            songFacade.changeBand(id, newBandId);
        } catch (Exception ex) {
            throw new InvalidRequestException("");
        }
    }
}
