package pl.upir.blog.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

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

    //private int usrId;
    private String usrDetFirstname;
    private String usrDetLastname;
    private Date usrDetBirthdate;
    private int usrDetCountry;
    private int usrDetCity;
    private String usrDetAdres;
    private String usrPhotoLink;
    private String usrFbPhotoLink;
    private Gender usrGender;



    @Id
    @Column(name = "usr_det_id", nullable = false, insertable = true, updatable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getUsrDetId() {
        return usrDetId;
    }

    public void setUsrDetId(int usrDetId) {
        this.usrDetId = usrDetId;
    }

  /*  @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "blgUser"))
    @GeneratedValue(generator = "generator")*/
    @OneToOne
    @JoinColumn(name = "usr_id", nullable = false, insertable = true, updatable = true)
    public BlgUser getBlgUser() {
        return blgUser;
    }

    public void setBlgUser(BlgUser blgUser) {
        this.blgUser = blgUser;
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
   @Basic
   @Column(name = "usr_photo_link", nullable = true, insertable = true, updatable = true)
    public String getUsrPhotoLink() {
        return usrPhotoLink;
    }

    public void setUsrPhotoLink(String usrPhotoLink) {
        this.usrPhotoLink = usrPhotoLink;
    }

    @Basic
    @Column(name = "usr_gender", nullable = true, insertable = true, updatable = true, length = 7)
    public Gender getUsrGender() {
        return usrGender;
    }

    public void setUsrGender(Gender usrGender) {
        this.usrGender = usrGender;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlgUserDetail that = (BlgUserDetail) o;

        if (usrDetId != that.usrDetId) return false;
        if (usrDetCountry != that.usrDetCountry) return false;
        if (usrDetCity != that.usrDetCity) return false;
        if (!blgUser.equals(that.blgUser)) return false;
        if (!usrDetFirstname.equals(that.usrDetFirstname)) return false;
        if (!usrDetLastname.equals(that.usrDetLastname)) return false;
        if (usrDetBirthdate != null ? !usrDetBirthdate.equals(that.usrDetBirthdate) : that.usrDetBirthdate != null)
            return false;
        if (usrDetAdres != null ? !usrDetAdres.equals(that.usrDetAdres) : that.usrDetAdres != null) return false;
        if (usrPhotoLink != null ? !usrPhotoLink.equals(that.usrPhotoLink) : that.usrPhotoLink != null) return false;
        if (usrFbPhotoLink != null ? !usrFbPhotoLink.equals(that.usrFbPhotoLink) : that.usrFbPhotoLink != null)
            return false;
        return usrGender == that.usrGender;

    }

    @Override
    public int hashCode() {
        int result = usrDetId;
        result = 31 * result + blgUser.hashCode();
        result = 31 * result + usrDetFirstname.hashCode();
        result = 31 * result + usrDetLastname.hashCode();
        result = 31 * result + (usrDetBirthdate != null ? usrDetBirthdate.hashCode() : 0);
        result = 31 * result + usrDetCountry;
        result = 31 * result + usrDetCity;
        result = 31 * result + (usrDetAdres != null ? usrDetAdres.hashCode() : 0);
        result = 31 * result + (usrPhotoLink != null ? usrPhotoLink.hashCode() : 0);
        result = 31 * result + (usrFbPhotoLink != null ? usrFbPhotoLink.hashCode() : 0);
        result = 31 * result + (usrGender != null ? usrGender.hashCode() : 0);
        return result;
    }
}
