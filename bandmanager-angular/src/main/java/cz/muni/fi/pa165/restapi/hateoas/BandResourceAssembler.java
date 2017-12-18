package cz.muni.fi.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.BandDTO;
import cz.muni.fi.pa165.restapi.controllers.BandsRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class BandResourceAssembler extends ResourceAssemblerSupport<BandDTO, BandResource> {
    private EntityLinks entityLinks;

    private final static Logger log = LoggerFactory.getLogger(ManagerResourceAssembler.class);

    public BandResourceAssembler(@SuppressWarnings("SpringJavaAutowiringInspection")
                                    @Autowired EntityLinks entityLinks) {
        super(BandsRestController.class, BandResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public BandResource toResource(BandDTO bandDTO) {
        long id = bandDTO.getId();
        BandResource bandResource = new BandResource(bandDTO);
        try {
            Link selfLink = entityLinks.linkForSingleResource(BandDTO.class, id).withSelfRel();
            bandResource.add(selfLink);

        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return bandResource;
    }
}
