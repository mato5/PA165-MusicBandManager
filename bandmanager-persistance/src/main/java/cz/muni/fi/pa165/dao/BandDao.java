package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Manager;
import cz.muni.fi.pa165.entity.Member;

import java.util.List;

/**
 * @author Iurii xkuznetc Kuznetcov
 */
public interface BandDao {
    public Band findById(Long id);

    public void create(Band band);

    public void delete(Band band);

    public void update(Band band);

    public List<Band> findAll();

    public Band findByName(String name);

    public List<Band> findByManager(Manager manager);

}
