package cz.muni.fi.pa165.hateoas;

import cz.fi.muni.pa165.dto.AlbumDTO;
import cz.muni.fi.pa165.controllers.AlbumsRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class AlbumResourceAssembler extends ResourceAssemblerSupport<AlbumDTO, AlbumResource> {

    private EntityLinks entityLinks;

    private final static Logger logger = LoggerFactory.getLogger(AlbumResourceAssembler.class);

    public AlbumResourceAssembler(@SuppressWarnings("SpringJavaAutowiringInspection")
                                 @Autowired EntityLinks entityLinks) {
        super(AlbumsRestController.class, AlbumResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public AlbumResource toResource(AlbumDTO albumDTO) {
        AlbumResource albumResource = new AlbumResource(albumDTO);
        return albumResource;
    }
}
