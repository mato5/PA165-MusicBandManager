package cz.muni.fi.pa165.hateoas;

import cz.fi.muni.pa165.dto.TourDTO;
import cz.muni.fi.pa165.controllers.ToursRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class TourResourceAssembler extends ResourceAssemblerSupport<TourDTO, TourResource> {

    private EntityLinks entityLinks;

    private final static Logger log = LoggerFactory.getLogger(TourResourceAssembler.class);

    public TourResourceAssembler(@SuppressWarnings("SpringJavaAutowiringInspection")
                                     @Autowired EntityLinks entityLinks) {
        super(ToursRestController.class, TourResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public TourResource toResource(TourDTO tourDTO) {
        long id = tourDTO.getId();
        TourResource tourResource = new TourResource(tourDTO);
        try {
            Link tourLink = entityLinks.linkForSingleResource(TourDTO.class, id).withSelfRel();
            tourResource.add(tourLink);

            Link bandLink = entityLinks.linkForSingleResource(TourDTO.class, id).slash("/bands").withRel("band");
            tourResource.add(bandLink);

            Link managerLink = entityLinks.linkForSingleResource(TourDTO.class, id).slash("/managers").withRel("manager");
            tourResource.add(managerLink);

        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return tourResource;
    }
}
