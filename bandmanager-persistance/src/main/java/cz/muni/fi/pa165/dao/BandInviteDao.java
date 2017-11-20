package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.BandInvite;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Member;

import java.util.List;

/**
 * @author Iurii xkuznetc Kuznetcov
 * <p>
 * Data Access Object interface for BandInvite entity with basic CRUD operations and some filtering.
 */

public interface BandInviteDao {
    /**
     * Gets BandInvite object by it's id
     *
     * @param id Long - id of BandInvite object
     * @return BandInvite invite object
     */
    public BandInvite findById(long id);

    /**
     * Persist new BandInvite object. Provides some basic fields values validation.
     *
     * @param bandInvite BandInvite object to persist
     */
    public void create(BandInvite bandInvite);


    /**
     * Delete existing BandInvite object.
     *
     * @param bandInvite BandInvite object to delete.
     */
    public void delete(BandInvite bandInvite);


    /**
     * Merge changes in BandInvite object and persist modified object. Provides some basic fields values validation.
     *
     * @param bandInvite BandInvite object to update.
     */
    public void update(BandInvite bandInvite);

    /**
     * Get all persistent BandInvite objects.
     *
     * @return List<BandInvite> - list of stored BandInvite objects.
     */
    public List<BandInvite> findAll();

    /**
     * Get all BandInvite objects by their Manager.
     *
     * @param manager Manager object which owns a BandInvite.
     * @return List<BandInvite> - list of stored BandInvite objects with given Manager
     * or null if such an objects doesn't exist.
     */
    public List<BandInvite> findByManager(Manager manager);

    /**
     * Get all BandInvite objects by their Band.
     *
     * @param band Band object which owns a BandInvite.
     * @return List<BandInvite> - list of stored BandInvite objects with given Band
     * or null if such an objects doesn't exist.
     */
    public List<BandInvite> findByBand(Band band);

    /**
     * Get all BandInvite objects by their Member.
     *
     * @param member Member object which owns a BandInvite.
     * @return List<BandInvite> - list of stored BandInvite objects with given Memberl
     * or null if such an objects doesn't exist.
     */
    public List<BandInvite> findByMember(Member member);
}
