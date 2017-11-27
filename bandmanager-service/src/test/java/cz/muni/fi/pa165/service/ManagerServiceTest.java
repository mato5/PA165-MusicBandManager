package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ManagerDao;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.constraints.AssertTrue;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.when;

/**
 * NOTE: For the general user test implementations eg. register, authenticate, change email etc. please see the
 * MemberServiceTest. Their implementation is the same, no need to test them twice.
 * @author Matej Sojak 433294
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ManagerServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private ManagerDao managerDao;

    @Autowired
    @InjectMocks
    private ManagerService managerService;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }


    @BeforeMethod
    public void prepareTestMembers(){

        Manager m1 = new Manager();
        //unecryptedPassword = "123456"
        m1.setPassword("1000:1ae6f43ce0ad97d056817a53bb84aa40383c7eafe8963558:f70a5405c5fe5cf688832c6c058611ca7b6fce3fc57e8c95");
        m1.setEmail("manager1@mail.com");
        m1.setId(1L);

        Manager m2 = new Manager();
        //unencryptedPassword = "654321"
        m2.setPassword("1000:4a77e3e7f817be893e4e99d5dace198c30fa21b472c991b5:6c8824e2cd2edb3cce44e5fd1a2fc1d7078e2ba448d3d84a");
        m2.setEmail("manager2@mail.com");
        m2.setId(2L);


        when(managerDao.findAll()).thenReturn(Arrays.asList(m1,m2));
        when(managerDao.findById(Long.valueOf(org.mockito.AdditionalMatchers.gt(2L)))).thenReturn(null);
        when(managerDao.findById(Long.valueOf(Matchers.eq(1L)))).thenReturn(m1);
        when(managerDao.findById(Long.valueOf(Matchers.eq(2L)))).thenReturn(m2);



    }

    @Test
    public void addManagedBandTest(){
        Band b = new Band();
        b.setId(999L);
        b.setName("Led Zeppelin");
        Manager m = managerDao.findById(1L);
        managerService.addManagedBand(m,b);
        Assert.assertTrue(m.getBands().contains(b));
    }

    @Test
    public void cancelManagedBandTest(){
        Band b = new Band();
        b.setId(999L);
        b.setName("Led Zeppelin");
        Manager m = managerDao.findById(1L);
        m.addBand(b);
        managerService.cancelManagedBand(m,b);
        Assert.assertTrue(m.getBands().size() == 0);
    }

    @Test
    public void addBandInviteTest(){
        Band b = new Band();
        b.setId(999L);
        b.setName("Led Zeppelin");
        Member m = new Member();
        m.setId(1L);
        m.setName("Jimmy Page");
        m.setEmail("zofo@mail.com");
        m.setPassword("test");
        m.setBand(b);
        m.setRole(Role.GUITARIST);
        Manager man = managerDao.findById(1L);
        BandInvite bi = new BandInvite();
        bi.setId(1L);
        bi.setBand(b);
        bi.setManager(man);
        bi.setInvitedMember(m);
        bi.setCreatedAt(Date.from(Instant.EPOCH));
        managerService.addBandInvite(man,bi);
        Assert.assertTrue(man.getBandInvites().contains(bi));
    }

    @Test
    public void cancelBandInviteTest(){
        Band b = new Band();
        b.setId(999L);
        b.setName("Led Zeppelin");
        Member m = new Member();
        m.setId(1L);
        m.setName("Jimmy Page");
        m.setEmail("zofo@mail.com");
        m.setPassword("test");
        m.setBand(b);
        m.setRole(Role.GUITARIST);
        Manager man = managerDao.findById(1L);
        BandInvite bi = new BandInvite();
        bi.setId(1L);
        bi.setBand(b);
        bi.setManager(man);
        bi.setInvitedMember(m);
        bi.setCreatedAt(Date.from(Instant.EPOCH));
        man.addBandInvite(bi);
        managerService.cancelBandInvite(man,bi);
        Assert.assertTrue(man.getBandInvites().size() == 0);
    }

    @Test
    public void addTourTest(){
        Band b = new Band();
        b.setId(999L);
        b.setName("Led Zeppelin");
        Manager man = managerDao.findById(1L);
        Tour t = new Tour();
        t.setId(1L);
        t.setDatetime(Date.from(Instant.EPOCH));
        t.setCityName("London");
        t.setManager(man);
        t.setBand(b);
        t.setName("Celebration Day");
        managerService.addTour(man,t);
        Assert.assertTrue(man.getTours().contains(t));
    }

    @Test
    public void cancelTourTest(){
        Band b = new Band();
        b.setId(999L);
        b.setName("Led Zeppelin");
        Manager man = managerDao.findById(1L);
        Tour t = new Tour();
        t.setId(1L);
        t.setDatetime(Date.from(Instant.EPOCH));
        t.setCityName("London");
        t.setManager(man);
        t.setBand(b);
        t.setName("Celebration Day");
        man.addTour(t);
        managerService.cancelTour(man,t);
        Assert.assertTrue(man.getTours().size() == 0);
    }



}
