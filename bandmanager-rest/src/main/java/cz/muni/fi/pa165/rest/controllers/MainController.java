package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.rest.ApiURIPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {
    final static Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Map<String, String> getResources() {

        Map<String, String> resourcesMap = new HashMap<>();

        resourcesMap.put("songs_uri", ApiURIPaths.ROOT_URI_SONGS);
        resourcesMap.put("albums_uri", ApiURIPaths.ROOT_URI_ALBUMS);
        resourcesMap.put("tours_uri", ApiURIPaths.ROOT_URI_TOURS);
        
        return Collections.unmodifiableMap(resourcesMap);

    }
}
