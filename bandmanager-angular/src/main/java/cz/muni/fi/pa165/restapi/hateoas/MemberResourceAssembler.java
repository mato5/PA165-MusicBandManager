package cz.muni.fi.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.MemberDTO;
import cz.muni.fi.pa165.restapi.controllers.MembersRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class MemberResourceAssembler extends ResourceAssemblerSupport<MemberDTO, MemberResource> {

    private EntityLinks entityLinks;

    private final static Logger log = LoggerFactory.getLogger(MemberResourceAssembler.class);

    public MemberResourceAssembler(@SuppressWarnings("SpringJavaAutowiringInspection")
                                   @Autowired EntityLinks entityLinks) {
        super(MembersRestController.class, MemberResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public MemberResource toResource(MemberDTO memberDTO) {
        long id = memberDTO.getId();
        MemberResource memberResource = new MemberResource(memberDTO);
        try {
            Link memLink = entityLinks.linkForSingleResource(MemberDTO.class, id).withSelfRel();
            memberResource.add(memLink);

            if (memberDTO.getBand() != null) {
                Link bandLink = entityLinks.linkForSingleResource(BandDTO.class, memberDTO.getBand().getId()).withRel("band");
                memberResource.add(bandLink);
            }

        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return memberResource;
    }
}
