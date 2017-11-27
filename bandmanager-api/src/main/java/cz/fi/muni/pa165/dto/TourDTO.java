package cz.fi.muni.pa165.dto;

/**
 * @author Alexander Kromka
 */
public class TourDTO {
    private Long id;
    private BandDTO band;
    private ManagerDTO manager;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BandDTO getBand() {
        return band;
    }

    public void setBand(BandDTO band) {
        this.band = band;
    }

    public ManagerDTO getManager() {
        return manager;
    }

    public void setManager(ManagerDTO manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof TourDTO)) return false;

        TourDTO tourDTO = (TourDTO) o;

        if (id != null ? !id.equals(tourDTO.getId()) : tourDTO.getId() != null) return false;
        if (manager != null ? !manager.equals(tourDTO.getManager()) : tourDTO.getManager() != null) return false;
        return band != null ? band.equals(tourDTO.getBand()) : tourDTO.getBand() == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (band != null ? band.hashCode() : 0);
        result = 31 * result + (manager != null ? manager.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TourDTO{" +
                "id=" + id +
                ", band=" + band +
                ", manager=" + manager +
                '}';
    }
}
