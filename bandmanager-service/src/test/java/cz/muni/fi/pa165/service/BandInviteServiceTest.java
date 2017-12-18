package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.*;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;


/**
 * @author Alexander Kromka
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BandInviteServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private MemberDao memberDao;

    @Mock
    private ManagerDao managerDao;

    @Mock
    private BandDao bandDao;

    @Autowired
    @InjectMocks
    private BandInviteService bandInviteService;

    @BeforeMethod
    public void prepareTestMembers() {

        Member member1 = new Member();
        //unecryptedPassword = "123456"
        member1.setPassword("1000:1ae6f43ce0ad97d056817a53bb84aa40383c7eafe8963558:f70a5405c5fe5cf688832c6c058611ca7b6fce3fc57e8c95");
        member1.setEmail("member1@mail.com");
        member1.setId(1L);

        Member member2 = new Member();
        //unencryptedPassword = "654321"
        member2.setPassword("1000:4a77e3e7f817be893e4e99d5dace198c30fa21b472c991b5:6c8824e2cd2edb3cce44e5fd1a2fc1d7078e2ba448d3d84a");
        member2.setEmail("member2@mail.com");
        member2.setId(2L);

        when(memberDao.findAll()).thenReturn(Arrays.asList(member1, member2));
        when(memberDao.findById(Long.valueOf(org.mockito.AdditionalMatchers.gt(2L)))).thenReturn(null);
        when(memberDao.findById(Long.valueOf(Matchers.eq(1L)))).thenReturn(member1);
        when(memberDao.findById(Long.valueOf(Matchers.eq(2L)))).thenReturn(member2);

    }

    @BeforeMethod
    public void prepareTestManagers(){

        Manager manager1 = new Manager();
        //unecryptedPassword = "123456"
        manager1.setPassword("1000:1ae6f43ce0ad97d056817a53bb84aa40383c7eafe8963558:f70a5405c5fe5cf688832c6c058611ca7b6fce3fc57e8c95");
        manager1.setEmail("manager1@mail.com");
        manager1.setId(1L);

        Manager manager2 = new Manager();
        //unencryptedPassword = "654321"
        manager2.setPassword("1000:4a77e3e7f817be893e4e99d5dace198c30fa21b472c991b5:6c8824e2cd2edb3cce44e5fd1a2fc1d7078e2ba448d3d84a");
        manager2.setEmail("manager2@mail.com");
        manager2.setId(2L);


        when(managerDao.findAll()).thenReturn(Arrays.asList(manager1,manager2));
        when(managerDao.findById(Long.valueOf(org.mockito.AdditionalMatchers.gt(2L)))).thenReturn(null);
        when(managerDao.findById(Long.valueOf(Matchers.eq(1L)))).thenReturn(manager1);
        when(managerDao.findById(Long.valueOf(Matchers.eq(2L)))).thenReturn(manager2);

    }

    @BeforeMethod
    public void prepareTestBands() {

        Band band1 = new Band();
        band1.setName("Existing test band 1");
        band1.setId(1L);

        Band band2 = new Band();
        band2.setName("Existing test band 2");
        band2.setId(2L);

        when(this.bandDao.findAll()).thenReturn(Arrays.asList(band1, band2));

        when(this.bandDao.findById(Long.valueOf(org.mockito.AdditionalMatchers.gt(2L)))).thenReturn(null);
        when(this.bandDao.findById(Long.valueOf(Matchers.eq(1L)))).thenReturn(band1);
        when(this.bandDao.findById(Long.valueOf(Matchers.eq(2L)))).thenReturn(band2);
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setMemberTest() {
        BandInvite bandInvite1 = new BandInvite();
        BandInvite bandInvite2 = new BandInvite();
        bandInviteService.setMember(bandInvite1, this.memberDao.findById(1l));
        bandInviteService.setMember(bandInvite2, this.memberDao.findById(1l));

        List<BandInvite> bandInvites = bandInviteService.findByMember(this.memberDao.findById(1l));

        Assert.assertTrue(bandInvites.contains(bandInvite1));
        Assert.assertTrue(bandInvites.contains(bandInvite2));
    }

    @Test
    public void setManagerTest() {
        BandInvite bandInvite1 = new BandInvite();
        BandInvite bandInvite2 = new BandInvite();
        bandInviteService.setManager(bandInvite1, this.managerDao.findById(1l));
        bandInviteService.setManager(bandInvite2, this.managerDao.findById(1l));

        List<BandInvite> bandInvites = bandInviteService.findByManager(this.managerDao.findById(1l));

        Assert.assertTrue(bandInvites.contains(bandInvite1));
        Assert.assertTrue(bandInvites.contains(bandInvite2));
    }

    @Test
    public void setBandTest() {
        BandInvite bandInvite1 = new BandInvite();
        BandInvite bandInvite2 = new BandInvite();
        bandInviteService.setBand(bandInvite1, this.bandDao.findById(1l));
        bandInviteService.setBand(bandInvite2, this.bandDao.findById(1l));

        List<BandInvite> bandInvites = bandInviteService.findByBand(this.bandDao.findById(1l));

        Assert.assertTrue(bandInvites.contains(bandInvite1));
        Assert.assertTrue(bandInvites.contains(bandInvite2));
    }

}
