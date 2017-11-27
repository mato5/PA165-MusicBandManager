package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alexander Kromka
 */

@Service
public interface BandInviteService {

    public BandInvite create(BandInvite bandInvite);

    public List<BandInvite> findAll();

    public BandInvite findById(Long id);

    public List<BandInvite> findByMember(Member member);

    public List<BandInvite> findByManager(Manager manager);

    public List<BandInvite> findByBand(Band band);

    public BandInvite setMember(BandInvite bandInvite, Member member);

    public BandInvite setManager(BandInvite bandInvite, Manager manager);

    public BandInvite setBand(BandInvite bandInvite, Band band);

    public void delete(BandInvite bandInvite);

}
