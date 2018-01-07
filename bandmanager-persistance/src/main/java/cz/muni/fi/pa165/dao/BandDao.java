package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Member;

import java.util.List;

/**
 * @author Iurii xkuznetc Kuznetcov
 * <p>
 * Data Access Object interface for Band entity with basic CRUD operations and some filtering.
 */
public interface BandDao {
    /**
     * Gets Band object by it's id
     *
     * @param id Long - id of Band object
     * @return Band band object
     */
    public Band findById(Long id);

    /**
     * Persist new Band object. Provides some basic fields values validation.
     *
     * @param band Band object to persist
     */
    public void create(Band band);

    /**
     * Delete existing Band object.
     *
     * @param band Band object to delete.
     */
    public void delete(Band band);

    /**
     * Merge changes in Band object and persist modified object. Provides some basic fields values validation.
     *
     * @param band
     */
    public void update(Band band);

    /**
     * Get all persistent Band objects.
     *
     * @return List<Band> - list of stored Band objects.
     */
    public List<Band> findAll();

    /**
     * Get Band object by it's name.
     *
     * @param name String - band's name
     * @return Band object if band with given name exists or null if not.
     */
    public Band findByName(String name);

    /**
     * Get all Band objects by their Manager.
     *
     * @param manager Manager object which owns a band.
     * @return List<Band> - list of stored Band objects with given Manager
     * or null if such an objects doesn't exist.
     */
    public List<Band> findByManager(Manager manager);

    /**
     * Get all Band objects by their Member.
     *
     * @param member Member object which owns a band.
     * @return List<Band> - list of stored Band objects with given Member
     * or null if such an objects doesn't exist.
     */
    public List<Band> findByMember(Member member);

}
