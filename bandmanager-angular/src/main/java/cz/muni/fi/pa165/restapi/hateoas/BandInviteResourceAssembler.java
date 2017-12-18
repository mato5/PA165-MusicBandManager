package cz.muni.fi.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.BandInviteDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.dto.MemberDTO;
import cz.muni.fi.pa165.restapi.controllers.BandInvitesRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class BandInviteResourceAssembler extends ResourceAssemblerSupport<BandInviteDTO,BandInviteResource> {

    private EntityLinks entityLinks;

    private final static Logger log = LoggerFactory.getLogger(BandInviteResourceAssembler.class);

    public BandInviteResourceAssembler(@SuppressWarnings("SpringJavaAutowiringInspection")
                                 @Autowired EntityLinks entityLinks) {
        super(BandInvitesRestController.class, BandInviteResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public BandInviteResource toResource(BandInviteDTO bandInviteDTO) {
        long id = bandInviteDTO.getId();
        BandInviteResource bandInviteResource = new BandInviteResource(bandInviteDTO);
        try {
            /*
            Link invLink = entityLinks.linkForSingleResource(BandInviteDTO.class, id).withSelfRel();
            bandInviteResource.add(invLink);

            Link bandLink = entityLinks.linkForSingleResource(BandDTO.class, id).slash("/bands").withRel("band");
            bandInviteResource.add(bandLink);
            
            Link managerLink = entityLinks.linkForSingleResource(ManagerDTO.class, id).slash("/managers").withRel("manager");
            bandInviteResource.add(managerLink);

            Link memberLink = entityLinks.linkForSingleResource(MemberDTO.class, id).slash("/members").withRel("member");
            bandInviteResource.add(memberLink);
*/
        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return bandInviteResource;
    }
}
