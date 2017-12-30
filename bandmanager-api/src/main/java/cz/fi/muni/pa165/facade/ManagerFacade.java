package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.*;

import java.util.Collection;

/**
 * @author Matej Sojak 433294
 */
public interface ManagerFacade {

    Long registerManager(ManagerDTO m, String unencryptedPassword);

    Collection<ManagerDTO> getAllManagers();

    ManagerDTO findManagerById(Long id);

    ManagerDTO findManagerByName(String name);

    ManagerDTO findManagerByEmail(String email);

    boolean authenticate(UserAuthDTO u);

    void changeEmail(UserChangeEmailDTO u);

    void changePassword(UserChangePasswordDTO u);

    /**
     * DEPRECATED
     *
     * @param m
     * @param b
     */
    void addManagedBand(ManagerDTO m, BandDTO b);

    /**
     * DEPRECATED
     *
     * @param m
     * @param b
     */
    void cancelManagedBand(ManagerDTO m, BandDTO b);

    void addTour(ManagerDTO m, TourDTO t);

    void cancelTour(ManagerDTO m, TourDTO t);

    /**
     * DEPRECATED
     *
     * @param m
     * @param b
     */
    void addBandInvite(ManagerDTO m, BandInviteDTO b);

    /**
     * DEPRECATED
     *
     * @param m
     * @param b
     */
    void cancelBandInvite(ManagerDTO m, BandInviteDTO b);

    //Overhaul
    Long createBand(ManagerDTO m, BandCreateDTO b);

    Long sendBandInvite(ManagerDTO m, BandInviteDTO b);

    void changeBandGenre(ManagerDTO m, BandGengreDTO b);

    void changeBandName(ManagerDTO m, BandNameDTO b);

    void changeBandLogo(ManagerDTO m, BandLogoDTO b);

    Long addNewSong(ManagerDTO m, SongCreateDTO s);

    Long addNewAlbum(ManagerDTO m, AlbumCreateDTO a);

    void addSongToAlbum(ManagerDTO m, SongToAlbumDTO s);

    Long createTour(TourCreateDTO t);

}
