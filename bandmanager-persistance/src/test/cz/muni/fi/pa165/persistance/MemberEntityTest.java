package cz.muni.fi.pa165.persistance;

import cz.muni.fi.pa165.PersistanceTestingContext;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.enums.Role;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@ContextConfiguration(classes = PersistanceTestingContext.class)
public class MemberEntityTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void createMemberTest() {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Member member = new Member();
        member.setEmail("test");
        member.setName("test");

        member.setRole(Role.DRUMMER);
        member.setPassword("SuperpasswordHash");

        em.persist(member);
        em.getTransaction().commit();
        em.close();
    }

}
