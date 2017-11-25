package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MemberDao;
import cz.muni.fi.pa165.entity.BandInvite;
import cz.muni.fi.pa165.entity.Member;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service layer for a Member entity
 * @author Matej Sojak 433294
 */
@Service
public interface MemberService {

    /**
     * Registers a new member
     * @param m member to be registered
     * @param unencryptedPassword a member's password
     */
    public void registerMember(Member m, String unencryptedPassword);

    /**
     * List all the existing members
     * @return list all the members
     */
    public List<Member> getAllMembers();

    /**
     * Authenticate a member by its password
     * @param m member to be authenticated
     * @param password member's password
     * @return true if authenticated successfully
     */
    public boolean authenticate(Member m, String password);

    /**
     * Find a member by its ID
     * @param id id of a member
     * @return member entity
     */
    public Member findMemberById(Long id);

    /**
     * Find a member by its email
     * @param email email of a member
     * @return member entity
     */
    public Member findMemberByEmail(String email);

    /**
     * Find a member by its name
     * @param name name of a member
     * @return
     */
    public List<Member> findMemberByName(String name);

    /**
     * Changes an email of a member
     * @param m the provided member
     * @param newEmail the new email
     */
    public void changeEmail(Member m, String newEmail);

    /**
     * Changes a password of a member
     * @param m the provided member
     * @param newPassword the new password
     */
    public void changePassword(Member m, String newPassword);

    public void acceptBandInvite(Member m, BandInvite b);

    public void declineBandInvite(Member m, BandInvite b);
}
