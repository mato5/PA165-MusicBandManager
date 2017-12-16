package cz.muni.fi.pa165.facade;

import cz.fi.muni.pa165.dto.AlbumCreateDTO;
import cz.fi.muni.pa165.dto.AlbumDTO;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.facade.AlbumFacade;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.enums.Genre;
import cz.muni.fi.pa165.service.AlbumService;
import cz.muni.fi.pa165.service.BandService;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.SongService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.facade.AlbumFacadeImpl;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Iurii xkuznetc Kuznetcov
 */


@ContextConfiguration(classes = ServiceConfiguration.class)
public class AlbumFacadeTest {

    private BandService bandService;

    private SongService songService;

    private BeanMappingService beanMappingService;

    private AlbumService albumService;

    @Autowired
    @InjectMocks
    private AlbumFacadeImpl albumFacade;


    private Manager firstManager;
    private Manager secondManager;
    private Manager fakeManager;

    private Band firstBand;
    private BandDTO firstBandDTO;
    private Band secondBand;
    private BandDTO secondBandDTO;

    private List<Album> albumList;
    private List<Song> songList;

    private AlbumDTO albumDTO;


    @BeforeMethod
    private void setUp() {

        this.albumService = mock(AlbumService.class);
        this.songService = mock(SongService.class);
        this.beanMappingService = mock(BeanMappingService.class);
        this.bandService = mock(BandService.class);

        MockitoAnnotations.initMocks(this);

        this.firstManager = new Manager();
        this.firstManager.setId(1L);
        this.firstManager.setName("Manager 1");
        this.firstManager.setEmail("manager_1@man.cz");

        this.secondManager = new Manager();
        this.secondManager.setId(2L);
        this.secondManager.setName("Manager 2");
        this.secondManager.setEmail("manager_2@man.cz");

        this.firstBand = new Band();
        this.firstBand.setId(1L);
        this.firstBand.setName("First test band");
        this.firstBand.setGenre(Genre.ROCK);
        this.firstBand.setManager(this.firstManager);

        this.secondBand = new Band();
        this.secondBand.setId(2L);
        this.secondBand.setName("Second test band");
        this.secondBand.setGenre(Genre.INDUSTRIAL);
        this.secondBand.setManager(this.secondManager);

        this.firstBandDTO = new BandDTO();
        this.firstBandDTO.setId(this.firstBand.getId());
        this.firstBandDTO.setName(this.firstBand.getName());
        this.firstBandDTO.setGenre(this.firstBand.getGenre());

        this.secondBandDTO = new BandDTO();
        this.secondBandDTO.setId(this.secondBand.getId());
        this.secondBandDTO.setName(this.secondBand.getName());
        this.secondBandDTO.setGenre(this.secondBand.getGenre());

        this.songList = new ArrayList<>();
        this.albumList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Song song = new Song();
            song.setName("TestSongTrack_" + i);
            song.setDuration((long) (Math.random() * (1000 - 1)) + 1);
            song.setId((long) i);
            if (i < 5) {
                song.setBand(this.firstBand);
            } else {
                song.setBand(this.secondBand);
            }
            this.songList.add(song);
        }

        Album firstAlbum = new Album();
        firstAlbum.setId(1L);
        firstAlbum.setBand(firstBand);
        firstAlbum.setName("First test album");

        Album secondAlbum = new Album();
        secondAlbum.setId(2L);
        secondAlbum.setBand(secondBand);
        secondAlbum.setName("Second test album");

        for (int i = 0; i < 10; i++) {
            if (i < 5) {
                firstAlbum.addSong(this.songList.get(i));
            } else {
                secondAlbum.addSong(this.songList.get(i));
            }
        }

        this.albumList.add(firstAlbum);
        this.albumList.add(secondAlbum);

        this.albumDTO = new AlbumDTO();
        this.albumDTO.setId(this.albumList.get(0).getId());
        this.albumDTO.setName(this.albumList.get(0).getName());
        this.albumDTO.setBand(this.firstBandDTO);

        firstManager.addBand(firstBand);
        secondManager.addBand(secondBand);

        when(this.beanMappingService.mapTo(any(Album.class), eq(AlbumDTO.class))).thenReturn(this.albumDTO);
        when(this.beanMappingService.mapTo(any(List.class), eq(AlbumDTO.class))).thenReturn(null);
    }

    @Test
    public void findByIdTest() {
        when(this.albumService.findById(1L)).thenReturn(this.albumList.get(0));

        Long id = 1L;
        AlbumDTO albumById = this.albumFacade.findById(id);

        verify(this.albumService).findById(id);
        verify(this.beanMappingService).mapTo(this.albumList.get(0), AlbumDTO.class);
    }

    @Test
    public void findAllTest() {
        when(this.albumService.findAll()).thenReturn(this.albumList);

        Collection<AlbumDTO> all = this.albumFacade.findAll();

        verify(this.albumService).findAll();
        verify(this.beanMappingService).mapTo(this.albumList, AlbumDTO.class);
    }

    @Test
    public void findByBandTest() {
        when(this.albumService.findByBand(this.firstBand)).thenReturn(Arrays.asList(this.albumList.get(0)));
        when(this.bandService.findById(1L)).thenReturn(this.firstBand);

        BandDTO bandDTO = new BandDTO();
        bandDTO.setId(this.firstBand.getId());

        this.albumFacade.findByBand(bandDTO.getId());

        verify(this.albumService).findByBand(firstBand);
        verify(this.beanMappingService).mapTo(Arrays.asList(this.albumList.get(0)), AlbumDTO.class);
    }

    @Test
    public void createAlbumTest() {
        when(this.albumService.create(any(Album.class))).thenReturn(this.albumList.get(0));
        when(this.bandService.findById(1L)).thenReturn(this.firstBand);

        AlbumCreateDTO albumCreateDTO = new AlbumCreateDTO();
        albumCreateDTO.setBandId(this.firstBand.getId());
        albumCreateDTO.setName(this.firstBand.getName());

        Long albumId = this.albumFacade.createAlbum(albumCreateDTO);

        verify(this.bandService).findById(1L);
        verify(this.albumService).create(any(Album.class));
    }

    @Test
    public void deleteAlbumTest() {
        when(this.albumService.findById(1L)).thenReturn(this.albumList.get(0));
        when(this.bandService.findById(1L)).thenReturn(this.firstBand);

        Long id = 1L;

        this.albumFacade.deleteAlbum(id);
        verify(this.albumService).delete(this.albumList.get(0));
    }

    @Test
    public void addSongTest() {
        when(this.albumService.findById(1L)).thenReturn(this.albumList.get(0));
        when(this.bandService.findById(1L)).thenReturn(this.firstBand);
        when(this.songService.findById(6L)).thenReturn(this.songList.get(6));

        this.albumFacade.addSong(1L, 6L);

        verify(this.albumService).addSong(this.albumList.get(0), this.songList.get(6));
    }

    @Test
    public void deleteSongTest() {

        when(this.albumService.findById(1L)).thenReturn(this.albumList.get(0));
        when(this.bandService.findById(1L)).thenReturn(this.firstBand);
        when(this.songService.findById(6L)).thenReturn(this.songList.get(6));

        this.albumFacade.deleteSong(1L, 6L);

        verify(this.albumService).deleteSong(this.albumList.get(0), this.songList.get(6));
    }
}
