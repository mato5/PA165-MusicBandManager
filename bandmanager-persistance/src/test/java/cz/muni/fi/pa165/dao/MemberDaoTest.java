package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistanceTestingContext;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.enums.Role;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author Iurii xkuznetc Kuznetcov
 */
@ContextConfiguration(classes = PersistanceTestingContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MemberDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private MemberDao memberDao;

    private Member member1;
    private Member member2;

    @BeforeMethod
    public void prepareMembers() {

        member1 = new Member();
        member1.setName("Test #1 user name");
        member1.setEmail("test_member_1@gmail.com");
        member1.setPassword("verYseCurePa$$word1HashString1");
        member1.setRole(Role.DRUMMER);

        memberDao.create(member1);

        member2 = new Member();
        member2.setName("Test #2 user name");
        member2.setEmail("test_member_2@gmail.com");
        member2.setPassword("verYseCurePa$$word1HashString2");
        member2.setRole(Role.BASSIST);

        memberDao.create(member2);
    }

    @Test
    public void nonExistentMemberReturnsNull() {
        Assert.assertNull(memberDao.findById(12345677890l));
    }


    @Test
    public void testExistentMemberFindById() {
        Member existingMember1 = memberDao.findById(member1.getId());

        Assert.assertEquals(existingMember1.getRole(), Role.DRUMMER);
        Assert.assertEquals(existingMember1.getBand(), null);
        Assert.assertEquals(existingMember1.getEmail(), "test_member_1@gmail.com");
        Assert.assertEquals(existingMember1.getName(), "Test #1 user name");
        Assert.assertEquals(existingMember1.getPassword(), "verYseCurePa$$word1HashString1");
    }

    @Test
    public void testExistentMemberFindByEmail() {
        Member existingMember1 = memberDao.findByEmail(member1.getEmail());

        Assert.assertEquals(existingMember1.getRole(), Role.DRUMMER);
        Assert.assertEquals(existingMember1.getBand(), null);
        Assert.assertEquals(existingMember1.getEmail(), "test_member_1@gmail.com");
        Assert.assertEquals(existingMember1.getName(), "Test #1 user name");
        Assert.assertEquals(existingMember1.getPassword(), "verYseCurePa$$word1HashString1");
    }

    @Test
    public void testExistentMemberFindByName() {
        List<Member> existingMembersByName = memberDao.findByName(member1.getName());

        Assert.assertEquals(existingMembersByName.size(), 1);
    }

    @Test
    public void testAllExistentMembers() {
        List<Member> existingMembers = memberDao.findAll();

        Assert.assertEquals(existingMembers.size(), 2);
    }

    @Test
    public void testDeleteExistentMember() {
        Member existingMember = memberDao.findById(member1.getId());
        memberDao.delete(existingMember);

        Member nonExistentMember = memberDao.findById(member1.getId());

        Assert.assertEquals(nonExistentMember, null);
    }

    @Test
    public void testUpdateExistentMember() {
        Member existingMember = memberDao.findById(member1.getId());
        existingMember.setName("another name for test #1 user");
        memberDao.update(existingMember);

        Member updatedExistentMember = memberDao.findById(member1.getId());

        Assert.assertEquals(updatedExistentMember.getName(), "another name for test #1 user");
    }

    /*
    * Uses top-most exception : https://stackoverflow.com/questions/17300956/expecting-database-exceptions-with-junit-spring-and-hibernate
     */
    @Test(expectedExceptions = JpaSystemException.class)
    public void testCreateDuplicateMember() {

        Member duplicateMember = new Member();
        duplicateMember.setName("Test #1 user name");
        duplicateMember.setEmail("test_member_1@gmail.com");
        duplicateMember.setPassword("verYseCurePa$$word1HashString1");
        duplicateMember.setRole(Role.DRUMMER);
        memberDao.create(duplicateMember);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void testInvalidMemberObjectCreate() {

        Member member = null;
        memberDao.create(member);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void testInvalidMemberEmailCreate() {

        Member member = new Member();
        member.setName("Test #2 user name");
        member.setPassword("verYseCurePa$$word1HashString2");
        member.setRole(Role.BASSIST);

        memberDao.create(member);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void testInvalidMemberNameCreate() {

        Member member = new Member();
        member.setEmail("test_member_2@gmail.com");
        member.setPassword("verYseCurePa$$word1HashString2");
        member.setRole(Role.BASSIST);

        memberDao.create(member);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testInvalidMemberPasswordCreate() {

        Member member = new Member();
        member.setName("Test #2 user name");
        member.setEmail("test_member_2@gmail.com");
        member.setRole(Role.BASSIST);

        memberDao.create(member);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testInvalidMemberRoleCreate() {

        Member member = new Member();
        member.setName("Test #2 user name");
        member.setEmail("test_member_2@gmail.com");
        member.setPassword("verYseCurePa$$word1HashString2");

        memberDao.create(member);
    }


}
