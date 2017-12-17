package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.TourCreateDTO;
import cz.fi.muni.pa165.dto.TourDTO;
import cz.fi.muni.pa165.facade.TourFacade;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.hateoas.TourResource;
import cz.muni.fi.pa165.restapi.hateoas.TourResourceAssembler;
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
import java.util.Date;
import java.util.List;

@RestController
@ExposesResourceFor(TourDTO.class)
@RequestMapping("/tours")
public class ToursRestController {

    private final static Logger logger = LoggerFactory.getLogger(ToursRestController.class);

    private TourFacade tourFacade;
    private TourResourceAssembler tourResourceAssembler;

    public ToursRestController(@Autowired TourFacade tourFacade,
                               @Autowired TourResourceAssembler tourResourceAssembler) {
        this.tourFacade = tourFacade;
        this.tourResourceAssembler = tourResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<TourResource>> getTours() {
        logger.debug("REST: getTours().");
        List<TourResource> resourceCollection = tourResourceAssembler
                .toResources(tourFacade.getAllTours());
        Resources<TourResource> tourResources = new Resources<>(resourceCollection);
        return new ResponseEntity<>(tourResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<TourResource> getTourById(@PathVariable("id") long id) throws Exception {
        logger.debug("REST: getTourById(" + String.valueOf(id) + ").");
        TourDTO tourDTO = tourFacade.findById(id);
        if (tourDTO == null) {
            logger.error("REST: getTourById(" + String.valueOf(id) + ") returns Not found.");
            throw new ResourceNotFoundException("Tour with id " + id + " not found.");
        }
        TourResource resource = tourResourceAssembler.toResource(tourDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "by_manager_id/{id}", method = RequestMethod.GET)
    public final HttpEntity<Resources<TourResource>> getTourByManager(@PathVariable("id") long id) throws Exception {
        logger.debug("REST: getTourByManager(" + String.valueOf(id) + ").");
        List<TourResource> resourceCollection = tourResourceAssembler
                .toResources(tourFacade.findByManager(id));
        Resources<TourResource> tourResources = new Resources<>(resourceCollection);
        return new ResponseEntity<>(tourResources, HttpStatus.OK);
    }

    @RequestMapping(value = "by_band_id/{id}", method = RequestMethod.GET)
    public final HttpEntity<Resources<TourResource>> getTourByBand(@PathVariable("id") long id) throws Exception {
        logger.debug("REST: getTourByBand(" + String.valueOf(id) + ").");
        List<TourResource> resourceCollection = tourResourceAssembler
                .toResources(tourFacade.findByBand(id));
        Resources<TourResource> tourResources = new Resources<>(resourceCollection);
        return new ResponseEntity<>(tourResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<TourResource> createTour(@RequestBody @Valid TourCreateDTO tourCreateDTO, BindingResult bindingResult) throws Exception {
        logger.debug("REST: createTour(" + tourCreateDTO.toString() + ").");
        if (bindingResult.hasErrors()) {
            logger.error("Failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = tourFacade.create(tourCreateDTO);
        TourResource resource = tourResourceAssembler.toResource(tourFacade.findById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteTour(@PathVariable("id") long id) throws Exception {
        logger.debug("REST: deleteTour(" + String.valueOf(id) + ").");
        try {
            tourFacade.delete(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Tour with id " + id + " not found.");
        }
    }

    @RequestMapping(value = "change_name/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changeName(@PathVariable("id") long id, @RequestBody String newName) {
        logger.debug("REST: changeName(" + String.valueOf(id) + ", newName = " + String.valueOf(newName) + ").");
        try {
            tourFacade.setName(id, newName);
        } catch (Exception ex) {
            throw new InvalidRequestException("");
        }
    }

    @RequestMapping(value = "change_band/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changeBand(@PathVariable("id") long id, @RequestBody Long newBandId) {
        logger.debug("REST: changeBand(" + String.valueOf(id) + ", newBandId = " + String.valueOf(newBandId) + ").");
        try {
            tourFacade.setBand(id, newBandId);
        } catch (Exception ex) {
            throw new InvalidRequestException("");
        }
    }

    @RequestMapping(value = "change_datetime/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changeDatetime(@PathVariable("id") long id, @RequestBody Date newDatetime) {
        logger.debug("REST: changeDatetime(" + String.valueOf(id) + ", newDatetime = " + String.valueOf(newDatetime) + ").");
        try {
            tourFacade.setDatetime(id, newDatetime);
        } catch (Exception ex) {
            throw new InvalidRequestException("");
        }
    }

}
