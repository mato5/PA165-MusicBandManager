package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.exceptions.BandManagerServiceException;
import cz.muni.fi.pa165.dao.BandDao;
import cz.muni.fi.pa165.dao.SongDao;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.service.SongService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@Service
public class SongServiceImpl implements SongService {

    @Inject
    private SongDao songDao;

    @Inject
    private BandDao bandDao;

    @Override
    public Song findById(Long id) {
        return songDao.findById(id);
    }

    @Override
    public List<Song> findAll() {
        return songDao.findAll();
    }

    @Override
    public Song createSong(Song song) {
        songDao.create(song);
        return song;
    }

    @Override
    public void changeDuration(Song song, Long duration) {
        if (duration <= 0) {
            throw new BandManagerServiceException(
                    "Duration of song must be positive, song "
                            + song.getId() + ", new duration: "
                            + duration);
        }
        song.setDuration(duration);
        songDao.update(song);
    }

    @Override
    public List<Song> findByBand(Band band) {
        return songDao.findByBand(band);
    }

    @Override
    public List<Song> findByName(String name) {
        return songDao.findByName(name);
    }

    @Override
    public void changeBand(Song song, Band band) {
        if (band == null) {
            throw new BandManagerServiceException(
                    "Can't associate null Band to song"
                            + song.getId());

        }
        if (bandDao.findById(band.getId()) == null) {
            throw new BandManagerServiceException(
                    "Band with id " + band.getId()
                            + " wasn't found. Can't associate it to song"
                            + song.getId());

        }

        songDao.update(song);
    }

    @Override
    public void deleteSong(Song song) {
        songDao.delete(song);
    }
}
