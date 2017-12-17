package cz.muni.fi.pa165.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.facade.MemberFacade;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.enums.Genre;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.service.*;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Matej Sojak
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MemberFacadeTest /*extends AbstractTestNGSpringContextTests*/ {

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

    @Autowired
    @InjectMocks
    private MemberFacadeImpl memberFacade;


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

        Mockito.reset(memberService, beanMappingService);
    }

    @Test
    public void findByIdTest() throws Exception {
        when(memberService.findMemberById(1L)).thenReturn(member);
        when(beanMappingService.mapTo(member, MemberDTO.class)).thenReturn(memberDTO);
        Long id = 1L;
        MemberDTO returnedDTO = memberFacade.findMemberById(id);

        Assert.assertEquals(returnedDTO, memberDTO);
        verify(memberService).findMemberById(id);
    }

    @Test
    public void findByEmailTest() throws Exception {
        when(memberService.findMemberByEmail("jimmy@mail.com")).thenReturn(member);
        when(beanMappingService.mapTo(member, MemberDTO.class)).thenReturn(memberDTO);
        String mail = "jimmy@mail.com";
        MemberDTO returnedDTO = memberFacade.findMemberByEmail(mail);

        Assert.assertEquals(returnedDTO, memberDTO);
        verify(memberService).findMemberByEmail(mail);
    }

    @Test
    public void registerMemberTest() throws Exception {
        when(beanMappingService.mapTo(memberDTO, Member.class)).thenReturn(member);
        when(memberService.registerMember(eq(member), eq("halabala"))).thenReturn(member);

        Long returnedId = memberFacade.registerMember(memberDTO, "halabala");
        Assert.assertEquals(returnedId.longValue(), memberId.longValue());

        verify(memberService).registerMember(eq(member), eq("halabala"));
    }

    @Test
    public void getAllMembersTest() throws Exception {
        when(memberService.getAllMembers()).thenReturn(Arrays.asList(member, member2));
        when(beanMappingService.mapTo(Arrays.asList(member, member2), MemberDTO.class))
                .thenReturn(Arrays.asList(memberDTO, memberDTO2));

        List<MemberDTO> returnedMemberDTOs = new ArrayList<>(memberFacade.getAllMembers());
        Assert.assertEquals(returnedMemberDTOs, Arrays.asList(memberDTO, memberDTO2));
        verify(memberService).getAllMembers();
    }

    @Test
    public void userAuthTest() throws Exception {
        when(memberService.findMemberById(userAuthDTO.getId())).thenReturn(member);
        memberFacade.authenticate(userAuthDTO);
        verify(memberService).authenticate(eq(member), eq("halabala"));
    }

    @Test
    public void changeEmailTest() throws Exception {
        when(memberService.findMemberById(userChangeEmailDTO.getId())).thenReturn(member);
        memberFacade.changeEmail(userChangeEmailDTO);
        verify(memberService).changeEmail(eq(member), eq(memberEmail2));
    }

    @Test
    public void changePasswordTest() throws Exception {
        when(memberService.findMemberById(userChangePasswordDTO.getId())).thenReturn(member);
        memberFacade.changePassword(userChangePasswordDTO);
        verify(memberService).changePassword(eq(member), eq(memberPassword2));
    }

    @Test
    public void findMemberByNameTest() throws Exception {
        when(memberService.findMemberByName(eq(memberName))).thenReturn(Arrays.asList(member));
        when(beanMappingService.mapTo(Arrays.asList(member), MemberDTO.class))
                .thenReturn(Arrays.asList(memberDTO));

        List<MemberDTO> returnedDTOs = new ArrayList<>(memberFacade.findMemberByName("Jimmy Page"));
        Assert.assertEquals(returnedDTOs, Arrays.asList(memberDTO));
        verify(memberService).findMemberByName(eq(memberName));
    }

    @Test
    public void acceptBandInviteTest() throws Exception {
        when(memberService.findMemberById(eq(memberId))).thenReturn(member);
        memberFacade.acceptBandInvite(memberId, bandInviteId);
        verify(memberService).acceptBandInvite(eq(member), eq(bandInvite));
        verify(bandService).addMember(eq(band), eq(member));
        verify(bandInviteService).delete(eq(bandInvite));
    }

    @Test
    public void declineBandInviteTest() throws Exception {
        when(memberService.findMemberById(eq(memberId))).thenReturn(member);
        memberFacade.declineBandInvite(memberId, bandInviteId);
        verify(memberService).declineBandInvite(eq(member), eq(bandInvite));
        verify(bandInviteService).delete(eq(bandInvite));
    }

    @Test
    public void listAllInvitesTest() throws Exception {
        when(memberService.findMemberById(eq(memberId))).thenReturn(member);
        when(bandInviteService.findByMember(eq(member))).thenReturn(Arrays.asList(bandInvite));
        when(beanMappingService.mapTo(Arrays.asList(bandInvite), BandInviteDTO.class))
                .thenReturn(Arrays.asList(bandInviteDTO));
        List<BandInviteDTO> returnedDTOs = new ArrayList<>(memberFacade.listAllMemberInvites(memberId));
        Assert.assertEquals(returnedDTOs, Arrays.asList(bandInviteDTO));
        verify(bandInviteService).findByMember(eq(member));
    }

    @Test
    public void listAllBandmatesTest() throws Exception {
        member.setBand(band);
        member2.setBand(band);
        band.addMember(member);
        band.addMember(member2);
        when(memberService.findMemberById(eq(memberId))).thenReturn(member);
        when(bandService.findById(eq(bandId))).thenReturn(band);
        Set<Member> mems = new HashSet<>(Arrays.asList(member, member2));
        when(beanMappingService.mapTo(mems, MemberDTO.class)).thenReturn(Arrays.asList(memberDTO, memberDTO2));
        List<MemberDTO> returnedDTOs = new ArrayList<>(memberFacade.listBandmates(memberId));
        Assert.assertEquals(returnedDTOs, Arrays.asList(memberDTO, memberDTO2));
    }

    @Test
    public void listAllActivities() throws Exception {
        member.setBand(band);
        band.addMember(member);
        when(memberService.findMemberById(eq(memberId))).thenReturn(member);
        when(bandService.findById(eq(bandId))).thenReturn(band);
        when(tourService.findByBand(band)).thenReturn(Arrays.asList(tour));
        when(beanMappingService.mapTo(Arrays.asList(tour), TourDTO.class)).thenReturn(Arrays.asList(tourDTO));
        List<TourDTO> returnedDTOs = new ArrayList<>(memberFacade.listAllActivities(memberId));
        Assert.assertEquals(returnedDTOs, Arrays.asList(tourDTO));
    }


}
