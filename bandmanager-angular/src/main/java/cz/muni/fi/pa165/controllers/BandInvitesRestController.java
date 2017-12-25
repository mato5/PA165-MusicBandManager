package cz.muni.fi.pa165.controllers;

//TODO

import cz.fi.muni.pa165.dto.BandInviteDTO;
import cz.fi.muni.pa165.facade.BandInviteFacade;
import cz.muni.fi.pa165.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.exceptions.ServerProblemException;
import cz.muni.fi.pa165.hateoas.BandInviteResource;
import cz.muni.fi.pa165.hateoas.BandInviteResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(BandInviteDTO.class)
@RequestMapping("/bandinvites")
public class BandInvitesRestController {
    private final static Logger log = LoggerFactory.getLogger(BandInvitesRestController.class);
    
    private BandInviteFacade bandInviteFacade;
    private BandInviteResourceAssembler bandInviteResourceAssembler; 
    
    public BandInvitesRestController(   @Autowired BandInviteFacade bandInviteFacade,
                                        @Autowired BandInviteResourceAssembler bandInviteResourceAssembler
            ) {
        this.bandInviteFacade = bandInviteFacade;
        this.bandInviteResourceAssembler = bandInviteResourceAssembler;
    }
    /*
    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<BandInviteResource>> getBandIvites() {
        log.debug("rest getBandInvites()");
        List<BandInviteResource> resourceCollection = this.bandInviteResourceAssembler.toResources(this.bandInviteFacade.getAllBandInvites());
        Resources<BandInviteResource> bandInvitesResources = new Resources<>(resourceCollection,
                linkTo(BandInvitesRestController.class).withSelfRel(),
                linkTo(BandInvitesRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(bandInvitesResources, HttpStatus.OK);
    }*/
    
    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<BandInviteResource>> getSpecificBandInvites(
            @RequestParam(value = "memId", required = false) Long memberId,
            @RequestParam(value = "bandId", required = false) Long bandId,
            @RequestParam(value = "manId", required = false) Long managerId
            ) {
        log.debug("rest getSpecificBandInvites()");
        List<BandInviteResource> resourceCollection = this.bandInviteResourceAssembler.toResources(this.bandInviteFacade.getAllBandInvites()
                .stream()
                .filter(invite -> memberId == null || invite.getMember().getId() == memberId)
                .filter(invite -> bandId == null || invite.getBand().getId() == bandId)
                .filter(invite -> managerId == null || invite.getManager().getId() == managerId)
                .collect(Collectors.toList()));
        
        Resources<BandInviteResource> bandInvitesResources = new Resources<>(resourceCollection,
                linkTo(BandInvitesRestController.class).withSelfRel(),
                linkTo(BandInvitesRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(bandInvitesResources, HttpStatus.OK);
    } 
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)  
    public final HttpEntity<BandInviteResource> getBandInvite(@PathVariable("id") long id) throws Exception {
        log.debug("rest getBandInvites({})", id);
        BandInviteDTO bandInviteDTO = this.bandInviteFacade.findById(id);
        if (bandInviteDTO == null) throw new ResourceNotFoundException("bandInvite " + id + " not found");
        BandInviteResource resource = bandInviteResourceAssembler.toResource(bandInviteDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteBandInvite(@PathVariable("id") long id) throws Exception {
        log.debug("rest deleteBandInvite({})", id);
        try {
            this.bandInviteFacade.delete(this.bandInviteFacade.findById(id));
        } catch (IllegalArgumentException ex) {
            log.error("bandInvite " + id + " not found");
            throw new ResourceNotFoundException("bandInvite " + id + " not found");
        } catch (Throwable ex) {
            log.error("cannot delete bandInvite " + id + " :" + ex.getMessage());
            Throwable rootCause=ex;
            while ((ex = ex.getCause()) != null) {
                rootCause = ex;
                log.error("caused by : " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
            throw new ServerProblemException(rootCause.getMessage());
        }
    }
    
    
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<BandInviteResource> createProduct(@RequestBody @Valid BandInviteDTO bandInvite, BindingResult bindingResult) throws Exception {
        log.debug("rest createProduct()");
        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        BandInviteDTO created = bandInviteFacade.create(bandInvite);
        BandInviteResource resource = this.bandInviteResourceAssembler.toResource(created);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
