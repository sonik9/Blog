package pl.upir.blog.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vitalii on 22.06.2015.
 */
@Entity
@Table(name = "blg_country", schema = "", catalog = "java_blog")
public class BlgCountry implements Serializable{
    private int countryId;
    private String countryName;

    @Id
    @Column(name = "country_id", nullable = false, insertable = true, updatable = true)
    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Basic
    @Column(name = "country_name", nullable = false, insertable = true, updatable = true, length = 50)
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlgCountry that = (BlgCountry) o;

        if (countryId != that.countryId) return false;
        if (countryName != null ? !countryName.equals(that.countryName) : that.countryName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = countryId;
        result = 31 * result + (countryName != null ? countryName.hashCode() : 0);
        return result;
    }
}
