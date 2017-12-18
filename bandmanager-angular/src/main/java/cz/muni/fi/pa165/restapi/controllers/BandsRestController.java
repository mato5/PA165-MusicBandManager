package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.BandCreateDTO;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.facade.BandFacade;
import cz.muni.fi.pa165.enums.Genre;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.hateoas.BandResource;
import cz.muni.fi.pa165.restapi.hateoas.BandResourceAssembler;
import jdk.management.resource.ResourceRequestDeniedException;
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

import static org.springframework.hateoas.jaxrs.JaxRsLinkBuilder.linkTo;

/**
 * @author Alexander Kromka
 */

@RestController
@ExposesResourceFor(BandDTO.class)
@RequestMapping("/bands")
public class BandsRestController {

    final static Logger logger = LoggerFactory.getLogger(BandsRestController.class);

    private BandFacade bandFacade;
    private BandResourceAssembler bandResourceAssembler;

    public BandsRestController(@Autowired BandFacade bandFacade, @Autowired BandResourceAssembler bandResourceAssembler) {
        this.bandFacade = bandFacade;
        this.bandResourceAssembler = bandResourceAssembler;
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

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resources<BandResource>> getBands() {
        logger.debug("REST: getBands().");
        List<BandResource> resourceCollection = bandResourceAssembler
                .toResources(bandFacade.findAll());
        Resources<BandResource> bandResources = new Resources<>(resourceCollection,
                linkTo(BandsRestController.class).withSelfRel(),
                linkTo(BandsRestController.class).slash("/create").withRel("create"));
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

    @RequestMapping(value = "/{id}/changeManager", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<BandResource> changeManager(@PathVariable("id") long id, @Valid @RequestBody ManagerDTO managerDTO, BindingResult result) {
        logger.debug("REST: changeManager().");
        BandDTO bandDTO = bandFacade.findById(id);
        if (result.hasErrors()) throw new ResourceRequestDeniedException();
        bandFacade.changeManager(bandDTO, managerDTO);
        BandResource resource = bandResourceAssembler.toResource(bandDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/changeGenre", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<BandResource> changeGenre(@PathVariable("id") long id, @Valid @RequestBody Genre genre, BindingResult result) {
        logger.debug("REST: changeGenre().");
        BandDTO bandDTO = bandFacade.findById(id);
        if (result.hasErrors()) throw new ResourceRequestDeniedException();
        bandFacade.changeGenre(bandDTO, genre);
        BandResource resource = bandResourceAssembler.toResource(bandDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}
