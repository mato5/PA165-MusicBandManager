package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.BandCreateDTO;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.facade.BandFacade;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.enums.Genre;
import cz.muni.fi.pa165.service.BandService;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.ManagerService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Miroslav Kadlec
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BandFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private BandService bandService;

    @Mock
    private ManagerService managerService;

    @Mock
    private BeanMappingService beanMappingService;

    @Autowired
    @InjectMocks
    private BandFacade bandFacade;

    @BeforeClass
    public void setupClass() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private Manager testManager;
    private Manager testManagerOther;

    private BandDTO bandDTO;

    private List<Band> bandList;
    private List<Band> bandListByManager;
    private List<Band> bandListByReagge;

    @BeforeMethod
    private void setUp() {

        this.testManager = new Manager();
        this.testManager.setId(Long.valueOf(2));
        this.testManager.setName("Manager");
        this.testManager.setEmail("manager@man.cz");

        this.testManagerOther = new Manager();
        this.testManager.setId(Long.valueOf(3));

        this.bandList = new ArrayList<>();
        this.bandListByManager = new ArrayList<>();
        this.bandListByReagge = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Band band = new Band();
            band.setName("BandName_" + i);
            band.setId(Long.valueOf(i));
            if (i % 3 == 0) {
                band.setManager(this.testManager);
                this.bandListByManager.add(band);
            }
            if (i % 2 == 0) {
                band.setGenre(Genre.REGGAE);
                this.bandListByReagge.add(band);
            }
            this.bandList.add(band);
        }

        this.bandDTO = new BandDTO();
        this.bandDTO.setId(this.bandList.get(0).getId());
        this.bandDTO.setGenre(this.bandList.get(0).getGenre());

        when(this.beanMappingService.mapTo(any(Band.class), eq(BandDTO.class))).thenReturn(this.bandDTO);
        when(this.beanMappingService.mapTo(any(List.class), eq(BandDTO.class))).thenReturn(null);
    }

    @Test
    public void findByIdTest() {
        when(this.bandService.findById(Long.valueOf(0))).thenReturn(this.bandList.get(0));

        Long id = Long.valueOf(0);
        BandDTO b = this.bandFacade.findById(id);

        verify(this.bandService).findById(id);
        verify(this.beanMappingService).mapTo(this.bandList.get(0), BandDTO.class);
    }

    @Test
    public void createTest() {
        when(this.managerService.findManagerById(any(Long.class))).thenReturn(this.testManager);
        when(this.bandService.create(this.bandList.get(0))).thenReturn(this.bandList.get(0));
        
        BandCreateDTO b = new BandCreateDTO();
        b.setName(this.bandList.get(0).getName());
        b.setLogoURI(this.bandList.get(0).getLogoURI());
        b.setGenre(this.bandList.get(0).getGenre());
        b.setManagerId(Long.valueOf(2));

        this.bandFacade.create(b);

        verify(this.managerService).findManagerById(Long.valueOf(2));
        verify(this.bandService).create(this.bandList.get(0));
    }

    @Test
    public void deleteTest() {
        when(this.bandService.findById(Long.valueOf(1))).thenReturn(this.bandList.get(1));
        when(this.managerService.findManagerById(any(Long.class))).thenReturn(this.testManager);

        this.bandFacade.delete(this.bandList.get(1).getId());

        verify(this.bandService).delete(this.bandList.get(1));
        verify(this.bandService).findById(Long.valueOf(1));
    }

    @Test
    public void findAllTest() {
        when(this.bandService.findAll()).thenReturn(this.bandList);

        List<BandDTO> bL = this.bandFacade.findAll();

        verify(this.bandService).findAll();
        verify(this.beanMappingService).mapTo(this.bandList, BandDTO.class);
    }

    @Test
    public void findAByManagerTest() {
        when(this.bandService.findByManager(this.testManager)).thenReturn(this.bandListByManager);
        when(this.managerService.findManagerById(Long.valueOf(2))).thenReturn(this.testManager);

        ManagerDTO m = new ManagerDTO();
        m.setId(Long.valueOf(this.testManager.getId()));

        List<BandDTO> bL = this.bandFacade.findByManager(m);

        verify(this.bandService).findByManager(this.testManager);
        verify(this.beanMappingService).mapTo(this.bandListByManager, BandDTO.class);
    }

    @Test
    public void findAByGenreTest() {
        when(this.bandService.findByGenre(Genre.REGGAE)).thenReturn(this.bandListByReagge);

        List<BandDTO> bL = this.bandFacade.findByGenre(Genre.REGGAE);

        verify(this.bandService).findByGenre(Genre.REGGAE);
        verify(this.beanMappingService).mapTo(this.bandListByReagge, BandDTO.class);
    }

    @Test
    public void changeManagerTest() {
        when(this.bandService.changeManager(this.bandList.get(6), this.testManagerOther)).thenReturn(this.bandList.get(6));
        when(this.bandService.findById(Long.valueOf(6))).thenReturn(this.bandList.get(6));
        when(this.managerService.findManagerById(this.testManagerOther.getId())).thenReturn(this.testManagerOther);

        ManagerDTO m = new ManagerDTO();
        m.setId(this.testManagerOther.getId());
        BandDTO b = new BandDTO();
        b.setId(this.bandList.get(6).getId());

        this.bandFacade.changeManager(b, m);

        verify(this.bandService).changeManager(this.bandList.get(6), this.testManagerOther);
    }

    @Test
    public void changeGenreTest() {
        when(this.bandService.findById(Long.valueOf(7))).thenReturn(this.bandList.get(7));

        BandDTO b = new BandDTO();
        b.setId(this.bandList.get(7).getId());

        this.bandFacade.changeGenre(b, Genre.JAZZ);

        verify(this.bandService).changeGenre(this.bandList.get(7), Genre.JAZZ);
    }
}
