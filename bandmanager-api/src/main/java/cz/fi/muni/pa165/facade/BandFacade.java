package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.BandCreateDTO;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.dto.MemberDTO;
import cz.muni.fi.pa165.enums.Genre;
import java.util.List;

/**
 *
 * @author kami
 */
public interface BandFacade {

    public BandDTO findById(Long bandId);

    public Long create(BandCreateDTO bandDTO);

    public void delete(Long bandId);

    public List<BandDTO> findAll();

    public List<BandDTO> findByManager(ManagerDTO managerDTO);
 
    public List<BandDTO> findByGenre(Genre genreDTO);

    /*public void addMember(BandDTO band, MemberDTO memberDTO);

    public void removeMember(BandDTO band, MemberDTO memberDTO);
    */
    public void changeManager(BandDTO band, ManagerDTO managerDTO);

    public void changeGenre(BandDTO band, Genre genre);
}
