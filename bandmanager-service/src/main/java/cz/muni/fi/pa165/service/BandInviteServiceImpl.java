package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.BandInviteDao;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.BandInvite;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.service.facade.MemberFacadeImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BandInviteServiceImpl implements BandInviteService {
final static Logger log = LoggerFactory.getLogger(BandInviteServiceImpl.class);
    @Inject
    private BandInviteDao bandInviteDao;

    @Override
    public BandInvite create(BandInvite bandInvite) {
        this.bandInviteDao.create(bandInvite);
        return bandInvite;
    }

    @Override
    public List<BandInvite> findAll() {
        return this.bandInviteDao.findAll();
    }

    @Override
    public BandInvite findById(Long id) {
        return this.bandInviteDao.findById(id);
    }

    @Override
    public List<BandInvite> findByMember(Member member) {
        return this.bandInviteDao.findByMember(member);
    }

    @Override
    public List<BandInvite> findByManager(Manager manager) {
        return this.bandInviteDao.findByManager(manager);
    }

    @Override
    public List<BandInvite> findByBand(Band band) {
        return this.bandInviteDao.findByBand(band);
    }

    @Override
    public BandInvite setMember(BandInvite bandInvite, Member member) {
        bandInvite.setInvitedMember(member);
        this.bandInviteDao.update(bandInvite);
        return bandInvite;
    }

    @Override
    public BandInvite setManager(BandInvite bandInvite, Manager manager) {
        bandInvite.setManager(manager);
        this.bandInviteDao.update(bandInvite);
        return bandInvite;
    }

    @Override
    public BandInvite setBand(BandInvite bandInvite, Band band) {
        bandInvite.setBand(band);
        this.bandInviteDao.update(bandInvite);
        return bandInvite;
    }

    @Override
    public void delete(BandInvite bandInvite) { this.bandInviteDao.delete(bandInvite); }

}
