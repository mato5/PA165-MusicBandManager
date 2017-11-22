package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MemberDao;
import cz.muni.fi.pa165.entity.Member;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public interface MemberService {

    public void registerMember(Member m, String unencryptedPassword);

    public List<Member> getAllMembers();

    public boolean authenticate(Member m, String password);

    public Member findMemberById(Long id);

    public Member findMemberByEmail(String email);

    public List<Member> findMemberByName(String name);

    public void changeEmail(Member m, String newEmail);

    public void changePassword(Member m, String newPassword);
}
