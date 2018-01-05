package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.facade.ManagerFacade;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * @author Matej Sojak 433294
 */
@Service
@Transactional
public class ManagerFacadeImpl implements ManagerFacade {

    final static Logger log = LoggerFactory.getLogger(MemberFacadeImpl.class);

    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private ManagerService managerService;

    @Inject
    private BandService bandService;

    @Inject
    private BandInviteService bandInviteService;

    @Inject
    private MemberService memberService;

    @Inject
    private SongService songService;

    @Inject
    private AlbumService albumService;

    @Inject
    private TourService tourService;


    @Override
    public Long registerManager(ManagerDTO m, String unencryptedPassword) {
        Manager manager = beanMappingService.mapTo(m, Manager.class);
        Manager newManager = managerService.registerManager(manager, unencryptedPassword);
        return newManager.getId();
    }

    @Override
    public Collection<ManagerDTO> getAllManagers() {
        return beanMappingService.mapTo(managerService.getAllManagers(), ManagerDTO.class);
    }

    @Override
    public ManagerDTO findManagerById(Long id) {
        Manager manager = managerService.findManagerById(id);
        return (manager == null) ? null : beanMappingService.mapTo(manager, ManagerDTO.class);
    }

    @Override
    public ManagerDTO findManagerByName(String name) {
        Manager manager = managerService.findManagerByName(name);
        return (manager == null) ? null : beanMappingService.mapTo(manager, ManagerDTO.class);
    }

    @Override
    public ManagerDTO findManagerByEmail(String email) {
        Manager manager = managerService.findManagerByEmail(email);
        return (manager == null) ? null : beanMappingService.mapTo(manager, ManagerDTO.class);
    }

    @Override
    public boolean authenticate(UserAuthDTO u) {
        List<Manager> list = managerService.getAllManagers();
        Manager lookingFor = null;
        for (Manager m : list) {
            if (m.getEmail().equals(u.getEmail())) {
                lookingFor = m;
                u.setId(m.getId());
            }
        }
        if (lookingFor == null) {
            return false;
        }
        return managerService.authenticate(lookingFor, u.getPassword());
    }

    @Override
    public void changeEmail(UserChangeEmailDTO u) {
        Manager manager = managerService.findManagerById(u.getId());
        managerService.changeEmail(manager, u.getEmail());
    }

    @Override
    public void changePassword(UserChangePasswordDTO u) {
        Manager manager = managerService.findManagerById(u.getId());
        managerService.changePassword(manager, u.getPassword());
    }

    @Override
    public void addManagedBand(ManagerDTO m, BandDTO b) {
        Manager manager = managerService.findManagerById(m.getId());
        Band band = bandService.findById(b.getId());
        managerService.addManagedBand(manager, band);
    }


    @Override
    public void cancelManagedBand(ManagerDTO m, BandDTO b) {
        Manager manager = managerService.findManagerById(m.getId());
        Band band = bandService.findById(b.getId());
        managerService.cancelManagedBand(manager, band);
    }

    @Override
    public void addTour(ManagerDTO m, TourDTO t) {
        Manager manager = managerService.findManagerById(m.getId());
        Tour tour = tourService.findById(t.getId());
        Band band = tour.getBand();
        tourService.create(tour);
        managerService.addTour(manager, tour);
        bandService.addTour(band, tour);
    }

    @Override
    public void cancelTour(ManagerDTO m, TourDTO t) {
        Manager manager = managerService.findManagerById(m.getId());
        Tour tour = tourService.findById(t.getId());
        Band band = tour.getBand();
        managerService.cancelTour(manager, tour);
        bandService.cancelTour(band, tour);
        tourService.delete(tour);
    }

    @Override
    public void addBandInvite(ManagerDTO m, BandInviteDTO b) {
        Manager manager = managerService.findManagerById(m.getId());
        BandInvite invite = bandInviteService.findById(b.getId());
        managerService.addBandInvite(manager, invite);
    }

    @Override
    public void cancelBandInvite(ManagerDTO m, BandInviteDTO b) {
        Manager manager = managerService.findManagerById(m.getId());
        BandInvite invite = bandInviteService.findById(b.getId());
        managerService.cancelBandInvite(manager, invite);
    }


    //Overhaul

    @Override
    public Long createBand(ManagerDTO m, BandCreateDTO b) {
        Manager manager = managerService.findManagerById(m.getId());
        Band newBand = new Band();
        newBand.setManager(manager);
        newBand.setGenre(b.getGenre());
        newBand.setLogoURI(b.getLogoURI());
        newBand.setName(b.getName());
        newBand = bandService.create(newBand);
        managerService.addManagedBand(manager, newBand);
        return newBand.getId();
    }

    @Override
    public Long sendBandInvite(ManagerDTO m, BandInviteDTO b) {
        Manager manager = managerService.findManagerById(m.getId());
        BandInvite invite = beanMappingService.mapBandInvite(b, BandInvite.class);
        Member member = memberService.findMemberById(b.getMember().getId());
        invite = bandInviteService.create(invite);
        managerService.addBandInvite(manager, invite);
        memberService.sendBandInvite(member, invite);
        return invite.getId();
    }

    @Override
    public void changeBandGenre(ManagerDTO m, BandGengreDTO b) {
        Band band = bandService.findById(b.getId());
        bandService.changeGenre(band, b.getGenre());
    }

    @Override
    public void changeBandName(ManagerDTO m, BandNameDTO b) {
        Band band = bandService.findById(b.getId());
        bandService.changeName(band, b.getName());
    }

    @Override
    public void changeBandLogo(ManagerDTO m, BandLogoDTO b) {
        Band band = bandService.findById(b.getId());
        bandService.changeLogoUri(band, b.getLogoURI());
    }

    //NOT SURE ABOUT THIS
    @Override
    public Long addNewSong(ManagerDTO m, SongCreateDTO s) {
        Band band = bandService.findById(s.getBandId());
        Song newSong = new Song();
        newSong.setBand(band);
        newSong.setDuration(s.getDuration());
        newSong.setName(s.getName());
        newSong = songService.create(newSong);
        return newSong.getId();
    }

    //NOT SURE ABOUT THIS
    @Override
    public Long addNewAlbum(ManagerDTO m, AlbumCreateDTO a) {
        Album newAlbum = new Album();
        Band band = bandService.findById(a.getBandId());
        newAlbum.setBand(band);
        newAlbum.setCoverURI(a.getCoverURI());
        newAlbum.setName(a.getName());
        newAlbum = albumService.create(newAlbum);
        bandService.addAlbum(band, newAlbum);
        return newAlbum.getId();
    }

    @Override
    public void addSongToAlbum(ManagerDTO m, SongToAlbumDTO s) {
        Song song = songService.findById(s.getSongId());
        Album album = albumService.findById(s.getAlbumId());
        albumService.addSong(album, song);
    }

    @Override
    public void removeSongFromAlbum(ManagerDTO m, SongToAlbumDTO s) {
        Song song = songService.findById(s.getSongId());
        Album album = albumService.findById(s.getAlbumId());
        albumService.deleteSong(album, song);
    }

    @Override
    public Long createTour(TourCreateDTO t) {
        Manager manager = managerService.findManagerById(t.getManagerId());
        Band band = bandService.findById(t.getBandId());
        Tour tour = new Tour();
        tour.setManager(manager);
        tour.setBand(band);
        tour.setCityName(t.getCityName());
        tour.setDatetime(t.getDatetime());
        tour.setName(t.getName());
        tour = tourService.create(tour);
        managerService.addTour(manager, tour);
        bandService.addTour(band, tour);
        return tour.getId();
    }
}
