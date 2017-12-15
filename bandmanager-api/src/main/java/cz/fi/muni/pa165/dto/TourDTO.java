package cz.fi.muni.pa165.dto;

import java.util.Date;

/**
 * @author Alexander Kromka
 */
public class TourDTO {

    private String name;
    private String cityName;
    private Date datetime;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof TourDTO)) return false;

        TourDTO tourDTO = (TourDTO) o;

        if (name != null ? !name.equals(tourDTO.name) : tourDTO.name != null) return false;
        if (cityName != null ? !cityName.equals(tourDTO.cityName) : tourDTO.cityName != null) return false;
        if (datetime != null ? !datetime.equals(tourDTO.datetime) : tourDTO.datetime != null) return false;
        if (id != null ? !id.equals(tourDTO.id) : tourDTO.id != null) return false;
        if (band != null ? !band.equals(tourDTO.band) : tourDTO.band != null) return false;
        return manager != null ? manager.equals(tourDTO.manager) : tourDTO.manager == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
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
