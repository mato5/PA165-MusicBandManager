package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.BandInviteDTO;
import cz.fi.muni.pa165.dto.MemberDTO;
import cz.fi.muni.pa165.facade.MemberFacade;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.hateoas.*;
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
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@ExposesResourceFor(MemberDTO.class)
@RequestMapping("/members")
public class MembersRestController {

    private final static Logger log = LoggerFactory.getLogger(MembersRestController.class);

    private MemberFacade memberFacade;
    private MemberResourceAssembler memberResourceAssembler;
    private TourResourceAssembler tourResourceAssembler;
    private BandInviteResourceAssembler bandInviteResourceAssembler;


    public MembersRestController(@Autowired MemberFacade memberFacade,
                                 @Autowired MemberResourceAssembler memberResourceAssembler,
                                 @Autowired TourResourceAssembler tourResourceAssembler,
                                 @Autowired BandInviteResourceAssembler bandInviteResourceAssembler) {
        this.memberFacade = memberFacade;
        this.memberResourceAssembler = memberResourceAssembler;
        this.tourResourceAssembler = tourResourceAssembler;
        this.bandInviteResourceAssembler = bandInviteResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<MemberResource>> getMembers() {
        log.debug("rest getMembers()");
        List<MemberResource> resourceCollection = memberResourceAssembler
                .toResources(memberFacade.getAllMembers());
        Resources<MemberResource> memberResources = new Resources<>(resourceCollection,
                linkTo(MembersRestController.class).withSelfRel(),
                linkTo(MembersRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(memberResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<MemberResource> getMember(@PathVariable("id") long id) throws Exception {
        log.debug("rest getMember({})", id);
        MemberDTO memberDTO = memberFacade.findMemberById(id);
        if (memberDTO == null) throw new ResourceNotFoundException("Member " + id + " not found");
        MemberResource resource = memberResourceAssembler.toResource(memberDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<MemberResource> registerMember(@RequestBody @Valid MemberDTO memberDTO, BindingResult bindingResult) throws Exception {
        log.debug("rest registerMember()");

        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id = memberFacade.registerMember(memberDTO, memberDTO.getPassword());
        MemberResource resource = memberResourceAssembler.toResource(memberFacade.findMemberById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "accept_invite/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<MemberResource> acceptInvite(@RequestBody Long bandInvId, @PathVariable("id") long memberId) throws Exception {
        log.debug("rest acceptBandInvite() id: " + bandInvId);
        try {
            memberFacade.acceptBandInvite(memberId, bandInvId);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Cannot accept bandInvite id: " + bandInvId + " of a member id: " + memberId);
        }

        MemberResource resource = memberResourceAssembler.toResource(memberFacade.findMemberById(memberId));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "decline_invite/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<MemberResource> declineInvite(@RequestBody Long bandInvId, @PathVariable("id") long memberId) throws Exception {
        log.debug("rest declineBandInvite() id: " + bandInvId);
        try {
            memberFacade.declineBandInvite(memberId, bandInvId);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Cannot decline bandInvite id: " + bandInvId + " of a member id: " + memberId);
        }

        MemberResource resource = memberResourceAssembler.toResource(memberFacade.findMemberById(memberId));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "bandmates/{id}", method = RequestMethod.GET)
    public final HttpEntity<Resources<MemberResource>> getBandmates(@PathVariable("id") long id) throws Exception {
        System.out.println("Im here");
        log.debug("rest getBandmates() of a member id: " + id);
        List<MemberResource> resourceCollection = null;
        try {
            resourceCollection = memberResourceAssembler
                    .toResources(memberFacade.listBandmates(id));
            System.out.println(memberFacade.listBandmates(id).size());
        } catch (Exception ex) {
            throw new InvalidRequestException("Member id:" + id + " is not a member of any band");
        }
        Resources<MemberResource> memberResources = new Resources<>(resourceCollection,
                linkTo(MembersRestController.class).withSelfRel());
        return new ResponseEntity<>(memberResources, HttpStatus.OK);
    }

    @RequestMapping(value = "activities/{id}", method = RequestMethod.GET)
    public final HttpEntity<Resources<TourResource>> getActivities(@PathVariable("id") long id) throws Exception {
        log.debug("rest getActivities() of a member id: " + id);
        List<TourResource> resourceCollection = null;
        try {
            resourceCollection = tourResourceAssembler
                    .toResources(memberFacade.listAllActivities(id));
        } catch (Exception ex) {
            throw new InvalidRequestException("Member id:" + id + " is not a member of any band");
        }
        Resources<TourResource> tourResources = new Resources<>(resourceCollection,
                linkTo(ToursRestController.class).withSelfRel());
        return new ResponseEntity<>(tourResources, HttpStatus.OK);
    }

    @RequestMapping(value = "invites/{id}", method = RequestMethod.GET)
    public final HttpEntity<Resources<BandInviteResource>> getInvites(@PathVariable("id") long id) throws Exception {
        log.debug("rest getInvites() of a member id: " + id);
        List<BandInviteResource> resourceCollection = null;
        try {
            List<BandInviteDTO> list = new ArrayList<>(memberFacade.listAllMemberInvites(id));
            MemberDTO missingDTO = memberFacade.findMemberById(id);
            for(BandInviteDTO b : list){
                b.setMember(missingDTO);
            }
            resourceCollection = bandInviteResourceAssembler
                    .toResources(list);
        } catch (Exception ex) {
            throw new InvalidRequestException("Member id:" + id + " is already a member of a band");
        }
        Resources<BandInviteResource> inviteResources = new Resources<>(resourceCollection,
                linkTo(BandInvitesRestController.class).withSelfRel());
        return new ResponseEntity<>(inviteResources, HttpStatus.OK);
    }

}
