package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.exceptions.BandManagerServiceException;
import cz.muni.fi.pa165.dao.AlbumDao;
import cz.muni.fi.pa165.dao.BandDao;
import cz.muni.fi.pa165.dao.SongDao;
import cz.muni.fi.pa165.entity.Album;
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
public class AlbumServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private SongDao songDao;

    @Mock
    private BandDao bandDao;

    @Mock
    private AlbumDao albumDao;

    @Autowired
    @InjectMocks
    private AlbumService albumService;

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
//        when(this.songDao.findAll()).thenReturn(testSongsList);
        when(this.songDao.findById(Long.valueOf(Matchers.eq(1L)))).thenReturn(testSongsList.get(1));
        when(this.songDao.findById(Long.valueOf(Matchers.eq(2L)))).thenReturn(testSongsList.get(2));
        when(this.songDao.findById(Long.valueOf(Matchers.eq(3L)))).thenReturn(testSongsList.get(3));
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

    @BeforeMethod
    public void prepareTestAlbums() {

        List<Album> testAlbumList;
        testAlbumList = new ArrayList<>();

        Band testBand1 = new Band();
        testBand1.setName("Existing test band 1");
        testBand1.setId(1L);

        Band testBand2 = new Band();
        testBand2.setName("Existing test band 2");
        testBand2.setId(2L);

        Album testAlbum = new Album();
        testAlbum.setBand(testBand1);

        testAlbumList.add(testAlbum);

        when(this.albumDao.findAll()).thenReturn(testAlbumList);
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addExistingSongTest() {
        Assert.assertEquals(albumService.findAll().size(), 1);
        Album album = albumService.findAll().get(0);
        int previousSize = album.getSongs().size();
        albumService.addSong(album, songDao.findById(1L));
        Assert.assertEquals(albumService.findAll().get(0).getSongs().size(), previousSize + 1);
    }

    @Test(expectedExceptions = BandManagerServiceException.class)
    public void addNonExistingSongTest() {
        Assert.assertEquals(albumService.findAll().size(), 1);
        Album album = albumService.findAll().get(0);
        albumService.addSong(album, new Song());
    }


    @Test
    public void removeNonLastSongTest() {
        Assert.assertEquals(albumService.findAll().size(), 1);
        Album album = albumService.findAll().get(0);
        int previousSize = album.getSongs().size();
        albumService.addSong(album, songDao.findById(1L));
        albumService.addSong(album, songDao.findById(2L));
        albumService.deleteSong(album, songDao.findById(2L));
    }

    /*@Test(expectedExceptions = BandManagerServiceException.class)
    public void removeLastSongTest() {
        Assert.assertEquals(albumService.findAll().size(), 1);
        Album album = albumService.findAll().get(0);
        int previousSize = album.getSongs().size();
        albumService.addSong(album, songDao.findById(1L));
        albumService.addSong(album, songDao.findById(2L));
        albumService.deleteSong(album, songDao.findById(2L));
        albumService.deleteSong(album, songDao.findById(1L));
    }*/
}
