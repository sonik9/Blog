package pl.upir.blog.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Vitalii on 22.06.2015.
 */
@Entity
@Table(name = "blg_user_detail", schema = "", catalog = "java_blog")
public class BlgUserDetail implements Serializable{
    private int usrDetId;
    private BlgUser blgUser;
    private int usrId;
    private String usrDetFirstname;
    private String usrDetLastname;
    private Date usrDetBirthdate;
    private int usrDetCountry;
    private int usrDetCity;
    private String usrDetAdres;



    @Id
    @Column(name = "usr_det_id", nullable = false, insertable = true, updatable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getUsrDetId() {
        return usrDetId;
    }

    public void setUsrDetId(int usrDetId) {
        this.usrDetId = usrDetId;
    }

    @Basic
    @Column(name = "usr_id", nullable = false, insertable = true, updatable = true)
    public int getUsrId() {
        return usrId;
    }

    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }

    @Basic
    @Column(name = "usr_det_firstname", nullable = false, insertable = true, updatable = true, length = 20)
    public String getUsrDetFirstname() {
        return usrDetFirstname;
    }

    public void setUsrDetFirstname(String usrDetFirstname) {
        this.usrDetFirstname = usrDetFirstname;
    }

    @Basic
    @Column(name = "usr_det_lastname", nullable = false, insertable = true, updatable = true, length = 20)
    public String getUsrDetLastname() {
        return usrDetLastname;
    }

    public void setUsrDetLastname(String usrDetLastname) {
        this.usrDetLastname = usrDetLastname;
    }

   /* @Basic
    @Column(name = "usr_det_birthdate", nullable = true, insertable = true, updatable = true)
    public Date getUsrDetBirthdate() {
        return usrDetBirthdate;
    }

    public void setUsrDetBirthdate(Date usrDetBirthdate) {
        this.usrDetBirthdate = usrDetBirthdate;
    }


    @Basic
    @Column(name = "usr_det_country", nullable = true, insertable = true, updatable = true)
    public int getUsrDetCountry() {
        return usrDetCountry;
    }

    public void setUsrDetCountry(int usrDetCountry) {
        this.usrDetCountry = usrDetCountry;
    }

    @Basic
    @Column(name = "usr_det_city", nullable = true, insertable = true, updatable = true)
    public int getUsrDetCity() {
        return usrDetCity;
    }

    public void setUsrDetCity(int usrDetCity) {
        this.usrDetCity = usrDetCity;
    }

    @Basic
    @Column(name = "usr_det_adres", nullable = true, insertable = true, updatable = true, length = 100)
    public String getUsrDetAdres() {
        return usrDetAdres;
    }

    public void setUsrDetAdres(String usrDetAdres) {
        this.usrDetAdres = usrDetAdres;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlgUserDetail that = (BlgUserDetail) o;

        if (usrDetId != that.usrDetId) return false;
       // if (usrId != that.usrId) return false;
        if (usrDetCountry != that.usrDetCountry) return false;
        if (usrDetCity != that.usrDetCity) return false;
        if (usrDetFirstname != null ? !usrDetFirstname.equals(that.usrDetFirstname) : that.usrDetFirstname != null)
            return false;
        if (usrDetLastname != null ? !usrDetLastname.equals(that.usrDetLastname) : that.usrDetLastname != null)
            return false;
        if (usrDetBirthdate != null ? !usrDetBirthdate.equals(that.usrDetBirthdate) : that.usrDetBirthdate != null)
            return false;
        if (usrDetAdres != null ? !usrDetAdres.equals(that.usrDetAdres) : that.usrDetAdres != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = usrDetId;
       // result = 31 * result + usrId;
        result = 31 * result + (usrDetFirstname != null ? usrDetFirstname.hashCode() : 0);
        result = 31 * result + (usrDetLastname != null ? usrDetLastname.hashCode() : 0);
        result = 31 * result + (usrDetBirthdate != null ? usrDetBirthdate.hashCode() : 0);
        result = 31 * result + usrDetCountry;
        result = 31 * result + usrDetCity;
        result = 31 * result + (usrDetAdres != null ? usrDetAdres.hashCode() : 0);
        return result;
    }


    @OneToOne
    @JoinColumn(name = "usr_id", insertable = false, updatable = false)
    private BlgUser getBlgUser() {
        return blgUser;
    }

    private void setBlgUser(BlgUser blgUser) {
        this.blgUser = blgUser;
    }

    @Override
    public String toString() {
        return "BlgUserDetail{" +
               // "blgUser=" + blgUser +
                ", usrDetFirstname='" + usrDetFirstname + '\'' +
                ", usrDetLastname='" + usrDetLastname + '\'' +
                '}';
    }
}
