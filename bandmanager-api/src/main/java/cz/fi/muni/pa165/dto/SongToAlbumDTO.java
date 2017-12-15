package cz.fi.muni.pa165.dto;

/**
 * @author Matej Sojak 433294
 */
public class SongToAlbumDTO {

    private Long songId;
    private Long albumId;

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
}
