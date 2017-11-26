package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.*;

import java.util.Collection;

/**
 * @author Matej Sojak 433294
 */
public interface ManagerFacade {

    void registerManager(ManagerDTO m, String unencryptedPassword);

    Collection<ManagerDTO> getAllManagers();

    ManagerDTO findManagerById(Long id);

    ManagerDTO findManagerByName(String name);

    boolean authenticate(UserAuthDTO u);

    void changeEmail(UserChangeEmailDTO u);

    void changePassword(UserChangePasswordDTO u);

    void addManagedBand(ManagerDTO m, BandDTO b);

    void cancelManagedBand(ManagerDTO m, BandDTO b);

    void addTour(ManagerDTO m, TourDTO t);

    void cancelTour(ManagerDTO m, TourDTO t);

    void addBandInvite(ManagerDTO m, BandInviteDTO b);

    void cancelBandInvite(ManagerDTO m, BandInviteDTO b);
}
