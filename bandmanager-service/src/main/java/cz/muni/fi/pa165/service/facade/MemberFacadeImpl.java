package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.exceptions.UserServiceException;
import cz.fi.muni.pa165.facade.MemberFacade;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.BandInvite;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Objects;

@Service
@Transactional
public class MemberFacadeImpl implements MemberFacade {

    final static Logger log = LoggerFactory.getLogger(MemberFacadeImpl.class);

    @Inject
    private MemberService memberService;

    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private BandService bandService;

    @Inject
    private BandInviteService bandInviteService;

    @Inject
    private TourService tourService;

    @Override
    public MemberDTO findMemberById(Long id) {
        Member member = memberService.findMemberById(id);
        return (member == null) ? null : beanMappingService.mapTo(member, MemberDTO.class);
    }

    @Override
    public MemberDTO findMemberByEmail(String email) {
        Member member = memberService.findMemberByEmail(email);
        return (member == null) ? null : beanMappingService.mapTo(member, MemberDTO.class);
    }

    @Override
    public Long registerMember(MemberDTO m, String unencryptedPassword) {
        Member member = beanMappingService.mapTo(m, Member.class);
        Member newMember = memberService.registerMember(member, unencryptedPassword);
        return newMember.getId();
    }

    @Override
    public Collection<MemberDTO> getAllMembers() {
        return beanMappingService.mapTo(memberService.getAllMembers(), MemberDTO.class);
    }

    @Override
    public boolean authenticate(UserAuthDTO u) {
        return memberService.authenticate(memberService.findMemberByEmail(u.getEmail()), u.getPassword());
    }

    @Override
    public void changeEmail(UserChangeEmailDTO u) {
        Member member = memberService.findMemberById(u.getId());
        memberService.changeEmail(member, u.getEmail());
    }

    @Override
    public void changePassword(UserChangePasswordDTO u) {
        Member member = memberService.findMemberById(u.getId());
        memberService.changePassword(member, u.getPassword());
    }

    @Override
    public Collection<MemberDTO> findMemberByName(String name) {
        return beanMappingService.mapTo(memberService.findMemberByName(name), MemberDTO.class);
    }

    @Override
    public void acceptBandInvite(Long userId, Long bandInvId) {
        Member m = memberService.findMemberById(userId);
        BandInvite b = null;
        Band band = null;
        for (BandInvite inv : m.getBandInvites()) {
            if (Objects.equals(inv.getId(), bandInvId)) {
                b = inv;
                band = inv.getBand();
            }
        }
        memberService.acceptBandInvite(m, b);
        bandService.addMember(band, m);
        bandInviteService.delete(b);
    }

    @Override
    public void declineBandInvite(Long userId, Long bandInvId) {
        Member m = memberService.findMemberById(userId);
        BandInvite b = null;
        for (BandInvite inv : m.getBandInvites()) {
            if (Objects.equals(inv.getId(), bandInvId)) {
                b = inv;
            }
        }
        memberService.declineBandInvite(m, b);
        bandInviteService.delete(b);
    }

    @Override
    public Collection<BandInviteDTO> listAllMemberInvites(Long memberId) {
        Member m = memberService.findMemberById(memberId);
        return beanMappingService.mapTo(bandInviteService.findByMember(m), BandInviteDTO.class);
    }

    @Override
    public Collection<MemberDTO> listBandmates(Long memberId) {
        Member m = memberService.findMemberById(memberId);
        if (m.getBand() == null) {
            throw new UserServiceException("This user is not a member of any band!");
        }
        Band b = bandService.findById(m.getBand().getId());
        System.out.println("Band size:" +b.getMembers().size());
        return beanMappingService.mapTo(b.getMembers(), MemberDTO.class);
    }

    @Override
    public Collection<TourDTO> listAllActivities(Long memberId) {
        Member m = memberService.findMemberById(memberId);
        if (m.getBand() == null) {
            throw new UserServiceException("This user is not a member of any band!");
        }
        Band b = bandService.findById(m.getBand().getId());
        return beanMappingService.mapTours(tourService.findByBand(b), TourDTO.class);
    }
}

