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
 * @author Matej Sojak 433294
 */
@ContextConfiguration(classes = PersistanceTestingContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class BandDaoTest extends AbstractTestNGSpringContextTests {
    @Inject
    private BandDao bandDao;

    /*@Inject
    private ManagerDao managerDao;
    */

    private Band band1;
    private Band band2;
    //private Manager manager;

    @BeforeMethod
    public void prepareBands() {


        band1 = new Band();
        band1.setName("The Black Keys");
        band1.setGenre(Genre.BLUES);
        band1.setLogoURI("testLogoUri1");
        /*manager = new Manager();
        manager.addBand(band1);
        manager.setName("Test #1 user name");
        manager.setEmail("test_manager_1@gmail.com");
        manager.setPassword("verYseCurePa$$word1HashString1");
        managerDao.create(manager)
        band1.setManager(manager);*/
        bandDao.create(band1);

        band2 = new Band();
        band2.setName("The Rolling Stones");
        band2.setGenre(Genre.ROCK);
        band2.setLogoURI("testLogoUri2");
        //manager.addBand(band2);
        //band2.setManager(manager);
        bandDao.create(band2);
    }

    @Test
    public void nonExistentBandReturnsNull() {
        Assert.assertNull(bandDao.findById(12345677890l));
    }

    @Test
    public void testExistentBandFindById() {
        Band existingBand1 = bandDao.findById(band1.getId());

        Assert.assertEquals(existingBand1.getGenre(), Genre.BLUES);
        Assert.assertEquals(existingBand1.getLogoURI(), "testLogoUri1");
        //Assert.assertEquals(existingBand1.getManager(),manager);
        Assert.assertEquals(existingBand1.getMembers().size(), 0);
    }

    @Test
    public void testExistentBandFindByName() {
        Band existingBand1 = bandDao.findByName(band1.getName());

        Assert.assertEquals(existingBand1.getGenre(), Genre.BLUES);
        Assert.assertEquals(existingBand1.getLogoURI(), "testLogoUri1");
        //Assert.assertEquals(existingBand1.getManager(),manager);
        Assert.assertEquals(existingBand1.getMembers().size(), 0);
    }

    /*@Test
    public void testExistentBandFindByManager() {
        List<Band> existingBands = bandDao.findByManager(band1.getManager());

        Assert.assertEquals(existingBands.size(), 2);
    }*/

    @Test
    public void testAllExistentBands() {
        List<Band> existingBands = bandDao.findAll();

        Assert.assertEquals(existingBands.size(), 2);
    }

    @Test
    public void testDeleteExistentBand() {
        Band existingBand1 = bandDao.findById(band1.getId());
        bandDao.delete(existingBand1);

        Band nonExistingBand1 = bandDao.findById(band1.getId());

        Assert.assertEquals(nonExistingBand1, null);
    }

    /*@Test
    public void testUpdateExistentBand() {
        Band existingBand1 = bandDao.findById(band1.getId());
        existingBand1.setName("The White Stripes");
        bandDao.update(existingBand1);

        Band updatedBand = bandDao.findById(band1.getId());

        Assert.assertEquals(updatedBand.getName(), "The White Stripes");
    }*/

    @Test(expectedExceptions = JpaSystemException.class)
    public void testCreateDuplicateBand() {

        Band duplicateBand = new Band();
        duplicateBand.setName("The Black Keys");
        duplicateBand.setGenre(Genre.BLUES);
        duplicateBand.setLogoURI("testLogoUri1");
        /*manager.addBand(band1);
        band1.setManager(manager);*/
        bandDao.create(duplicateBand);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void testInvalidBandObjectCreate() {

        Band b = null;
        bandDao.create(b);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testInvalidBandNameCreate() {

        Band b = new Band();
        band1.setGenre(Genre.BLUES);
        band1.setLogoURI("testLogoUri1");

        bandDao.create(b);
    }






}
