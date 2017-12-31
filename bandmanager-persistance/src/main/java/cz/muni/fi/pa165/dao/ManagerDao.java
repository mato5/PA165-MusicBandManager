package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Manager;

import java.util.List;

/**
 * @author Alexander Kromka
 * <p>
 * Data Access Object interface for Manager entity with basic CRUD operations and some filtering.
 */
public interface ManagerDao {
    /**
     * Gets Manager object by it's id
     *
     * @param id Long - id of Manager object
     * @return Manager manager object
     */
    public Manager findById(Long id);

    /**
     * Persist new Manager object. Provides some basic fields values validation.
     *
     * @param manager Manager object to persist
     */
    public void create(Manager manager);

    /**
     * Delete existing Manager object.
     *
     * @param manager Manager object to delete.
     */
    public void delete(Manager manager);

    /**
     * Merge changes in Manager object and persist modified object. Provides some basic fields values validation.
     *
     * @param manager
     */
    public void update(Manager manager);

    /**
     * Get all persistent Manager objects.
     *
     * @return List<Manager> - list of stored Manager objects.
     */
    public List<Manager> findAll();

    /**
     * Get Manager object by it's name.
     *
     * @param name String - manager's name
     * @return Manager object if manager with given name exists or null if not.
     */
    public Manager findByName(String name);


    public Manager findByEmail(String email);

}
