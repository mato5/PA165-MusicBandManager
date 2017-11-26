package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.MemberDTO;
import cz.fi.muni.pa165.dto.UserAuthDTO;
import cz.fi.muni.pa165.dto.UserChangeEmailDTO;
import cz.fi.muni.pa165.dto.UserChangePasswordDTO;
import cz.fi.muni.pa165.facade.MemberFacade;
import cz.muni.fi.pa165.entity.BandInvite;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;

@Service
@Transactional
public class MemberFacadeImpl implements MemberFacade {

    final static Logger log = LoggerFactory.getLogger(MemberFacadeImpl.class);

    @Inject
    private MemberService memberService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public MemberDTO findMemberById(Long id) {
        Member member = memberService.findMemberById(id);
        return (member == null) ? null : beanMappingService.mapTo(member,MemberDTO.class);
    }

    @Override
    public MemberDTO findMemberByEmail(String email) {
        Member member = memberService.findMemberByEmail(email);
        return (member == null) ? null : beanMappingService.mapTo(member,MemberDTO.class);
    }

    @Override
    public void registerMember(MemberDTO m, String unencryptedPassword) {
        Member member = beanMappingService.mapTo(m,Member.class);
        memberService.registerMember(member,unencryptedPassword);
        m.setId(member.getId());
    }

    @Override
    public Collection<MemberDTO> getAllMembers() {
        return beanMappingService.mapTo(memberService.getAllMembers(),MemberDTO.class);
    }

    @Override
    public boolean authenticate(UserAuthDTO u) {
        return memberService.authenticate(memberService.findMemberById(u.getId()),u.getPassword());
    }

    @Override
    public void changeEmail(UserChangeEmailDTO u) {
        Member member = memberService.findMemberByEmail(u.getEmail());
        memberService.changeEmail(member,u.getEmail());
    }

    @Override
    public void changePassword(UserChangePasswordDTO u) {
        Member member = memberService.findMemberById(u.getId());
        memberService.changePassword(member,u.getPassword());
    }

    @Override
    public Collection<MemberDTO> findMemberByName(String name) {
        return beanMappingService.mapTo(memberService.findMemberByName(name),MemberDTO.class);
    }

    @Override
    public void acceptBandInvite(Long userId, Long bandInvId) {
        Member m =  memberService.findMemberById(userId);
        BandInvite b = null;
        for(BandInvite inv : m.getBandInvites()){
            if(inv.getId() == bandInvId){
                b = inv;
            }
        }
        memberService.acceptBandInvite(m,b);
    }

    @Override
    public void declineBandInvite(Long userId, Long bandInvId) {
        Member m = memberService.findMemberById(userId);
        BandInvite b = null;
        for(BandInvite inv : m.getBandInvites()){
            if(inv.getId() == bandInvId){
                b = inv;
            }
        }
        memberService.declineBandInvite(m,b);
    }
}
