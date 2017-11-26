package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.BandInvite;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Tour;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for a Manager entity
 * @author Matej Sojak 433294
 */
@Service
public interface ManagerService {

    /**
     * Registers a new manager
     * @param m manager to be registered
     * @param unecryptedPassword a manager's password
     */
    public void registerManager(Manager m, String unecryptedPassword);

    /**
     * List all the existing managers
     * @return list of all the managers
     */
    public List<Manager> getAllManagers();

    /**
     * Finds a manager by its ID
     * @param id a manager's ID
     * @return manager entity
     */
    public Manager findManagerById(Long id);

    /**
     * Finds a manager by its name
     * @param name a manager's name
     * @return manager entity
     */
    public Manager findManagerByName(String name);

    /**
     * Adds a new band to a manager
     * @param m band's manager
     * @param b the band to be added
     */
    public void addManagedBand(Manager m, Band b);

    /**
     * Adds a new tour for a manager to manage
     * @param m tour's manager
     * @param t the tour to be added
     */
    public void addTour(Manager m, Tour t);

    /**
     * Adds a new band invite to a manager
     * @param m manager of a band
     * @param b band invite
     */
    public void addBandInvite(Manager m, BandInvite b);

    /**
     * Terminates an ongoing contract between a manager and a band
     * @param m manager of a band
     * @param b the band
     */
    public void cancelManagedBand(Manager m, Band b);

    /**
     * Terminates a scheduled tour of a band
     * @param m manager of a band
     * @param t tour entity
     */
    public void cancelTour(Manager m, Tour t);

    /**
     * Terminates a pending band invitation
     * @param m manager of a band
     * @param b bandinvite entity
     */
    public void cancelBandInvite(Manager m, BandInvite b);

    /**
     * Authenticate a manager by its password
     * @param m manager to be authenticated
     * @param password manager's password
     * @return true if authenticated successfully
     */
    public boolean authenticate(Manager m, String password);

    /**
     * Changes an email of a manager
     * @param m the provided manager
     * @param newEmail the new email
     */
    public void changeEmail(Manager m, String newEmail);

    /**
     * Changes a password of a manager
     * @param m the provided manager
     * @param newPassword the new password
     */
    public void changePassword(Manager m, String newPassword);
}
