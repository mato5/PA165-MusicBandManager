package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dto.*;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.enums.Genre;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.*;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {
/*
    @Autowired
    //@InjectMocks
    private BeanMappingService beanMappingService;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    //Member1
    private Member member;
    private Long memberId;
    private String memberEmail;
    private String memberName;
    private String memberPassword;
    private Role memberRole;

    //Band
    private Band band;
    private String bandName;
    private Long bandId;
    private String bandLogoUri;
    private Genre bandGenre;

    //Manager
    private Manager manager;
    private Long managerId;
    private String managerEmail;
    private String managerPassword;
    private String managerName;

    //BandInvite
    private BandInvite bandInvite;
    private Date bandInviteCreatedAt;
    private Long bandInviteId;

    //Song
    private Song song;
    private Long songId;
    private Long songDuration;
    private String songName;

    //Album
    private Album album;
    private Long albumId;
    private String albumName;
    private String albumCoverUri;

    //Tour
    private Tour tour;
    private Long tourId;
    private Date tourDatetime;
    private String tourName;
    private String tourCityName;

    //DTOs
    private AlbumCreateDTO albumCreateDTO;
    private AlbumDTO albumDTO;
    private BandCreateDTO bandCreateDTO;
    private BandDTO bandDTO;
    private BandGengreDTO bandGengreDTO;
    private BandInviteDTO bandInviteDTO;
    private BandLogoDTO bandLogoDTO;
    private BandNameDTO bandNameDTO;
    private ManagerDTO managerDTO;
    private MemberDTO memberDTO;
    private SongCreateDTO songCreateDTO;
    private SongDTO songDTO;
    private SongToAlbumDTO songToAlbumDTO;
    private TourDTO tourDTO;
    private UserAuthDTO userAuthDTO;
    private UserChangeEmailDTO userChangeEmailDTO;
    private UserChangePasswordDTO userChangePasswordDTO;


    @BeforeMethod
    public void prepareTesData() {

        //Member1
        memberId = 1L;
        memberEmail = "jimmy@mail.com";
        memberName = "Jimmy Page";
        memberPassword = "halabala";
        memberRole = Role.GUITARIST;

        member = new Member();
        member.setId(memberId);
        member.setName(memberName);
        member.setRole(memberRole);
        member.setEmail(memberEmail);
        member.setPassword(memberPassword);
        member.setBand(null);

        //Manager
        managerId = 1L;
        managerEmail = "manager@makemoney.com";
        managerName = "John Smith";
        managerPassword = "123456";

        manager = new Manager();
        manager.setPassword(managerPassword);
        manager.setEmail(managerEmail);
        manager.setId(managerId);
        manager.setName(managerName);

        //Band
        bandId = 1L;
        bandGenre = Genre.ROCK;
        bandLogoUri = "TEST_URI";
        bandName = "Led Zeppelin";

        band = new Band();
        band.setName(bandName);
        band.setId(bandId);
        band.setManager(manager);
        band.setLogoURI(bandLogoUri);
        band.setGenre(bandGenre);


        //BandInvite
        bandInviteId = 1L;
        bandInviteCreatedAt = Date.from(Instant.EPOCH);

        bandInvite = new BandInvite();
        bandInvite.setCreatedAt(bandInviteCreatedAt);
        bandInvite.setId(bandInviteId);
        bandInvite.setInvitedMember(member);
        bandInvite.setManager(manager);
        bandInvite.setBand(band);


        //Song
        songDuration = 1000L;
        songId = 1L;
        songName = "Whole Lotta Love";

        song = new Song();
        song.setDuration(songDuration);
        song.setId(songId);
        song.setBand(band);
        song.setName(songName);

        //Album
        albumCoverUri = "Test_URI";
        albumId = 1L;
        albumName = "Led Zeppelin II";

        album = new Album();
        album.setBand(band);
        album.setName(albumName);
        album.setId(albumId);
        album.setCoverURI(albumCoverUri);
        album.addSong(song);

        //Tour
        tourCityName = "London";
        tourName = "Celebration Day";
        tourId = 1L;
        tourDatetime = Date.from(Instant.EPOCH);

        tour = new Tour();
        tour.setBand(band);
        tour.setName(tourName);
        tour.setId(tourId);
        tour.setCityName(tourCityName);
        tour.setDatetime(tourDatetime);
        tour.setManager(manager);

        //Fill all entity fields
        member.addBandInvite(bandInvite);
        member.setBand(band);
        manager.addBand(band);
        manager.addBandInvite(bandInvite);
        manager.addTour(tour);
        band.addAlbum(album);
        band.addBandInvite(bandInvite);
        band.addMember(member);
        band.addTour(tour);
        band.setManager(manager);
        bandInvite.setBand(band);
        bandInvite.setInvitedMember(member);
        bandInvite.setManager(manager);
        song.setBand(band);
        album.addSong(song);
        album.setBand(band);
        tour.setBand(band);
        tour.setManager(manager);

        //DTOs
        albumCreateDTO = new AlbumCreateDTO();
        albumDTO = new AlbumDTO();
        bandCreateDTO = new BandCreateDTO();
        bandDTO =  new BandDTO();
        bandGengreDTO = new BandGengreDTO();
        bandInviteDTO = new BandInviteDTO();
        bandLogoDTO = new BandLogoDTO();
        bandNameDTO = new BandNameDTO();
        managerDTO = new ManagerDTO();
        memberDTO = new MemberDTO();
        songCreateDTO = new SongCreateDTO();
        songDTO = new SongDTO();
        songToAlbumDTO = new SongToAlbumDTO();
        tourDTO = new TourDTO();
        userAuthDTO = new UserAuthDTO();
        userChangeEmailDTO =  new UserChangeEmailDTO();
        userChangePasswordDTO = new UserChangePasswordDTO();

        //Done
        albumCreateDTO.setName(albumName);
        albumCreateDTO.setCoverURI(albumCoverUri);
        albumCreateDTO.setBandId(bandId);

        albumDTO.setCoverURI(albumCoverUri);
        albumDTO.setName(albumName);
        albumDTO.setId(albumId);

        //Done
        bandCreateDTO.setGenre(bandGenre);
        bandCreateDTO.setLogoURI(bandLogoUri);
        bandCreateDTO.setManagerId(managerId);
        bandCreateDTO.setName(bandName);

        //Done
        bandDTO.setGenre(bandGenre);
        bandDTO.setId(bandId);
        bandDTO.setLogoURI(bandLogoUri);
        bandDTO.setName(bandName);

        //Done
        bandGengreDTO.setGenre(bandGenre);
        bandGengreDTO.setId(bandId);

        bandInviteDTO.setId(bandInviteId);

        //Done
        bandLogoDTO.setId(bandId);
        bandLogoDTO.setLogoURI(bandLogoUri);

        //Done
        bandNameDTO.setId(bandId);
        bandNameDTO.setName(bandName);

        managerDTO.setId(managerId);
        managerDTO.setEmail(managerEmail);
        managerDTO.setName(managerName);
        managerDTO.setPassword(managerPassword);

        memberDTO.setRole(memberRole);
        memberDTO.setEmail(memberEmail);
        memberDTO.setId(memberId);
        memberDTO.setName(memberName);
        memberDTO.setPassword(memberPassword);

        //Done
        songCreateDTO.setBandId(bandId);
        songCreateDTO.setDuration(songDuration);
        songCreateDTO.setName(songName);

        songDTO.setDuration(songDuration);
        songDTO.setId(songId);
        songDTO.setName(songName);

        //Done
        songToAlbumDTO.setAlbumId(albumId);
        songToAlbumDTO.setSongId(songId);

        tourDTO.setId(tourId);

        //Done
        userAuthDTO.setId(managerId);
        userAuthDTO.setPassword(managerPassword);

        //Done
        userChangeEmailDTO.setEmail(managerEmail);
        userChangeEmailDTO.setId(managerId);

        //Done
        userChangePasswordDTO.setId(managerId);
        userChangePasswordDTO.setPassword(managerPassword);

        albumDTO.setBand(bandDTO);
        albumDTO.setSongs(Collections.singleton(songDTO));
        bandInviteDTO.setBand(bandDTO);
        bandInviteDTO.setManager(managerDTO);
        bandInviteDTO.setMember(memberDTO);
        managerDTO.setBandInvites(Collections.singleton(bandInviteDTO));
        managerDTO.setBands(Collections.singleton(bandDTO));
        managerDTO.setTours(Collections.singleton(tourDTO));
        memberDTO.setBand(bandDTO);
        memberDTO.setBandInvites(Collections.singleton(bandInviteDTO));
        songDTO.setBand(bandDTO);
        tourDTO.setBand(bandDTO);
        tourDTO.setManager(managerDTO);

        Mockito.reset(beanMappingService);
    }

    @Test
    public void AlbumCreateDTOTest(){
        //Test

    }*/
}
