package cz.muni.fi.pa165.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.facade.ManagerFacade;
import cz.fi.muni.pa165.facade.MemberFacade;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.enums.Genre;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.service.*;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.facade.ManagerFacadeImpl;
import cz.muni.fi.pa165.service.facade.MemberFacadeImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Matej Sojak 433294
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ManagerFacadeTest extends AbstractTestNGSpringContextTests {
    @Mock
    private MemberService memberService;

    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private BandInviteService bandInviteService;

    @Mock
    private BandService bandService;

    @Mock
    private TourService tourService;

    @Mock
    private ManagerService managerService;

    @Mock
    private SongService songService;

    @Mock
    private AlbumService albumService;

    @Autowired
    @InjectMocks
    private ManagerFacade managerFacade;


    //Member1
    private Member member;
    private MemberDTO memberDTO;
    private Long memberId;
    private String memberEmail;
    private String memberName;
    private String memberPassword;
    private Role memberRole;
    private UserAuthDTO userAuthDTO;
    private UserChangeEmailDTO userChangeEmailDTO;
    private UserChangePasswordDTO userChangePasswordDTO;

    //Member2
    private Member member2;
    private MemberDTO memberDTO2;
    private Long memberId2;
    private String memberEmail2;
    private String memberName2;
    private String memberPassword2;
    private Role memberRole2;

    //Band
    private Band band;
    private String bandName;
    private Long bandId;
    private String bandLogoUri;
    private Genre bandGenre;
    private BandCreateDTO bandCreateDTO;
    private BandGengreDTO bandGengreDTO;
    private BandLogoDTO bandLogoDTO;
    private BandNameDTO bandNameDTO;

    //Manager
    private Manager manager;
    private Long managerId;
    private String managerEmail;
    private String managerPassword;
    private String managerName;
    private ManagerDTO managerDTO;

    //BandInvite
    private BandInvite bandInvite;
    private Long bandInviteId;
    private Date bandInviteCreated;
    private BandInviteDTO bandInviteDTO;

    //Song
    private Song song;
    private Long songId;
    private Long songDuration;
    private String songName;
    private SongCreateDTO songCreateDTO;

    //Album
    private Album album;
    private Long albumId;
    private String albumName;
    private String albumCoverUri;
    private AlbumCreateDTO albumCreateDTO;
    private SongToAlbumDTO songToAlbumDTO;

    //Tour
    private Tour tour;
    private TourDTO tourDTO;

    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareTestData() {

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

        memberDTO = new MemberDTO();
        memberDTO.setId(memberId);
        memberDTO.setBand(null);
        memberDTO.setName(memberName);
        memberDTO.setRole(memberRole);
        memberDTO.setEmail(memberEmail);
        memberDTO.setPassword(memberPassword);

        userAuthDTO = new UserAuthDTO();
        userAuthDTO.setId(memberId);
        userAuthDTO.setPassword(memberPassword);

        //Member2
        memberId2 = 2L;
        memberEmail2 = "robert@mail.com";
        memberName2 = "Robert Plant";
        memberPassword2 = "balahala";
        memberRole2 = Role.VOCALS;

        member2 = new Member();
        member2.setId(memberId2);
        member2.setName(memberName2);
        member2.setRole(memberRole2);
        member2.setEmail(memberEmail2);
        member2.setPassword(memberPassword2);
        member2.setBand(null);

        memberDTO2 = new MemberDTO();
        memberDTO2.setId(memberId2);
        memberDTO2.setBand(null);
        memberDTO2.setName(memberName2);
        memberDTO2.setRole(memberRole2);
        memberDTO2.setEmail(memberEmail2);
        memberDTO2.setPassword(memberPassword2);

        userChangeEmailDTO = new UserChangeEmailDTO();
        userChangeEmailDTO.setId(memberId);
        userChangeEmailDTO.setEmail(memberEmail2);

        userChangePasswordDTO = new UserChangePasswordDTO();
        userChangePasswordDTO.setId(memberId);
        userChangePasswordDTO.setPassword(memberPassword2);

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

        managerDTO = new ManagerDTO();
        managerDTO.setEmail(managerEmail);
        managerDTO.setId(managerId);
        managerDTO.setName(managerName);
        managerDTO.setPassword(managerPassword);

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

        bandCreateDTO = new BandCreateDTO();
        bandCreateDTO.setManagerId(managerId);
        bandCreateDTO.setGenre(bandGenre);
        bandCreateDTO.setLogoURI(bandLogoUri);
        bandCreateDTO.setName(bandName);

        bandGengreDTO = new BandGengreDTO();
        bandGengreDTO.setGenre(bandGenre);
        bandGengreDTO.setId(bandId);

        bandLogoDTO = new BandLogoDTO();
        bandLogoDTO.setId(bandId);
        bandLogoDTO.setLogoURI(bandLogoUri);

        bandNameDTO = new BandNameDTO();
        bandNameDTO.setId(bandId);
        bandNameDTO.setName(bandName);

        //BandInvite
        bandInviteId = 1L;
        bandInviteCreated = Date.from(Instant.EPOCH);

        bandInvite = new BandInvite();
        bandInvite.setCreatedAt(bandInviteCreated);
        bandInvite.setId(bandInviteId);
        bandInvite.setInvitedMember(member);
        bandInvite.setManager(manager);
        bandInvite.setBand(band);
        member.addBandInvite(bandInvite);

        bandInviteDTO = new BandInviteDTO();
        bandInviteDTO.setMember(memberDTO);
        bandInviteDTO.setId(1L);

        //Song
        songDuration = 1000L;
        songId = 1L;
        songName = "Whole Lotta Love";

        song = new Song();
        song.setDuration(songDuration);
        song.setId(songId);
        song.setBand(band);
        song.setName(songName);

        songCreateDTO = new SongCreateDTO();
        songCreateDTO.setBandId(bandId);
        songCreateDTO.setDuration(songDuration);
        songCreateDTO.setName(songName);

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

        albumCreateDTO = new AlbumCreateDTO();
        albumCreateDTO.setBandId(bandId);
        albumCreateDTO.setCoverURI(albumCoverUri);
        albumCreateDTO.setName(albumName);

        songToAlbumDTO = new SongToAlbumDTO();
        songToAlbumDTO.setSongId(songId);
        songToAlbumDTO.setAlbumId(albumId);

        //Tour
        tour = new Tour();
        tour.setBand(band);
        tour.setName("Celebration Day");
        tour.setId(1L);
        tour.setCityName("London");
        tour.setDatetime(Date.from(Instant.now()));
        tour.setManager(manager);

        tourDTO = new TourDTO();
        tourDTO.setId(1L);

        Mockito.reset(managerService, beanMappingService);
    }

    @Test
    public void findByIdTest() throws Exception {
        when(managerService.findManagerById(1L)).thenReturn(manager);
        when(beanMappingService.mapTo(manager, ManagerDTO.class)).thenReturn(managerDTO);
        Long id = 1L;
        ManagerDTO returnedDTO = managerFacade.findManagerById(id);

        Assert.assertEquals(returnedDTO, managerDTO);
        verify(managerService).findManagerById(id);
    }

    @Test
    public void findByNameTest() throws Exception {
        when(managerService.findManagerByName(eq(managerName))).thenReturn(manager);
        when(beanMappingService.mapTo(manager, ManagerDTO.class)).thenReturn(managerDTO);
        ManagerDTO returnedDTO = managerFacade.findManagerByName(managerName);
        Assert.assertEquals(returnedDTO, managerDTO);
        verify(managerService).findManagerByName(eq(managerName));
    }

    @Test
    public void getAllManagersTest() throws Exception {
        when(managerService.getAllManagers()).thenReturn(Arrays.asList(manager));
        when(beanMappingService.mapTo(Arrays.asList(manager), ManagerDTO.class)).thenReturn(Arrays.asList(managerDTO));
        List<ManagerDTO> returnedDTOs = new ArrayList<>(managerFacade.getAllManagers());
        Assert.assertEquals(returnedDTOs, Arrays.asList(managerDTO));
        verify(managerService).getAllManagers();
    }

    @Test
    public void registerManagerTest() throws Exception {
        when(beanMappingService.mapTo(managerDTO, Manager.class)).thenReturn(manager);
        when(managerService.registerManager(eq(manager), eq(managerPassword))).thenReturn(manager);

        Long returnedId = managerFacade.registerManager(managerDTO, managerPassword);
        Assert.assertEquals(returnedId.longValue(), managerId.longValue());

        verify(managerService).registerManager(eq(manager), eq(managerPassword));
    }

    @Test
    public void authenticateTest() throws Exception {
        userAuthDTO.setId(managerId);
        userAuthDTO.setPassword(managerPassword);
        when(managerService.findManagerById(userAuthDTO.getId())).thenReturn(manager);
        managerFacade.authenticate(userAuthDTO);
        verify(managerService).authenticate(eq(manager), eq(managerPassword));
    }

    @Test
    public void changeEmailTest() throws Exception {
        userChangeEmailDTO.setId(managerId);
        when(managerService.findManagerById(userChangeEmailDTO.getId())).thenReturn(manager);
        managerFacade.changeEmail(userChangeEmailDTO);
        verify(managerService).changeEmail(eq(manager), eq(memberEmail2));
    }

    @Test
    public void changePasswordTest() throws Exception {
        userChangeEmailDTO.setId(managerId);
        when(managerService.findManagerById(userChangePasswordDTO.getId())).thenReturn(manager);
        managerFacade.changePassword(userChangePasswordDTO);
        verify(managerService).changePassword(eq(manager), eq(memberPassword2));
    }

    @Test
    public void addTourTest() throws Exception {
        when(beanMappingService.mapTo(managerDTO, Manager.class)).thenReturn(manager);
        when(beanMappingService.mapTo(tourDTO, Tour.class)).thenReturn(tour);
        managerFacade.addTour(managerDTO, tourDTO);

        verify(managerService).addTour(eq(manager), eq(tour));
        verify(tourService).create(eq(tour));
        verify(bandService).addTour(eq(band), eq(tour));
    }

    @Test
    public void cancelTourTest() throws Exception {
        when(beanMappingService.mapTo(managerDTO, Manager.class)).thenReturn(manager);
        when(beanMappingService.mapTo(tourDTO, Tour.class)).thenReturn(tour);
        managerFacade.cancelTour(managerDTO, tourDTO);

        verify(managerService).cancelTour(eq(manager), eq(tour));
        verify(tourService).delete(eq(tour));
        verify(bandService).cancelTour(eq(band), eq(tour));
    }

    @Test
    public void createBandTest() throws Exception {
        when(beanMappingService.mapTo(managerDTO, Manager.class)).thenReturn(manager);
        when(beanMappingService.mapTo(bandCreateDTO, Band.class)).thenReturn(band);

        Long returnedId = managerFacade.createBand(managerDTO, bandCreateDTO);
        Assert.assertEquals(returnedId.longValue(), bandId.longValue());

        verify(bandService).create(band);
        verify(managerService).addManagedBand(manager, band);
    }

    @Test
    public void sendBandInviteTest() throws Exception {
        when(beanMappingService.mapTo(managerDTO, Manager.class)).thenReturn(manager);
        when(beanMappingService.mapTo(bandInviteDTO, BandInvite.class)).thenReturn(bandInvite);
        managerFacade.sendBandInvite(managerDTO, bandInviteDTO);

        verify(bandInviteService).create(bandInvite);
        verify(managerService).addBandInvite(manager, bandInvite);
        verify(memberService).sendBandInvite(member, bandInvite);
    }

    @Test
    public void changeBandGenreTest() throws Exception {
        when(bandService.findById(bandId)).thenReturn(band);
        managerFacade.changeBandGenre(managerDTO, bandGengreDTO);

        verify(bandService).changeGenre(band, bandGenre);
    }

    @Test
    public void changeBandNameTest() throws Exception {
        when(bandService.findById(bandId)).thenReturn(band);
        managerFacade.changeBandName(managerDTO, bandNameDTO);

        verify(bandService).changeName(band, bandName);
    }

    @Test
    public void changeBandLogoTest() throws Exception {
        when(bandService.findById(bandId)).thenReturn(band);
        managerFacade.changeBandLogo(managerDTO, bandLogoDTO);

        verify(bandService).changeLogoUri(band, bandLogoUri);
    }


    @Test
    public void addNewSongTest() throws Exception {
        when(beanMappingService.mapTo(songCreateDTO, Song.class)).thenReturn(song);
        Long returnedId = managerFacade.addNewSong(managerDTO, songCreateDTO);

        Assert.assertEquals(returnedId, songId);
        verify(songService).create(song);
    }

    @Test
    public void addNewAlbumTest() throws Exception {
        when(beanMappingService.mapTo(albumCreateDTO, Album.class)).thenReturn(album);
        when(bandService.findById(bandId)).thenReturn(band);

        Long returnedId = managerFacade.addNewAlbum(managerDTO,albumCreateDTO);
        Assert.assertEquals(returnedId.longValue(), albumId.longValue());

        verify(albumService).create(album);
        verify(bandService).addAlbum(band,album);
    }

    @Test
    public void addSongToAlbumTest() throws Exception {
        when(songService.findById(songId)).thenReturn(song);
        when(albumService.findById(albumId)).thenReturn(album);
        managerFacade.addSongToAlbum(managerDTO,songToAlbumDTO);

        verify(albumService).addSong(album,song);
    }


}
