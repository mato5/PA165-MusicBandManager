package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Member;

import java.util.List;

/**
 * @author Matej Sojak
 */
public interface MemberDao {
    public Member findById(Long id);

    public void create(Member m);

    public void delete(Member m);

    public List<Member> findAll();

    public List<Member> findByName(String name);

    public Member findByEmail(String email);

    public void update(Member m);
}
