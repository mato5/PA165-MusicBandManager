package cz.muni.fi.pa165.rest.controllers;

import cz.fi.muni.pa165.dto.TourCreateDTO;
import cz.fi.muni.pa165.dto.TourDTO;
import cz.fi.muni.pa165.facade.TourFacade;
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
import java.util.Date;
import java.util.List;

/**
 * @author Iurii xkuznetc Kuznetcov
 */
@RestController
@RequestMapping(ApiURIPaths.ROOT_URI_TOURS)
public class ToursController {

    final static Logger logger = LoggerFactory.getLogger(ToursController.class);

    @Inject
    private TourFacade tourFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TourDTO> getTours() {
        logger.debug("REST: getTours().");
        return (List<TourDTO>) tourFacade.getAllTours();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TourDTO getTourById(@PathVariable("id") long id) {
        logger.debug("REST: getTourById(" + String.valueOf(id) + ").");
        TourDTO tourDTO = tourFacade.findById(id);
        if (tourDTO == null) {
            logger.warn("REST: getTourById(" + String.valueOf(id) + ") returns Not found.");
            throw new ResourceNotFoundException();
        }
        return tourDTO;
    }

    @RequestMapping(value = "by_manager_id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TourDTO> getTourByManager(@PathVariable("id") long id) {
        logger.debug("REST: getTourByManager(" + String.valueOf(id) + ").");
        try {
            Collection<TourDTO> byManager = tourFacade.findByManager(id);
            return (List<TourDTO>) byManager;
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "by_band_id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TourDTO> getTourByBand(@PathVariable("id") long id) {
        logger.debug("REST: getTourByBand(" + String.valueOf(id) + ").");
        try {
            Collection<TourDTO> byBand = tourFacade.findByBand(id);
            return (List<TourDTO>) byBand;
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final TourDTO createTour(@RequestBody TourCreateDTO tourCreateDTO) throws Exception {
        logger.debug("REST: createTour(" + tourCreateDTO.toString() + ").");
        try {
            Long id = tourFacade.create(tourCreateDTO);
            return tourFacade.findById(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteTour(@PathVariable("id") long id) throws Exception {
        logger.debug("REST: deleteTour(" + String.valueOf(id) + ").");
        try {
            tourFacade.delete(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "change_name/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changeName(@PathVariable("id") long id, @RequestBody String newName) {
        logger.debug("REST: changeName(" + String.valueOf(id) + ", newName = " + String.valueOf(newName) + ").");
        try {
            tourFacade.setName(id, newName);
        } catch (Exception ex) {
            throw new ResourceNotModifiedException();
        }
    }

    @RequestMapping(value = "change_city/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changeCityName(@PathVariable("id") long id, @RequestBody String newCityName) {
        logger.debug("REST: changeCityName(" + String.valueOf(id) + ", newCityName = " + String.valueOf(newCityName) + ").");
        try {
            tourFacade.setCity(id, newCityName);
        } catch (Exception ex) {
            throw new ResourceNotModifiedException();
        }
    }

    @RequestMapping(value = "change_band/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changeBand(@PathVariable("id") long id, @RequestBody Long newBandId) {
        logger.debug("REST: changeBand(" + String.valueOf(id) + ", newBandId = " + String.valueOf(newBandId) + ").");
        try {
            tourFacade.setBand(id, newBandId);
        } catch (Exception ex) {
            throw new ResourceNotModifiedException();
        }
    }

    @RequestMapping(value = "change_datetime/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changeDatetime(@PathVariable("id") long id, @RequestBody Date newDatetime) {
        logger.debug("REST: changeDatetime(" + String.valueOf(id) + ", newDatetime = " + String.valueOf(newDatetime) + ").");
        try {
            tourFacade.setDatetime(id, newDatetime);
        } catch (Exception ex) {
            throw new ResourceNotModifiedException();
        }
    }
}
