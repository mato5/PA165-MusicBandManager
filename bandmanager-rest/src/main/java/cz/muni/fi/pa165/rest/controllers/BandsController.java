package cz.muni.fi.pa165.rest.controllers;

import cz.fi.muni.pa165.dto.BandCreateDTO;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.facade.BandFacade;
import cz.muni.fi.pa165.enums.Genre;
import cz.muni.fi.pa165.rest.ApiURIPaths;
import cz.muni.fi.pa165.rest.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotModifiedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Alexander Kromka
 */

@RestController
@RequestMapping(ApiURIPaths.ROOT_URI_BANDS)
public class BandsController {

    final static Logger logger = LoggerFactory.getLogger(BandsController.class);

    @Inject
    private BandFacade bandFacade;


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Long createBands(@Valid @RequestBody BandCreateDTO bandDTO, BindingResult result) {
        logger.debug("REST: createBands().");
        if (result.hasErrors()) throw new ResourceNotModifiedException();
        return bandFacade.create(bandDTO);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<BandDTO> getBands() {
        logger.debug("REST: getBands().");
        try {
            return bandFacade.findAll();
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final BandDTO getBandById(@PathVariable("id") long id) {
        logger.debug("REST: getBandById(" + String.valueOf(id) + ").");
        BandDTO bandDTO = bandFacade.findById(id);
        if (bandDTO == null) {
            logger.warn("REST: getBandById(" + String.valueOf(id) + ") returns Not found.");
            throw new ResourceNotFoundException();
        }
        return bandDTO;
    }

    @RequestMapping(value = "/{id}/changeManager", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changeManager(@PathVariable("id") long id, @Valid @RequestBody ManagerDTO managerDTO, BindingResult result) {
        logger.debug("REST: changeManager().");
        BandDTO bandDTO = bandFacade.findById(id);
        if (result.hasErrors()) throw new ResourceNotModifiedException();
        bandFacade.changeManager(bandDTO, managerDTO);
    }

    @RequestMapping(value = "/{id}/changeGenre", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changeGenre(@PathVariable("id") long id, @Valid @RequestBody Genre genre, BindingResult result) {
        logger.debug("REST: changeGenre().");
        BandDTO bandDTO = bandFacade.findById(id);
        if (result.hasErrors()) throw new ResourceNotModifiedException();
        bandFacade.changeGenre(bandDTO, genre);
    }

}
