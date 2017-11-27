package cz.muni.fi.pa165.facade;

import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.dto.MemberDTO;
import cz.fi.muni.pa165.facade.MemberFacade;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.ManagerService;
import cz.muni.fi.pa165.service.MemberService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.facade.ManagerFacadeImpl;
import cz.muni.fi.pa165.service.facade.MemberFacadeImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Matej Sojak 433294
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ManagerFacadeTest {
    /*@Mock
    private ManagerService managerService;

    @Mock
    private BeanMappingService mappingService;


    @Autowired
    @InjectMocks
    private ManagerFacadeImpl managerFacade;

    private Manager member;
    private ManagerDTO memberDTO;
    private Long memberId;
    private String memberEmail;
    private String memberName;
    private String memberPassword;


    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareTestData() {
        memberId = 1L;
        memberEmail = "member@mail.com";
        memberName = "Jimmy Page";
        memberPassword = "halabala";

        member = new Manager();
        member.setId(memberId);
        member.setName(memberName);
        //member.setRole(Role.GUITARIST);
        member.setEmail(memberEmail);
        member.setPassword(memberPassword);

        memberDTO = new ManagerDTO();
        memberDTO.setId(memberId);
        //memberDTO.setBand(null);
        memberDTO.setName(memberName);
        //memberDTO.setRole(Role.GUITARIST);
        memberDTO.setEmail(memberEmail);


        Mockito.reset(managerService, mappingService);
    }

    @Test
    public void testFindById() throws Exception {
        when(managerFacade.findManagerById(memberId)).thenReturn(member);
        when(mappingService.mapTo(member, MemberDTO.class)).thenReturn(memberDTO);

        MemberDTO returnedDTO = memberFacade.findMemberById(memberId);

        Assert.assertEquals(returnedDTO, memberDTO);
        verify(memberService).findMemberById(memberId);
    }*/
}
