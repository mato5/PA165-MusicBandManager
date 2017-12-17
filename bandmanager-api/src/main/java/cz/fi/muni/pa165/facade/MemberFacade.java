package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.*;

import java.util.Collection;

/**
 * @author Matej Sojak 433294
 */
public interface MemberFacade {

    MemberDTO findMemberById(Long id);

    MemberDTO findMemberByEmail(String email);

    Long registerMember(MemberDTO m, String unencryptedPassword);

    Collection<MemberDTO> getAllMembers();

    boolean authenticate(UserAuthDTO u);

    void changeEmail(UserChangeEmailDTO u);

    void changePassword(UserChangePasswordDTO u);

    Collection<MemberDTO> findMemberByName(String name);

    void acceptBandInvite(Long userId, Long bandInvId);

    void declineBandInvite(Long userId, Long bandInvId);

    Collection<BandInviteDTO> listAllMemberInvites(Long memberId);

    Collection<MemberDTO> listBandmates(Long memberId);

    Collection<TourDTO> listAllActivities(Long memberId);
}
