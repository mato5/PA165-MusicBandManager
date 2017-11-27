package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.exceptions.UserServiceException;
import cz.muni.fi.pa165.dao.ManagerDao;
import cz.muni.fi.pa165.dao.MemberDao;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.service.MemberService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Matej Sojak 43329
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MemberServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MemberDao memberDao;

    @Autowired
    @InjectMocks
    private MemberService memberService;

    @BeforeClass
    public void setup() throws ServiceException
    {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareTestMembers(){

        Member m1 = new Member();
        //unecryptedPassword = "123456"
        m1.setPassword("1000:1ae6f43ce0ad97d056817a53bb84aa40383c7eafe8963558:f70a5405c5fe5cf688832c6c058611ca7b6fce3fc57e8c95");
        m1.setEmail("member1@mail.com");
        m1.setId(1L);

        Member m2 = new Member();
        //unencryptedPassword = "654321"
        m2.setPassword("1000:4a77e3e7f817be893e4e99d5dace198c30fa21b472c991b5:6c8824e2cd2edb3cce44e5fd1a2fc1d7078e2ba448d3d84a");
        m2.setEmail("member2@mail.com");
        m2.setId(2L);

        when(memberDao.findAll()).thenReturn(Arrays.asList(m1,m2));
        when(memberDao.findById(Long.valueOf(org.mockito.AdditionalMatchers.gt(2L)))).thenReturn(null);
        when(memberDao.findById(Long.valueOf(Matchers.eq(1L)))).thenReturn(m1);
        when(memberDao.findById(Long.valueOf(Matchers.eq(2L)))).thenReturn(m2);

    }

    @Test
    public void authenticateValidTest(){
        Member m = memberDao.findById(1L);
        boolean result = memberService.authenticate(m,"123456");
        Assert.assertTrue(result);
    }

    @Test
    public void authenticateInvalidTest(){
        Member m = memberDao.findById(1L);
        boolean result = memberService.authenticate(m,"123455");
        Assert.assertFalse(result);
    }

    @Test
    public void changeEmailValidTest(){
        String newMail = "user@mail.muni.cz";
        Member m = memberDao.findById(1L);
        memberService.changeEmail(m,newMail);
        Assert.assertEquals(newMail,m.getEmail());
    }

    @Test(expectedExceptions = UserServiceException.class)
    public void changeEmailInvalidTest(){
        String newMail = "Hello World";
        Member m = memberDao.findById(1L);
        memberService.changeEmail(m,newMail);
    }

    @Test
    public void changePasswordValidTest(){
        Member m1 = memberDao.findById(1L);
        memberService.changePassword(m1,"halabala");
        boolean authResult = memberService.authenticate(m1,"halabala");
        Assert.assertTrue(authResult);
    }

    @Test(expectedExceptions = UserServiceException.class)
    public void changePasswordInvalidTest(){
        Member m1 = memberDao.findById(1L);
        memberService.changePassword(m1,"X");
    }

    @Test
    public void registerMemberTest(){
        Member m = memberDao.findById(1L);
        memberService.registerMember(m,"654321");
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberDao).create(memberCaptor.capture());
        Member registeredMember = memberCaptor.getValue();

        Assert.assertEquals(m, registeredMember);

    }




}
