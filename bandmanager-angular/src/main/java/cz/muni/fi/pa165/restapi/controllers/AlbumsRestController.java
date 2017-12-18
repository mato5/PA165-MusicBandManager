package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.AlbumCreateDTO;
import cz.fi.muni.pa165.dto.AlbumDTO;
import cz.fi.muni.pa165.facade.AlbumFacade;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.hateoas.AlbumResource;
import cz.muni.fi.pa165.restapi.hateoas.AlbumResourceAssembler;
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

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@RestController
@ExposesResourceFor(AlbumDTO.class)
@RequestMapping("/albums")
public class AlbumsRestController {

    private final static Logger logger = LoggerFactory.getLogger(ToursRestController.class);

    private AlbumFacade albumFacade;
    private AlbumResourceAssembler albumResourceAssembler;

    public AlbumsRestController(@Autowired AlbumFacade albumFacade,
                                @Autowired AlbumResourceAssembler albumResourceAssembler) {
        this.albumFacade = albumFacade;
        this.albumResourceAssembler = albumResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<AlbumResource>> getAlbums() {
        logger.debug("REST: getAlbums().");
        List<AlbumResource> resourceCollection = albumResourceAssembler
                .toResources(albumFacade.findAll());
        Resources<AlbumResource> albumResources = new Resources<>(resourceCollection);
        return new ResponseEntity<>(albumResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<AlbumResource> getAlbumById(@PathVariable("id") long id) throws Exception {
        logger.debug("REST: getAlbumById(" + String.valueOf(id) + ").");
        AlbumDTO albumDTO = albumFacade.findById(id);
        if (albumDTO == null) {
            logger.warn("REST: getAlbumById(" + String.valueOf(id) + ") returns Not found.");
            throw new ResourceNotFoundException("Album with id " + id + " not found.");
        }
        AlbumResource resource = albumResourceAssembler.toResource(albumDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "by_band_id/{id}", method = RequestMethod.GET)
    public final HttpEntity<Resources<AlbumResource>> getAlbumsByBand(@PathVariable("id") long band_id) throws Exception {
        logger.debug("REST: getAlbumsByBand(" + String.valueOf(band_id) + ").");
        List<AlbumResource> resourceCollection = albumResourceAssembler
                .toResources(albumFacade.findByBand(band_id));
        Resources<AlbumResource> albumResources = new Resources<>(resourceCollection);
        return new ResponseEntity<>(albumResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteAlbum(@PathVariable("id") long id) throws Exception {
        logger.debug("REST: deleteAlbum(" + String.valueOf(id) + ").");
        try {
            albumFacade.deleteAlbum(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Album with id " + id + " not found.");
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<AlbumResource> createAlbum(@RequestBody @Valid AlbumCreateDTO albumCreateDTO, BindingResult bindingResult) throws Exception {
        logger.debug("REST: createAlbum(" + albumCreateDTO.toString() + ").");
        if (bindingResult.hasErrors()) {
            logger.error("Failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = albumFacade.createAlbum(albumCreateDTO);
        AlbumResource resource = albumResourceAssembler.toResource(albumFacade.findById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/songs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<AlbumResource> addSong(@PathVariable("id") long id, @RequestBody Long songId) throws Exception {
        logger.debug("REST: addSong(" + String.valueOf(id) + ", songId = " + String.valueOf(songId) + " ).");
        try {
            albumFacade.addSong(id, songId);
        } catch (Exception ex) {
            throw new InvalidRequestException("");
        }
        AlbumDTO byId = albumFacade.findById(id);
        AlbumResource resource = albumResourceAssembler.toResource(byId);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/songs", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<AlbumResource> deleteSong(@PathVariable("id") long id, @RequestBody Long songId) throws Exception {
        logger.debug("REST: deleteSong(" + String.valueOf(id) + ", songId = " + String.valueOf(songId) + " ).");
        try {
            albumFacade.deleteSong(id, songId);
        } catch (Exception ex) {
            throw new InvalidRequestException("");
        }
        AlbumDTO byId = albumFacade.findById(id);
        AlbumResource resource = albumResourceAssembler.toResource(byId);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
