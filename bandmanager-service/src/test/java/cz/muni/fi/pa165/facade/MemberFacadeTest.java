package cz.muni.fi.pa165.facade;

import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.dto.MemberDTO;
import cz.fi.muni.pa165.facade.MemberFacade;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.MemberService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
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
 * @author Matej Sojak
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MemberFacadeTest {

    @Mock
    private MemberService memberService;

    @Mock
    private BeanMappingService mappingService;


    @Autowired
    @InjectMocks
    private MemberFacadeImpl memberFacade;

    private Member member;
    private MemberDTO memberDTO;
    private Long memberId;
    private String memberEmail;
    private String memberName;
    private String memberPassword;
    private Role memberRole;

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

        member = new Member();
        member.setId(memberId);
        member.setName(memberName);
        //member.setRole(Role.GUITARIST);
        member.setEmail(memberEmail);
        member.setBand(null);

        memberDTO = new MemberDTO();
        memberDTO.setId(memberId);
        memberDTO.setBand(null);
        memberDTO.setName(memberName);
        //memberDTO.setRole(Role.GUITARIST);
        memberDTO.setEmail(memberEmail);


        Mockito.reset(memberService, mappingService);
    }

    @Test
    public void testFindById() throws Exception {
        when(memberService.findMemberById(memberId)).thenReturn(member);
        when(mappingService.mapTo(member, MemberDTO.class)).thenReturn(memberDTO);

        MemberDTO returnedDTO = memberFacade.findMemberById(memberId);

        Assert.assertEquals(returnedDTO, memberDTO);
        verify(memberService).findMemberById(memberId);
    }


}
