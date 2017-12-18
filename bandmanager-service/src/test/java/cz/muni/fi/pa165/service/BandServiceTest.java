package cz.muni.fi.pa165.service;


import cz.fi.muni.pa165.exceptions.BandManagerServiceException;
import cz.muni.fi.pa165.PersistanceTestingContext;
import cz.muni.fi.pa165.dao.BandDao;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.enums.Genre;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.facade.AlbumFacadeImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Miroslav Kadlec
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BandServiceTest extends AbstractTestNGSpringContextTests  {
    final static Logger logger = LoggerFactory.getLogger(AlbumFacadeImpl.class);
    
    @Mock
    private BandDao bandDao;
    
    @Autowired
    @InjectMocks
    private BandService bandService;
    
    @BeforeClass
    public void setup() throws ServiceException
    {
        MockitoAnnotations.initMocks(this);
    }
    
    private List<Band> testBandList;
    private Member member1;
    private Member member2;
    private Album album1;
    private Album album2;
    private Album album2Test;
    
    
    @BeforeMethod
    public void prepareTestBand() {
        this.testBandList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Band band = new Band();
            band.setName("BandName_" + i);
            if (i % 3 == 0) {
                band.setGenre(Genre.REGGAE);
            }
            this.testBandList.add(band);
        }
        this.member1 = new Member();
        this.member2 = new Member();
        this.member1.setId(Long.valueOf(1));
        this.member1.setEmail("mail@1.cz");
        this.member2.setId(Long.valueOf(2));
        this.member2.setEmail("mail@2.cz");
        
        this.album1 = new Album();
        this.album1.setId(Long.valueOf(1));
        this.album1.setName("album1");
        this.album1.setBand(this.testBandList.get(0));
        this.album2 = new Album();
        this.album2.setId(Long.valueOf(2));
        this.album2.setName("album2");
        this.album2Test = new Album();
        this.album2Test.setId(Long.valueOf(2));
        this.album2Test.setName("album2");
        this.album2Test.setBand(this.testBandList.get(0));
        
        this.testBandList.get(0).addMember(this.member1);
        this.testBandList.get(0).addAlbum(this.album1);
        
        
        when(this.bandDao.findAll()).thenReturn(this.testBandList);
        doNothing().when(this.bandDao).update(any(Band.class));
    }
    
    @Test
    public void findByGenreTest() {
        List<Band> reggaeBands = this.bandService.findByGenre(Genre.REGGAE);
        Assert.assertNotNull(reggaeBands);
        Assert.assertEquals(reggaeBands.size(), 4);
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(reggaeBands.get(i).getGenre(), Genre.REGGAE);
            Assert.assertEquals(reggaeBands.get(i).getName(), "BandName_" + 3 * i);
        }
    }
    
    @Test(expectedExceptions = BandManagerServiceException.class)
    public void changeManagerToNullTest() {
        this.bandService.changeManager(this.testBandList.get(0), null);
    }
    
    @Test(expectedExceptions = BandManagerServiceException.class)
    public void addNullMemberTest() {
        this.bandService.addMember(this.testBandList.get(0), null);
    }
    
    @Test(expectedExceptions = BandManagerServiceException.class)
    public void addIncorrectMemberTest() {
        this.bandService.addMember(this.testBandList.get(0), this.member1);
    }
    
    @Test(expectedExceptions = BandManagerServiceException.class)
    public void removeNullMemberTest() {
        this.bandService.removeMember(this.testBandList.get(0), null);
    }
    
    @Test(expectedExceptions = BandManagerServiceException.class)
    public void removeIncorrectMemberTest() {
        this.bandService.removeMember(this.testBandList.get(0), this.member2);
    }
    
    @Test
    public void addCorrectMemberTest() {
        Assert.assertEquals(this.testBandList.get(0).getMembers().size(), 1);
        this.bandService.addMember(this.testBandList.get(0), this.member2);
        Set<Member> mSet = this.testBandList.get(0).getMembers();
        Assert.assertEquals(mSet.size(), 2);
        Assert.assertTrue(mSet.contains(this.member1));
        Assert.assertTrue(mSet.contains(this.member2));
    }
    
    @Test
    public void removeCorrectMemberTest() {
        this.bandService.removeMember(this.testBandList.get(0), this.member1);
        Assert.assertEquals(this.testBandList.get(0).getMembers().size(), 0);
    }
    
    
    
    
    @Test(expectedExceptions = BandManagerServiceException.class)
    public void addNullAlbumTest() {
        this.bandService.addAlbum(this.testBandList.get(0), null);
    }
    
    /*@Test(expectedExceptions = BandManagerServiceException.class)
    public void addIncorrectAlbumTest() {
        this.bandService.addAlbum(this.testBandList.get(0), this.album1);
    }*/
    
    @Test(expectedExceptions = BandManagerServiceException.class)
    public void removeNullAlbumTest() {
        this.bandService.removeAlbum(this.testBandList.get(0), null);
    }
    
    @Test(expectedExceptions = BandManagerServiceException.class)
    public void removeIncorrectAlbumTest() {
        Assert.assertEquals(this.testBandList.get(0).getAlbums().size(), 1);
        this.bandService.removeAlbum(this.testBandList.get(0), this.album2);
    }
    
    @Test
    public void addCorrectAlbumTest() {
        this.bandService.addAlbum(this.testBandList.get(0), this.album2);
        
        List<Album> albumsGot = this.testBandList.get(0).getAlbums().stream().collect(Collectors.toList());

        Assert.assertEquals(albumsGot.size(), 2);
        Assert.assertEquals(albumsGot.get(0), this.album1);
        Assert.assertEquals(albumsGot.get(1), this.album2);

    }
    
    @Test
    public void removeCorrectAlbumTest() {
        this.bandService.removeAlbum(this.testBandList.get(0), this.album1);
        Assert.assertEquals(this.testBandList.get(0).getAlbums().size(), 0);
    }
}
