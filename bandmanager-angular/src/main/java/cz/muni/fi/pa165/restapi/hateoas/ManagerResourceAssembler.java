package cz.muni.fi.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.BandInviteDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.dto.TourDTO;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.restapi.controllers.ManagersRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ManagerResourceAssembler extends ResourceAssemblerSupport<ManagerDTO, ManagerResource> {
    private EntityLinks entityLinks;

    private final static Logger log = LoggerFactory.getLogger(ManagerResourceAssembler.class);

    public ManagerResourceAssembler(@SuppressWarnings("SpringJavaAutowiringInspection")
                                    @Autowired EntityLinks entityLinks) {
        super(ManagersRestController.class, ManagerResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public ManagerResource toResource(ManagerDTO managerDTO) {
        long id = managerDTO.getId();
        ManagerResource managerResource = new ManagerResource(managerDTO);
        try {
            Link manLink = entityLinks.linkForSingleResource(ManagerDTO.class, id).withSelfRel();
            managerResource.add(manLink);

            if (managerDTO.getBands() != null && managerDTO.getBands().size() > 0) {
                for (BandDTO b : managerDTO.getBands()) {
                    Link bandLink = entityLinks.linkForSingleResource(BandDTO.class, b.getId()).withRel("bands");
                    managerResource.add(bandLink);
                }
            }

            if (managerDTO.getTours() != null && managerDTO.getTours().size() > 0) {
                for (TourDTO t : managerDTO.getTours()) {
                    Link tourLink = entityLinks.linkForSingleResource(TourDTO.class, t.getId()).withRel("tours");
                    managerResource.add(tourLink);
                }
            }

            if (managerDTO.getBandInvites() != null && managerDTO.getBandInvites().size() > 0) {
                for (BandInviteDTO b : managerDTO.getBandInvites()) {
                    Link invLink = entityLinks.linkForSingleResource(BandInviteDTO.class, b.getId()).withRel("invites");
                    managerResource.add(invLink);
                }
            }

        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return managerResource;
    }
}
