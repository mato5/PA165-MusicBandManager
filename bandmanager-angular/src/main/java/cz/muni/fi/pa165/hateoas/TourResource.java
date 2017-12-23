package cz.muni.fi.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.pa165.dto.BandDTO;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.dto.TourDTO;
import cz.muni.fi.pa165.entity.Tour;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;



@Relation(value = "tour", collectionRelation = "tours")
public class TourResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;

    private String name;
    private String cityName;
    private Date datetime;
    private BandDTO band;
    private Long managerId;

    public TourResource(TourDTO tourDTO){
        this.dtoId = tourDTO.getId();
        this.name = tourDTO.getName();
        this.cityName = tourDTO.getCityName();
        this.datetime = tourDTO.getDatetime();
        this.band = tourDTO.getBand();
        this.managerId = tourDTO.getManagerId();
    }

    public long getDtoId() {
        return dtoId;
    }

    public void setDtoId(long dtoId) {
        this.dtoId = dtoId;
    }

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

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public BandDTO getBand() {
        return band;
    }

    public void setBand(BandDTO band) {
        this.band = band;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerid) {
        this.managerId = managerId;
    }
}
