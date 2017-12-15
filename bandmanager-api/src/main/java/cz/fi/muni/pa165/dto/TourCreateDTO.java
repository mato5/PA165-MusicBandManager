package cz.fi.muni.pa165.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

public class TourCreateDTO {

    @NotNull
    @Size(min = 5, max = 50)
    private String name;

    @NotNull
    @Size(min = 5, max = 50)
    private String cityName;

    @NotNull
    private Long bandId;

    @NotNull
    private Long managerId;

    @NotNull
    private Date datetime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getBandId() {
        return bandId;
    }

    public void setBandId(Long bandId) {
        this.bandId = bandId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
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
        if (o == null || !(o instanceof TourCreateDTO)) return false;

        TourCreateDTO that = (TourCreateDTO) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null) return false;
        if (bandId != null ? !bandId.equals(that.bandId) : that.bandId != null) return false;
        if (managerId != null ? !managerId.equals(that.managerId) : that.managerId != null) return false;
        return datetime != null ? datetime.equals(that.datetime) : that.datetime == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (bandId != null ? bandId.hashCode() : 0);
        result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TourCreateDTO{" +
                "name='" + name + '\'' +
                ", cityName='" + cityName + '\'' +
                ", bandId=" + bandId +
                ", managerId=" + managerId +
                ", datetime=" + datetime +
                '}';
    }
}
