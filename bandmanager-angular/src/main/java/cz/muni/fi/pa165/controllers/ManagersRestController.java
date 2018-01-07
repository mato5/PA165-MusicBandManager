package cz.muni.fi.pa165.controllers;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.facade.*;
import cz.muni.fi.pa165.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.exceptions.ServerProblemException;
import cz.muni.fi.pa165.hateoas.*;
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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@ExposesResourceFor(ManagerDTO.class)
@RequestMapping("/managers")
public class ManagersRestController {

    private final static Logger log = LoggerFactory.getLogger(MembersRestController.class);

    private ManagerFacade managerFacade;
    private BandInviteFacade bandInviteFacade;
    private ManagerResourceAssembler managerResourceAssembler;
    private BandInviteResourceAssembler bandInviteResourceAssembler;
    private TourResourceAssembler tourResourceAssembler;
    private BandResourceAssembler bandResourceAssembler;
    private SongResourceAssembler songResourceAssembler;
    private AlbumResourceAssembler albumResourceAssembler;
    private AlbumFacade albumFacade;
    private SongFacade songFacade;
    private TourFacade tourFacade;
    private BandFacade bandFacade;

    public ManagersRestController(@Autowired ManagerFacade managerFacade,
                                  @Autowired ManagerResourceAssembler managerResourceAssembler,
                                  @Autowired BandInviteResourceAssembler bandInviteResourceAssembler,
                                  @Autowired BandInviteFacade bandInviteFacade,
                                  @Autowired TourResourceAssembler tourResourceAssembler,
                                  @Autowired TourFacade tourFacade,
                                  @Autowired BandResourceAssembler bandResourceAssembler,
                                  @Autowired BandFacade bandFacade,
                                  @Autowired SongResourceAssembler songResourceAssembler,
                                  @Autowired SongFacade songFacade,
                                  @Autowired AlbumResourceAssembler albumResourceAssembler,
                                  @Autowired AlbumFacade albumFacade) {
        this.managerFacade = managerFacade;
        this.managerResourceAssembler = managerResourceAssembler;
        this.bandInviteResourceAssembler = bandInviteResourceAssembler;
        this.bandInviteFacade = bandInviteFacade;
        this.tourResourceAssembler = tourResourceAssembler;
        this.bandResourceAssembler = bandResourceAssembler;
        this.tourFacade = tourFacade;
        this.bandFacade = bandFacade;
        this.songFacade = songFacade;
        this.songResourceAssembler = songResourceAssembler;
        this.albumFacade = albumFacade;
        this.albumResourceAssembler = albumResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<ManagerResource>> getManagers() {
        log.debug("rest getManagers()");
        List<ManagerResource> resourceCollection = managerResourceAssembler
                .toResources(managerFacade.getAllManagers());
        Resources<ManagerResource> managerResources = new Resources<>(resourceCollection,
                linkTo(ManagersRestController.class).withSelfRel(),
                linkTo(ManagersRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(managerResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<ManagerResource> getManager(@PathVariable("id") long id) throws Exception {
        log.debug("rest getManager({})", id);
        ManagerDTO managerDTO = managerFacade.findManagerById(id);
        if (managerDTO == null) throw new ResourceNotFoundException("Manager " + id + " not found");
        ManagerResource resource = managerResourceAssembler.toResource(managerDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<ManagerResource> registerManager(@RequestBody @Valid ManagerDTO managerDTO, BindingResult bindingResult) throws Exception {
        log.debug("rest registerManager()");

        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = managerFacade.registerManager(managerDTO, managerDTO.getPassword());
        ManagerResource resource = managerResourceAssembler.toResource(managerFacade.findManagerById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    //TODO create band
    @RequestMapping(value = "/create_band", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<BandResource> createBand(@RequestBody @Valid BandCreateDTO bandCreateDTO, BindingResult bindingResult) throws Exception {
        log.debug("rest createBand");

        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        ManagerDTO managerDTO = managerFacade.findManagerById(bandCreateDTO.getManagerId());
        Long id = managerFacade.createBand(managerDTO, bandCreateDTO);
        BandResource resource = bandResourceAssembler.toResource(bandFacade.findById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    //TODO add new song
    @RequestMapping(value = "/create_song", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<SongResource> createSong(@RequestBody @Valid SongCreateDTO songCreateDTO, BindingResult bindingResult) throws Exception {
        log.debug("rest createSong");

        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        //BandDTO band = bandFacade.findById(songCreateDTO.getBandId());

        Long id = managerFacade.addNewSong(null, songCreateDTO);
        SongResource resource = songResourceAssembler.toResource(songFacade.findById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    //TODO add new album
    @RequestMapping(value = "/create_album", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<AlbumResource> createAlbum(@RequestBody @Valid AlbumCreateDTO albumCreateDTO, BindingResult bindingResult) throws Exception {
        log.debug("rest createAlbum");

        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        //BandDTO band = bandFacade.findById(songCreateDTO.getBandId());

        Long id = managerFacade.addNewAlbum(null, albumCreateDTO);
        AlbumResource resource = albumResourceAssembler.toResource(albumFacade.findById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/add_to_album", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<AlbumResource> addSongToAlbum(@RequestBody @Valid SongToAlbumDTO songToAlbumDTO, BindingResult bindingResult) throws Exception {
        log.debug("rest add to Album");

        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        //BandDTO band = bandFacade.findById(songCreateDTO.getBandId());
        managerFacade.addSongToAlbum(null, songToAlbumDTO);

        //Long id = managerFacade.addNewAlbum(null,albumCreateDTO);
        AlbumResource resource = albumResourceAssembler.toResource(albumFacade.findById(songToAlbumDTO.getAlbumId()));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove_from_album", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<AlbumResource> removeSongFromAlbum(@RequestBody @Valid SongToAlbumDTO songToAlbumDTO, BindingResult bindingResult) throws Exception {
        log.debug("rest remove from Album");

        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        //BandDTO band = bandFacade.findById(songCreateDTO.getBandId());
        managerFacade.removeSongFromAlbum(null, songToAlbumDTO);

        //Long id = managerFacade.addNewAlbum(null,albumCreateDTO);
        AlbumResource resource = albumResourceAssembler.toResource(albumFacade.findById(songToAlbumDTO.getAlbumId()));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    @RequestMapping(value = "/send_invite", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<BandInviteResource> sendInvite(@RequestBody @Valid BandInviteDTO bandInviteDTO, BindingResult bindingResult) throws Exception {
        log.debug("rest sendInvite");

        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = managerFacade.sendBandInvite(bandInviteDTO.getManager(), bandInviteDTO);
        BandInviteResource resource = bandInviteResourceAssembler.toResource(bandInviteFacade.findById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/send_invite_create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<BandInviteResource> sendInviteCreate(@RequestBody @Valid BandInviteCreateDTO bandInviteDTO, BindingResult bindingResult) throws Exception {
        log.error("rest sendInviteCreate" + bandInviteDTO);
        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = managerFacade.sendBandInvite(bandInviteDTO);
        BandInviteResource resource = bandInviteResourceAssembler.toResource(bandInviteFacade.findById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
    @RequestMapping(value = "/create_tour", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<TourResource> createTour(@RequestBody @Valid TourCreateDTO tourCreateDTO, BindingResult bindingResult) throws Exception {
        log.debug("rest createTour");

        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = managerFacade.createTour(tourCreateDTO);
        TourResource resource = tourResourceAssembler.toResource(tourFacade.findById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<ManagerResource> loginUser(@RequestBody @Valid UserAuthDTO credentials, BindingResult bindingResult) throws Exception {

        log.debug("rest: loginUser(" + credentials.getEmail().toString() + ")");
        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }

        try {
            if (!managerFacade.authenticate(credentials)) {
                throw new Exception("Failed to authenticate");
            }

            ManagerDTO managerDTO = managerFacade.findManagerById(credentials.getId());
            ManagerResource resource = managerResourceAssembler.toResource(managerDTO);

            return new ResponseEntity<>(resource, HttpStatus.OK);

        } catch (Throwable ex) {
            log.error("User " + credentials.getEmail().toString() + " doesn't exists");
            Throwable rootCause = ex;
            while ((ex = ex.getCause()) != null) {
                rootCause = ex;
                log.error("caused by : " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
            throw new ServerProblemException(rootCause.getMessage());
        }
    }

}
