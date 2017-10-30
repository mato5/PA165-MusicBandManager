package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistanceTestingContext;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.enums.Genre;
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
 * @author Alexander Kromka
 */
@ContextConfiguration(classes = PersistanceTestingContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ManagerDaoTest extends AbstractTestNGSpringContextTests {
    @Inject
    private ManagerDao managerDao;

    @Inject
    private BandDao bandDao;

    private Manager manager1;
    private Manager manager2;
    private Band band1;
    private Band band2;
    private Band band3;

    @BeforeMethod
    public void prepareManagers() {


        manager1 = new Manager();
        manager1.setName("Kingpin");
        manager1.setEmail("test_manager_1@gmail.com");
        manager1.setPassword("blablatextohYes4$");

        manager2 = new Manager();
        manager2.setName("Kingpin2");
        manager2.setEmail("test_manager_2@gmail.com");
        manager2.setPassword("blablagaatextohYes4$");

        band1 = new Band();
        band1.setName("The Rolling Stones");
        band1.setGenre(Genre.ROCK);
        band1.setLogoURI("testLogoUri1");
        manager1.addBand(band1);
        band1.setManager(manager1);

        band2 = new Band();
        band2.setName("Led Zeppelin");
        band2.setGenre(Genre.ROCK);
        band2.setLogoURI("testLogoUri2");
        manager1.addBand(band2);
        band2.setManager(manager1);

        band3 = new Band();
        band3.setName("Alligators");
        band3.setGenre(Genre.ROCK);
        band3.setLogoURI("testLogoUri3");
        manager2.addBand(band3);
        band3.setManager(manager2);

        managerDao.create(manager1);
        managerDao.create(manager2);
        bandDao.create(band1);
        bandDao.create(band2);
        bandDao.create(band3);

    }

    @Test
    public void nonExistentManagerReturnsNull() {
        Assert.assertNull(managerDao.findById(12345677890l));
    }

    @Test
    public void testExistentManagerFindById() {
        Manager existingManager1 = managerDao.findById(manager1.getId());

        Assert.assertEquals(existingManager1.getEmail(), "test_manager_1@gmail.com");

    }

    @Test
    public void testExistentManagerFindByName() {
        Manager existingManager1 = managerDao.findById(manager1.getId());

        Assert.assertEquals(existingManager1.getEmail(), "test_manager_1@gmail.com");
    }


    @Test
    public void testAllExistentManagers() {
        List<Manager> existingManagers = managerDao.findAll();

        Assert.assertEquals(existingManagers.size(), 2);
    }

    @Test
    public void testDeleteExistentManager() {
        Manager existingManager1 = managerDao.findById(manager1.getId());
        managerDao.delete(existingManager1);

        Manager nonExistingManager1 = managerDao.findById(manager1.getId());

        Assert.assertEquals(nonExistingManager1, null);
    }

    @Test
    public void testUpdateExistentManager() {
        Manager existingManager1 = managerDao.findById(manager1.getId());
        existingManager1.setName("Bill Gates");
        managerDao.update(existingManager1);

        Manager updatedManager = managerDao.findById(manager1.getId());

        Assert.assertEquals(updatedManager.getName(), "Bill Gates");
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void testCreateDuplicateBand() {

        Manager duplicateManager = new Manager();
        duplicateManager.setName("Kingpin");
        duplicateManager.setEmail("test_manager_1@gmail.com");
        duplicateManager.setPassword("blablatextohYes4$");
        duplicateManager.addBand(band1);

        managerDao.create(duplicateManager);

    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void testInvalidManagerObjectCreate() {

        Manager manager = null;
        managerDao.create(manager);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void testInvalidManagerNameCreate() {

        Manager manager = new Manager();

        managerDao.create(manager);
    }

}
