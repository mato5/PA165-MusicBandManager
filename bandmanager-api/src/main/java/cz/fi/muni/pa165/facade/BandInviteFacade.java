package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.*;

import java.util.Collection;

/**
 * @author Alexander Kromka
 */
public interface BandInviteFacade {
    BandInviteDTO create(BandInviteDTO bandInvite);

    Collection<BandInviteDTO> getAllBandInvites();

    BandInviteDTO findById(Long id);

    Collection<BandInviteDTO> findByMember(MemberDTO member);

    Collection<BandInviteDTO> findByManager(ManagerDTO manager);

    Collection<BandInviteDTO> findByBand(BandDTO band);

    BandInviteDTO setMember(BandInviteDTO bandInvite, MemberDTO member);

    BandInviteDTO setManager(BandInviteDTO bandInvite, ManagerDTO manager);

    BandInviteDTO setband(BandInviteDTO bandInvite, BandDTO band);

    void delete(BandInviteDTO bandInvite);
}
