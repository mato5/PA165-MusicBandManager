package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.enums.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Miroslav Kadlec
 */
@Service
public interface BandService {
    /**
     * Returns Band with given Id 
     * @param   id key to find Band
     * @return  Band found in the DB 
     */
    public Band findById(Long id);
    
    /**
     * Returns Band with given name 
     * @param   name to search band
     * @return  Band found in the DB 
     */
    public Band findByName(String name);
            
    /**
     * Creates a new Band in the DB
     * @param   band Band to be created
     * @return  created Band
     */
    public Band create(Band band);

    /**
     * Deletes Band from the DB
     * @param   band Band to be deleted
     */
    public void delete(Band band);

    /**
     * Lists all Bands in the DB
     * @return  List of bands found
     */
    public List<Band> findAll();
    
    /**
     * Lists all Bands in the DB managed by given Manager
     * @return  List of bands found
     */
    public List<Band> findByManager(Manager manager);

    /**
     * Lists all Bands in the DB by member
     * @return  List of bands found
     */
    public List<Band> findByMember(Member member);
    
    /**
     * Lists all Bands in the DB playing the given genre
     * @return  List of bands found
     */
    public List<Band> findByGenre(Genre genre);

    /**
     * Adds Member to given Band
     * @param   band Band to add member to
     * @param   member Member to be added
     * @return  updated Band 
     */
    public Band addMember(Band band, Member member);
    
    /**
     * Adds Album to given Band
     * @param   band Band to add member to
     * @param   album Album to be added
     * @return  updated Band 
     */
    public Band addAlbum(Band band, Album album);
    /**
     * Removes Member from given Band
     * @param   band Band to remove member from
     * @param   member Member to be removed
     * @return  updated Band 
     */
    public Band removeMember(Band band, Member member);
    
    /**
     * Removes Album from given Band
     * @param   band Band to remove member from
     * @param   album album to be removed
     * @return  updated Band 
     */
    public Band removeAlbum(Band band, Album album);
    
    /**
     * Changes Manager of given Band
     * @param   band Band to be updated
     * @param   manager new Manager
     * @return  updated Band 
     */
    public Band changeManager(Band band, Manager manager);
    
    /**
     * Changes logo uri of given Band
     * @param   band Band to be updated
     * @param   logoUri new uri
     * @return  updated Band 
     */
    public Band changeLogoUri(Band band, String logoUri);

    void disbandBand(Band band);

    /**
     * Changes name of given Band
     * @param   band Band to be updated
     * @param   name new band name
     * @return  updated Band 
     */
    public Band changeName(Band band, String name);
    /**
     * Changes Genre of given Band
     * @param   band Band to be updated
     * @param   genre new Genre
     * @return  updated Band 
     */
    public Band changeGenre(Band band, Genre genre);

    Band removeInvitation(Band band, BandInvite bandInvite);

    Band addInvitation(Band band, BandInvite bandInvite);

    Band addTour(Band band, Tour tour);

    Band cancelTour(Band band, Tour tour);
}
