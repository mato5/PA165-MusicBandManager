package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistanceTestingContext;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.enums.Genre;
import java.util.List;
import javax.inject.Inject;
import org.testng.annotations.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

/**
 *
 * @author Miroslav Kadlec
 */
@ContextConfiguration(classes = PersistanceTestingContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SongDaoTest extends AbstractTestNGSpringContextTests {
    @Inject
    ManagerDao managerDao;
    
    @Inject
    private SongDao songDao;
    
    @Inject
    private BandDao bandDao;
    
    private Song song1;
    private Song song2;
    private Song song3;
    private Band band1;
    private Band band2;
    
    @BeforeMethod
    private void setUp() {
        Manager manager = new Manager();
        manager.setEmail("test@email.com");
        manager.setName("Test Manager");
        manager.setPassword("******");
        this.managerDao.create(manager);
        
        this.band1 = new Band();
        this.band1.setName("TestBand1");
        this.band1.setGenre(Genre.CLASSICAL);
        this.band1.setLogoURI("testBand1LogoUrl");
        this.band1.setManager(manager);
        this.bandDao.create(this.band1);
        
        this.band2 = new Band();
        this.band2.setName("TestBand2");
        this.band2.setGenre(Genre.ROCK);
        this.band2.setLogoURI("testBand2LogoUrl");
        this.band2.setManager(manager);
        this.bandDao.create(this.band2);
        
        this.song1 = new Song();
        this.song1.setName("testSong1");
        this.song1.setDuration(Long.valueOf(250));
        this.song1.setBand(this.band1);
        this.songDao.create(song1);
        
        this.song2 = new Song();
        this.song2.setName("testSong2");
        this.song2.setDuration(Long.valueOf(310));
        this.song2.setBand(this.band1);
        this.songDao.create(song2);
        
        this.song3 = new Song();
        this.song3.setName("testSong1");
        this.song3.setDuration(Long.valueOf(230));
        this.song3.setBand(this.band2);
        this.songDao.create(song3);
    }
    
    @Test
    public void findByIdTest() {
        Song foundSong = this.songDao.findById(this.song1.getId());
        Assert.assertNotNull(foundSong);
        Assert.assertEquals(foundSong.getName(), this.song1.getName());
        Assert.assertEquals(foundSong.getDuration(), this.song1.getDuration());
        Assert.assertEquals(foundSong.getBand(), this.song1.getBand());
    }
    
    @Test
    public void updateTest() {
        this.song1.setDuration(Long.valueOf(123));
        this.songDao.update(song1);
        Song foundSong = this.songDao.findById(this.song1.getId());;
        Assert.assertNotNull(foundSong);
        Assert.assertEquals(foundSong.getDuration(), Long.valueOf(123));
    }
    
    @Test
    public void findAllTest() {
        List<Song> foundSongs = this.songDao.findAll();
        int resultCode = 0;
        for (Song foundSong : foundSongs) {
            if (foundSong.equals(this.song1)) {
                resultCode += 1;
            } else if (foundSong.equals(this.song2)) {
                resultCode += 10;
            } else if (foundSong.equals(this.song3)) {
                resultCode += 100;
            }
        }
        Assert.assertEquals(resultCode, 111);
    }
    
    @Test
    public void findByBandTest() {
        List<Song> foundSongs = this.songDao.findByBand(this.band1);
        Assert.assertEquals(foundSongs.size(), 2);
        int resultCode = 0;
        for (Song foundSong : foundSongs) {
            if (foundSong.equals(this.song1)) {
                resultCode += 1;
            } else if (foundSong.equals(this.song2)) {
                resultCode += 10;
            }
        }
        Assert.assertEquals(resultCode, 11);
    }
    
    @Test
    public void findByNameTest() {
        List<Song> foundSongs = this.songDao.findByName("testSong1");
        Assert.assertEquals(foundSongs.size(), 2);
        int resultCode = 0;
        for (Song foundSong : foundSongs) {
            if (foundSong.equals(this.song1)) {
                resultCode += 1;
            } else if (foundSong.equals(this.song3)) {
                resultCode += 10;
            }
        }
        Assert.assertEquals(resultCode, 11);
    }
    
    @Test
    public void deleteTest() {
        this.songDao.delete(song2);
        List<Song> foundSongs = this.songDao.findAll();
        int resultCode = 0;
        for (Song foundSong : foundSongs) {
            if (foundSong.equals(this.song1)) {
                resultCode += 1;
            } else if (foundSong.equals(this.song2)) {
                resultCode += 10;
            } else if (foundSong.equals(this.song3)) {
                resultCode += 100;
            }
        }
        Assert.assertEquals(resultCode, 101);
    }
    
}
