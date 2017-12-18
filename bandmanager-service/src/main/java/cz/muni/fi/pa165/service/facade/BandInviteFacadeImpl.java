package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.BandInviteDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.dto.MemberDTO;
import cz.fi.muni.pa165.facade.BandInviteFacade;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.BandInvite;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.service.*;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class BandInviteFacadeImpl implements BandInviteFacade {

    final static Logger log = LoggerFactory.getLogger(BandInviteFacadeImpl.class);

    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private BandInviteService bandInviteService;

    @Inject
    private MemberService memberService;

    @Inject
    private ManagerService managerService;

    @Inject
    private BandService bandService;

    @Override
    public BandInviteDTO create(BandInviteDTO b) {
        BandInvite bandInvite = beanMappingService.mapBandInvite(b, BandInvite.class);
        bandInvite.setCreatedAt(Date.from(Instant.now()));
        bandInviteService.create(bandInvite);
        return (bandInvite == null) ? null : beanMappingService.mapTo(bandInvite,BandInviteDTO.class);
    }
    
    @Override
    public Collection<BandInviteDTO> getAllBandInvites() { 
              Collection<BandInvite> testCol = bandInviteService.findAll();



      for (BandInvite b : testCol) {



          log.info("############### before mapping ##################: BANDINVITE: {}, member {}", b.getId(), b.getInvitedMember().getName());



      }



      Collection<BandInviteDTO> testColDTO =  beanMappingService.mapBandInviteDTOs(testCol, BandInviteDTO.class);



      for (BandInviteDTO b : testColDTO) {



          log.info("################# after mapping ################: BANDINVITE: {}, member {}", b.getId(), b.getMember());



      }return testColDTO;//beanMappingService.mapBandInviteDTOs(bandInviteService.findAll(), BandInviteDTO.class);
    }

    @Override
    public BandInviteDTO findById(Long id) {
        BandInvite bandInvite = bandInviteService.findById(id);
        return (bandInvite == null) ? null : beanMappingService.mapTo(bandInvite,BandInviteDTO.class);
    }

    @Override
    public Collection<BandInviteDTO> findByMember(MemberDTO member) {
        return beanMappingService.mapTo(bandInviteService.findByMember(memberService.findMemberById(member.getId())), BandInviteDTO.class);
    }

    @Override
    public Collection<BandInviteDTO> findByManager(ManagerDTO manager) {
        return beanMappingService.mapTo(bandInviteService.findByManager(managerService.findManagerById(manager.getId())), BandInviteDTO.class);
    }

    @Override
    public Collection<BandInviteDTO> findByBand(BandDTO band) {
        return beanMappingService.mapTo(bandInviteService.findByBand(bandService.findById(band.getId())), BandInviteDTO.class);
    }

    @Override
    public BandInviteDTO setMember(BandInviteDTO b, MemberDTO m) {
        BandInvite bandInvite = bandInviteService.findById(b.getId());
        Member member = memberService.findMemberById(m.getId());
        bandInviteService.setMember(bandInvite, member);
        return beanMappingService.mapTo(bandInvite,BandInviteDTO.class);
    }

    @Override
    public BandInviteDTO setManager(BandInviteDTO b, ManagerDTO m) {
        BandInvite bandInvite = bandInviteService.findById(m.getId());
        Manager manager = managerService.findManagerById(m.getId());
        bandInviteService.setManager(bandInvite, manager);
        return beanMappingService.mapTo(bandInvite,BandInviteDTO.class);
    }

    @Override
    public BandInviteDTO setband(BandInviteDTO bandInviteDTO, BandDTO bandDTO) {
        BandInvite bandInvite = bandInviteService.findById(bandInviteDTO.getId());
        Band band = bandService.findById(bandDTO.getId());
        bandInviteService.setBand(bandInvite, band);
        return beanMappingService.mapTo(bandInvite,BandInviteDTO.class);
    }

    @Override
    public void delete(BandInviteDTO bandInvite) {
        bandService.delete(bandService.findById(bandInvite.getId()));
    }
}
