package cz.muni.fi.pa165.controllers;

import cz.fi.muni.pa165.dto.BandCreateDTO;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.facade.BandFacade;
import cz.fi.muni.pa165.facade.SongFacade;
import cz.muni.fi.pa165.enums.Genre;
import cz.muni.fi.pa165.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.hateoas.BandResource;
import cz.muni.fi.pa165.hateoas.BandResourceAssembler;
import cz.muni.fi.pa165.hateoas.SongResource;
import cz.muni.fi.pa165.hateoas.SongResourceAssembler;
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
 * @author Alexander Kromka
 */

@RestController
@ExposesResourceFor(BandDTO.class)
@RequestMapping("/bands")
public class BandsRestController {

    final static Logger logger = LoggerFactory.getLogger(BandsRestController.class);

    private BandFacade bandFacade;
    private SongFacade songFacade;
    private BandResourceAssembler bandResourceAssembler;
    private SongResourceAssembler songResourceAssembler;

    public BandsRestController(@Autowired BandFacade bandFacade,
                               @Autowired BandResourceAssembler bandResourceAssembler,
                               @Autowired SongFacade songFacade,
                               @Autowired SongResourceAssembler songResourceAssembler) {
        this.bandFacade = bandFacade;
        this.bandResourceAssembler = bandResourceAssembler;
        this.songFacade = songFacade;
        this.songResourceAssembler = songResourceAssembler;
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<BandResource> createBand(@RequestBody @Valid BandCreateDTO bandDTO, BindingResult bindingResult) throws Exception {
        logger.debug("REST registerBand()");

        if (bindingResult.hasErrors()) {
            logger.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = bandFacade.create(bandDTO);
        BandResource resource = bandResourceAssembler.toResource(bandFacade.findById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<BandResource>> getBands() {
        logger.debug("REST: getBands().");
        List<BandResource> resourceCollection = bandResourceAssembler
                .toResources(bandFacade.findAll());
        Resources<BandResource> bandResources = new Resources<>(resourceCollection);
        return new ResponseEntity<>(bandResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<BandResource> getBand(@PathVariable("id") long id) throws Exception {
        logger.debug("REST getBand({})", id);
        BandDTO bandDTO = bandFacade.findById(id);
        if (bandDTO == null) throw new ResourceNotFoundException("Band " + id + " not found");
        BandResource resource = bandResourceAssembler.toResource(bandDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/songs/{id}", method = RequestMethod.GET)
    public final HttpEntity<Resources<SongResource>> getSongsByBand(@PathVariable("id") long id) throws Exception {
        logger.debug("REST getSongs({})", id);

        List<SongResource> resourceCollection = songResourceAssembler.toResources(songFacade.findByBand(id));
        Resources<SongResource> songResources = new Resources<>(resourceCollection);
        return new ResponseEntity<>(songResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/by_manager_id/{id}", method = RequestMethod.GET)
    public final HttpEntity<Resources<BandResource>> getByManagerId(@PathVariable("id") long managerId) throws Exception {
        logger.debug("REST getByManagerId({})", managerId);
        List<BandResource> resourceCollection = bandResourceAssembler.toResources(
                bandFacade.findByManagerId(managerId));
        Resources<BandResource> bandResources = new Resources<>(resourceCollection);
        return new ResponseEntity<>(bandResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/changeManager", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<BandResource> changeManager(@PathVariable("id") long id, @Valid @RequestBody ManagerDTO managerDTO, BindingResult result) {
        logger.debug("REST: changeManager().");
        BandDTO bandDTO = bandFacade.findById(id);
        if (result.hasErrors()) throw new InvalidRequestException("InvalidRequestException");
        bandFacade.changeManager(bandDTO, managerDTO);
        BandResource resource = bandResourceAssembler.toResource(bandDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/changeGenre", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<BandResource> changeGenre(@PathVariable("id") long id, @Valid @RequestBody Genre genre, BindingResult result) {
        logger.debug("REST: changeGenre().");
        BandDTO bandDTO = bandFacade.findById(id);
        if (result.hasErrors()) throw new InvalidRequestException("InvalidRequestException");
        bandFacade.changeGenre(bandDTO, genre);
        BandResource resource = bandResourceAssembler.toResource(bandDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}
