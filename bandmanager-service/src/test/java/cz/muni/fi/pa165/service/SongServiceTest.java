package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.exceptions.BandManagerServiceException;
import cz.muni.fi.pa165.dao.BandDao;
import cz.muni.fi.pa165.dao.SongDaoImpl;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Song;
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
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class SongServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private SongDaoImpl songDao;

    @Mock
    private BandDao bandDao;

    @Autowired
    @InjectMocks
    private SongService songService;


    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareTestSongs() {

        List<Song> testSongsList;

        Band testBand = new Band();
        testBand.setName("Test Band");
        testBand.setId(1L);

        testSongsList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Song song = new Song();
            song.setName("TestSongTrack_" + i);
            song.setId((long) i);
            if (i % 3 == 0) {
                song.setDuration(0L);
            } else {
                song.setDuration((long) (Math.random() * (1000 - 1)) + 1);
            }
            testSongsList.add(song);
        }
        when(this.songDao.findAll()).thenReturn(testSongsList);
    }

    @BeforeMethod
    public void prepareTestBands() {

        Band testBand1 = new Band();
        testBand1.setName("Existing test band 1");
        testBand1.setId(1L);

        Band testBand2 = new Band();
        testBand2.setName("Existing test band 2");
        testBand2.setId(2L);

        when(this.bandDao.findAll()).thenReturn(Arrays.asList(testBand1, testBand2));

        when(this.bandDao.findById(Long.valueOf(org.mockito.AdditionalMatchers.gt(2L)))).thenReturn(null);
        when(this.bandDao.findById(Long.valueOf(Matchers.eq(1L)))).thenReturn(testBand1);
        when(this.bandDao.findById(Long.valueOf(Matchers.eq(2L)))).thenReturn(testBand2);
    }

    @Test
    public void validChangeDurationTest() {
        Assert.assertEquals(songService.findAll().size(), 10);
        List<Song> songs = songService.findAll();
        Song invalidDurationSong = songs.stream()
                .filter(song -> song.getDuration() <= 0)
                .findAny()
                .orElse(null);
        Assert.assertNotNull(invalidDurationSong);
        songService.changeDuration(invalidDurationSong, 60L);
    }

    @Test(expectedExceptions = BandManagerServiceException.class)
    public void invalidChangeDurationTest() {
        Assert.assertEquals(songService.findAll().size(), 10);
        List<Song> songs = songService.findAll();
        Song invalidDurationSong = songs.stream()
                .filter(song -> song.getDuration() > 0)
                .findAny()
                .orElse(null);
        Assert.assertNotNull(invalidDurationSong);
        songService.changeDuration(invalidDurationSong, -60L);
    }

    @Test
    public void validChangeBandTest() {
        Assert.assertEquals(songService.findAll().size(), 10);
        Assert.assertEquals(bandDao.findAll().size(), 2);
        Band firstBand = bandDao.findAll().get(0);
        Assert.assertNotNull(firstBand);
        Band secondBand = bandDao.findAll().get(1);
        Assert.assertNotNull(secondBand);

        List<Song> songs = songService.findAll();
        Song song = songs.get(0);
        Assert.assertNotNull(song);
        songService.changeBand(song, secondBand);
    }

    @Test(expectedExceptions = BandManagerServiceException.class)
    public void invalidChangeBandTest() {
        Assert.assertEquals(songService.findAll().size(), 10);
        Assert.assertEquals(bandDao.findAll().size(), 2);
        Band firstBand = bandDao.findAll().get(0);
        Assert.assertNotNull(firstBand);
        Band secondBand = bandDao.findAll().get(1);
        Assert.assertNotNull(secondBand);

        List<Song> songs = songService.findAll();
        Song song = songs.get(0);
        Assert.assertNotNull(song);

        Band thirdFakeBand = new Band();
        thirdFakeBand.setId(3L);

        songService.changeBand(song, thirdFakeBand);
    }

}