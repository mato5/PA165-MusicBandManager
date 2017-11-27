package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.exceptions.BandManagerServiceException;
import cz.muni.fi.pa165.dao.BandDao;
import cz.muni.fi.pa165.dao.SongDaoImpl;
import cz.muni.fi.pa165.dao.TourDao;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.entity.Tour;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author Alexander Kromka
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class TourServiceTest extends AbstractTransactionalTestNGSpringContextTests {


    @Mock
    private TourDao tourDao;

    @Autowired
    @InjectMocks
    private TourServiceImpl tourService;


    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareTest() {

        Manager testManager = new Manager();
        testManager.setId(1L);

        Band testBand = new Band();
        testBand.setId(1L);
        testBand.setName("testBand");
        testBand.setManager(testManager);

        Tour testTour = new Tour();
        testTour.setId(1L);
        testTour.setDatetime(new Date());
        testTour.setCityName("testCity");
        testTour.setBand(testBand);
        testTour.setManager(testManager);

        List<Tour> tourList = new ArrayList<>();
        tourList.add(testTour);

        when(this.tourDao.findById(testTour.getId())).thenReturn(testTour);
        when(this.tourDao.findAll()).thenReturn(tourList);
        when(this.tourDao.findByBand(testTour.getBand())).thenReturn(tourList);
        when(this.tourDao.findByManager(testTour.getManager())).thenReturn(tourList);
    }

    @Test
    public void setManagerNameTest() {
        tourService.findById(1l).setName("testResult");
        Assert.assertEquals(tourDao.findById(1l).getName(), "testResult");
    }

    @Test
    public void setCityNameTest() {
        tourService.findById(1l).setCityName("testCity");
        Assert.assertEquals(tourDao.findById(1l).getCityName(), "testCity");
    }

    @Test
    public void setDateTest() {
        tourService.findById(1l).setDatetime(new Date(2017));
        Assert.assertEquals(tourDao.findById(1l).getDatetime(), new Date(2017));
    }


}