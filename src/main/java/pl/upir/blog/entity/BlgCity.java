package pl.upir.blog.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vitalii on 22.06.2015.
 */
@Entity
@Table(name = "blg_city", schema = "", catalog = "java_blog")
public class BlgCity implements Serializable{
    private int cityId;
    private String cityName;
    private Integer countryId;

    @Id
    @Column(name = "city_id", nullable = false, insertable = true, updatable = true)
    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Basic
    @Column(name = "city_name", nullable = true, insertable = true, updatable = true, length = 50)
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Basic
    @Column(name = "country_id", nullable = true, insertable = true, updatable = true)
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlgCity blgCity = (BlgCity) o;

        if (cityId != blgCity.cityId) return false;
        if (cityName != null ? !cityName.equals(blgCity.cityName) : blgCity.cityName != null) return false;
        if (countryId != null ? !countryId.equals(blgCity.countryId) : blgCity.countryId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cityId;
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (countryId != null ? countryId.hashCode() : 0);
        
        return result;
    }

}
