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
    private Long managerId;

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

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
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
        return managerId != null ? managerId.equals(tourDTO.managerId) : tourDTO.managerId == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (band != null ? band.hashCode() : 0);
        result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TourDTO{" +
                "name='" + name + '\'' +
                ", cityName='" + cityName + '\'' +
                ", datetime=" + datetime +
                ", id=" + id +
                ", managerId=" + managerId +
                '}';
    }
}
