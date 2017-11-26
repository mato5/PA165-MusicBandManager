package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.facade.ManagerFacade;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.BandInvite;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Tour;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Matej Sojak 433294
 */
@Service
@Transactional
public class ManagerFacadeImpl implements ManagerFacade {

    final static Logger log = LoggerFactory.getLogger(MemberFacadeImpl.class);

    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private ManagerService managerService;

    @Override
    public void registerManager(ManagerDTO m, String unencryptedPassword) {
        Manager manager = beanMappingService.mapTo(m,Manager.class);
        managerService.registerManager(manager,unencryptedPassword);
        m.setId(manager.getId());
    }

    @Override
    public Collection<ManagerDTO> getAllManagers() {
        return beanMappingService.mapTo(managerService.getAllManagers(),ManagerDTO.class);
    }

    @Override
    public ManagerDTO findManagerById(Long id) {
        Manager manager = managerService.findManagerById(id);
        return (manager == null) ? null : beanMappingService.mapTo(manager,ManagerDTO.class);
    }

    @Override
    public ManagerDTO findManagerByName(String name) {
        Manager manager = managerService.findManagerByName(name);
        return (manager == null) ? null : beanMappingService.mapTo(manager,ManagerDTO.class);
    }

    @Override
    public boolean authenticate(UserAuthDTO u) {
        return managerService.authenticate(managerService.findManagerById(u.getId()),u.getPassword());
    }

    @Override
    public void changeEmail(UserChangeEmailDTO u) {
        Manager manager = managerService.findManagerById(u.getId());
        managerService.changeEmail(manager,u.getEmail());
    }

    @Override
    public void changePassword(UserChangePasswordDTO u) {
        Manager manager = managerService.findManagerById(u.getId());
        managerService.changePassword(manager,u.getPassword());
    }

    @Override
    public void addManagedBand(ManagerDTO m, BandDTO b) {
        Manager manager = beanMappingService.mapTo(m,Manager.class);
        Band band = beanMappingService.mapTo(b,Band.class);
        managerService.addManagedBand(manager,band);
    }

    @Override
    public void cancelManagedBand(ManagerDTO m, BandDTO b) {
        Manager manager = beanMappingService.mapTo(m,Manager.class);
        Band band = beanMappingService.mapTo(b,Band.class);
        managerService.cancelManagedBand(manager,band);
    }

    @Override
    public void addTour(ManagerDTO m, TourDTO t) {
        Manager manager = beanMappingService.mapTo(m,Manager.class);
        Tour tour = beanMappingService.mapTo(t,Tour.class);
        managerService.addTour(manager,tour);
    }

    @Override
    public void cancelTour(ManagerDTO m, TourDTO t) {
        Manager manager = beanMappingService.mapTo(m,Manager.class);
        Tour tour = beanMappingService.mapTo(t,Tour.class);
        managerService.cancelTour(manager,tour);
    }

    @Override
    public void addBandInvite(ManagerDTO m, BandInviteDTO b) {
        Manager manager = beanMappingService.mapTo(m,Manager.class);
        BandInvite invite = beanMappingService.mapTo(b,BandInvite.class);
        managerService.addBandInvite(manager,invite);
    }

    @Override
    public void cancelBandInvite(ManagerDTO m, BandInviteDTO b) {
        Manager manager = beanMappingService.mapTo(m,Manager.class);
        BandInvite invite = beanMappingService.mapTo(b,BandInvite.class);
        managerService.cancelBandInvite(manager,invite);
    }
}
