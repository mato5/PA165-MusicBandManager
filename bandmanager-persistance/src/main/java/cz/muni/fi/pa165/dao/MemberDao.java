package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Member;

import java.util.List;

/**
 * DAO layer for a Member entity containing basic CRUD and filtering operations
 * @author Matej Sojak
 */
public interface MemberDao {
    /**
     * Find a member entity based on its ID
     * @param id member id
     * @return member object
     */
    public Member findById(Long id);

    /**
     * Create a new member
     * @param m member to be created
     */
    public void create(Member m);

    /**
     * Delete an existing member
     * @param m member to be deleted
     */
    public void delete(Member m);

    /**
     * Find all members
     * @return list of all members
     */
    public List<Member> findAll();

    /**
     * Find a member by its name
     * @param name member's name
     * @return list of members with a specified name
     */
    public List<Member> findByName(String name);

    /**
     * Find a member by its email
     * @param email member's email
     * @return member object
     */
    public Member findByEmail(String email);

    /**
     * Update an existing member
     * @param m member to be updated
     */
    public void update(Member m);
}
