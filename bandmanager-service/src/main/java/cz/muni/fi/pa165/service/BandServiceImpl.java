package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dto.SongDTO;
import cz.fi.muni.pa165.exceptions.BandManagerServiceException;
import cz.muni.fi.pa165.dao.*;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.enums.Genre;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

/**
 * @author Miroslav Kadlec
 */
@Service
public class BandServiceImpl implements BandService {

    @Inject
    private BandDao bandDao;

    @Inject
    private MemberDao memberDao;

    @Inject
    private ManagerDao managerDao;

    @Inject
    private AlbumDao albumDao;

    @Inject
    private SongDao songDao;

    @Inject
    private BandInviteDao bandInviteDao;

    @Inject
    private TourDao tourDao;

    @Override
    public Band findById(Long id) {
        return this.bandDao.findById(id);
    }

    @Override
    public Band findByName(String name) {
        return this.bandDao.findByName(name);
    }

    @Override
    public Band create(Band band) {
        this.bandDao.create(band);
        return band;
    }

    @Override
    public void delete(Band band) {
        this.bandDao.delete(band);
    }

    @Override
    public List<Band> findAll() {
        return this.bandDao.findAll();
    }

    @Override
    public List<Band> findByManager(Manager manager) {
        return this.bandDao.findByManager(manager);
    }

    @Override
    public List<Band> findByGenre(Genre genre) {
        List<Band> resultList = new ArrayList<>();
        List<Band> allBands = this.bandDao.findAll();
        for (Band b : allBands) {
            if (b.getGenre() == genre) {
                resultList.add(b);
            }
        }
        return resultList;
    }

    @Override
    public Band addMember(Band band, Member member) {
        if (member == null) {
            throw new BandManagerServiceException(
                    "Cannot add a null membmer to band "
                            + band.getName() + "(id = " + band.getId() + " ) , not updated.");
        }
        if (band.getMembers().contains(member)) {
            throw new BandManagerServiceException("Given member is already a part of the band "
                    + band.getName() + "(id = " + band.getId() + " ) , not updated.");
        }
        band.addMember(member);
        this.bandDao.update(band);
        return band;
    }

    @Override
    public Band addAlbum(Band band, Album album) {
        if (album == null) {
            throw new BandManagerServiceException(
                    "Cannot add a null album to band "
                            + band.getName() + "(id = " + band.getId() + " ) , not updated.");
        }
        /*if (band.getAlbums().contains(album)) {
            throw new BandManagerServiceException("Given album is already associated with given band "
                    + band.getName() + "(id = " + band.getId() + " ) , not updated.");
        }*/
        band.addAlbum(album);
        this.bandDao.update(band);
        return band;
    }


    @Override
    public Band removeMember(Band band, Member member) {
        if (member == null) {
            throw new BandManagerServiceException(
                    "Cannot remove a null member from band "
                            + band.getName() + "(id = " + band.getId() + " ) , not updated.");
        }
        if (!band.getMembers().contains(member)) {
            throw new BandManagerServiceException("Given member was not a part of the band "
                    + band.getName() + "(id = " + band.getId() + " ) , not updated.");
        }
        band.removeMember(member);
        this.bandDao.update(band);
        return band;
    }

    @Override
    public Band removeAlbum(Band band, Album album) {
        if (album == null) {
            throw new BandManagerServiceException(
                    "Cannot remove a null album from band "
                            + band.getName() + "(id = " + band.getId() + " ) , not updated.");
        }
        if (!band.getAlbums().contains(album)) {
            throw new BandManagerServiceException("Given album was not a part of the band "
                    + band.getName() + "(id = " + band.getId() + " ) , not updated.");
        }
        band.removeAlbum(album);
        this.bandDao.update(band);
        return band;
    }

    @Override
    public Band changeManager(Band band, Manager manager) {
        if (manager == null) {
            throw new BandManagerServiceException(
                    "Manager can't be null. Band "
                            + band.getName() + "(id = " + band.getId() + " ) , not updated.");
        }
        band.setManager(manager);
        this.bandDao.update(band);
        return band;
    }

    @Override
    public void disbandBand(Band band) {
        Set<Member> members = band.getMembers();
        Manager manager = band.getManager();
        Set<Album> albums = band.getAlbums();
        List<Song> songs = songDao.findByBand(band);
        List<BandInvite> invites = bandInviteDao.findByBand(band);
        List<Tour> tours = tourDao.findByBand(band);
        for (Member item : members){
            item.setBand(null);
            memberDao.update(item);
        }
        manager.removeBand(band);
        managerDao.update(manager);
        for(Album item: albums){
            albumDao.delete(item);
        }
        for(Song item : songs){
            songDao.delete(item);
        }
        for(BandInvite item: invites){
            bandInviteDao.delete(item);
        }
        for(Tour item : tours){
            tourDao.delete(item);
        }
        band.setAlbums(null);
        band.setBandInvites(null);
        band.setMembers(null);
        band.setTours(null);
        Long id = band.getId();
        bandDao.update(band);
        band = bandDao.findById(id);
        bandDao.delete(band);
    }

    @Override
    public Band changeName(Band band, String name) {
        if (name == null) {
            throw new BandManagerServiceException("New name should not null. Band "
                    + band.getName() + "(id = " + band.getId() + " ) , not updated.");
        }
        if (name.isEmpty()) {
            throw new BandManagerServiceException("New name should not be an empty string. Band "
                    + band.getName() + "(id = " + band.getId() + " ) , not updated.");
        }
        band.setName(name);
        this.bandDao.update(band);
        return band;
    }

    @Override
    public Band changeLogoUri(Band band, String logoUri) {
        band.setName(logoUri);
        this.bandDao.update(band);
        return band;
    }


    @Override
    public Band changeGenre(Band band, Genre genre) {
        band.setGenre(genre);
        this.bandDao.update(band);
        return band;
    }

    @Override
    public Band removeInvitation(Band band, BandInvite bandInvite) {
        band.removeBandInvite(bandInvite);
        this.bandDao.update(band);
        return band;
    }

    @Override
    public Band addInvitation(Band band, BandInvite bandInvite) {
        band.addBandInvite(bandInvite);
        this.bandDao.update(band);
        return band;
    }

    @Override
    public Band addTour(Band band, Tour tour) {
        band.addTour(tour);
        bandDao.update(band);
        return band;
    }

    @Override
    public Band cancelTour(Band band, Tour tour) {
        band.removeTour(tour);
        bandDao.update(band);
        return band;
    }
}
