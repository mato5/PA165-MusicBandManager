package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.exceptions.UserServiceException;
import cz.muni.fi.pa165.dao.MemberDao;
import cz.muni.fi.pa165.entity.BandInvite;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.utils.Validator;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Matej Sojak 433294
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Inject
    MemberDao memberDao;

    @Override
    public void registerMember(Member m, String unencryptedPassword) {
        if (unencryptedPassword == null || unencryptedPassword.length() < 5) {
            throw new UserServiceException("The provided password is too short");
        }
        m.setPassword(Validator.createHash(unencryptedPassword));
        memberDao.create(m);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberDao.findAll();
    }

    @Override
    public boolean authenticate(Member m, String password) {
        return Validator.validatePassword(password, m.getPassword());
    }

    @Override
    public Member findMemberById(Long id) {
        return memberDao.findById(id);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberDao.findByEmail(email);
    }

    @Override
    public List<Member> findMemberByName(String name) {
        return memberDao.findByName(name);
    }

    @Override
    public void changeEmail(Member m, String newEmail) {
        if (newEmail == null || !Validator.validateEmail(newEmail)) {
            throw new UserServiceException("The provided email is invalid!");
        }
        if (memberDao.findById(m.getId()) == null) {
            throw new UserServiceException("This action cannot be performed on a non-existent member.");
        }
        m.setEmail(newEmail);
        memberDao.update(m);
    }

    @Override
    public void changePassword(Member m, String newPassword) {
        if (newPassword == null || newPassword.length() < 5) {
            throw new UserServiceException("The provided password is too short");
        }
        if (memberDao.findById(m.getId()) == null) {
            throw new UserServiceException("This action cannot be performed on a non-existent member.");
        }
        m.setPassword(Validator.createHash(newPassword));
        memberDao.update(m);
    }

    @Override
    public void acceptBandInvite(Member m, BandInvite b) {
        if (m == null) {
            throw new UserServiceException("This action cannot be performed by a non-existent member.");
        }
        if (b == null) {
            throw new UserServiceException("This action cannot be performed on a non-existent band invitation.");
        }
        m.setBand(b.getBand());
        m.removeBandInvite(b);
        memberDao.update(m);
    }

    @Override
    public void declineBandInvite(Member m, BandInvite b) {
        if (m == null) {
            throw new UserServiceException("This action cannot be performed by a non-existent member.");
        }
        if (b == null) {
            throw new UserServiceException("This action cannot be performed on a non-existent band invitation.");
        }
        m.removeBandInvite(b);
        memberDao.update(m);
    }


}
