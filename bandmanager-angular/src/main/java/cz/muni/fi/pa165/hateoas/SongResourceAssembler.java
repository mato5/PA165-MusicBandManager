package cz.muni.fi.pa165.hateoas;

import cz.fi.muni.pa165.dto.SongDTO;
import cz.muni.fi.pa165.controllers.SongsRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@Component
public class SongResourceAssembler extends ResourceAssemblerSupport<SongDTO, SongResource> {

    private EntityLinks entityLinks;

    private final static Logger logger = LoggerFactory.getLogger(SongResourceAssembler.class);

    public SongResourceAssembler(@SuppressWarnings("SpringJavaAutowiringInspection")
                                 @Autowired EntityLinks entityLinks) {
        super(SongsRestController.class, SongResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public SongResource toResource(SongDTO songDTO) {
        SongResource songResource = new SongResource(songDTO);
        return songResource;
    }
}

