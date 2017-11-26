package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.exceptions.BandManagerServiceException;
import cz.muni.fi.pa165.dao.AlbumDao;
import cz.muni.fi.pa165.dao.SongDao;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Band;
import cz.muni.fi.pa165.entity.Song;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@Service
public class AlbumServiceImpl implements AlbumService {

    @Inject
    private AlbumDao albumDao;

    @Inject
    private SongDao songDao;

    @Override
    public Album findById(Long id) {
        return albumDao.findById(id);
    }

    @Override
    public Album create(Album album) {
        albumDao.create(album);
        return album;
    }

    @Override
    public void delete(Album album) {
        albumDao.delete(album);
    }

    @Override
    public List<Album> findAll() {
        return albumDao.findAll();
    }

    @Override
    public List<Album> findByBand(Band band) {
        return albumDao.findByBand(band);
    }

    @Override
    public Album addSong(Album album, Song song) {
        if (songDao.findById(song.getId()) == null) {
            throw new BandManagerServiceException("Can't add not existing song "
                    + song.getId() + " to album " + album.getId());
        }

        album.getSongs().add(song);
        albumDao.update(album);
        return album;
    }

    @Override
    public Album deleteSong(Album album, Song song) {
        if (album.getSongs().isEmpty()) {
            throw new BandManagerServiceException("Can't delete song "
                    + song.getId() + " from empty album " + album.getId());
        }

        if (album.getSongs().size() == 1) {
            throw new BandManagerServiceException("Album " + album.getId()
                    + " must contain at least one song, skip remove of song"
                    + song.getId());
        }

        if (!album.getSongs().contains(song)) {
            throw new BandManagerServiceException("Album " + album.getId()
                    + " doesn't contains this song, skip remove of song"
                    + song.getId());
        }

        album.getSongs().remove(song);
        albumDao.update(album);
        return album;
    }
}
