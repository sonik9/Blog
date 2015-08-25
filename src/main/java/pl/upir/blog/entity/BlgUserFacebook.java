package pl.upir.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vitalii on 12.08.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "blg_user_facebook", schema = "", catalog = "java_blog")
public class BlgUserFacebook  implements Serializable{
    @JsonProperty("id")
    private long usrFbId;
    @JsonProperty("verified")
    private boolean usrFbVerified;

    private String usrFbAccestoken;


    @JsonProperty("email")
    private String email;
    @JsonProperty("gender")
    private Gender gender;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;

    private BlgUser blgUser;

    @OneToOne
    @JoinColumn(name = "usr_id", nullable = false, insertable = true, updatable = true)
    public BlgUser getBlgUser() {
        return blgUser;
    }

    public void setBlgUser(BlgUser blgUser) {
        this.blgUser = blgUser;
    }


    @Id
    @Column(name = "usr_fb_id", nullable = false, insertable = true, updatable = true)
    public long getUsrFbId() {
        return usrFbId;
    }

    public void setUsrFbId(long usrFbId) {
        this.usrFbId = usrFbId;
    }


    @Basic
    @Column(name = "usr_fb_verified", nullable = false, insertable = true, updatable = true)
    public boolean isUsrFbVerified() {
        return usrFbVerified;
    }

    public void setUsrFbVerified(boolean usrFbVerified) {
        this.usrFbVerified = usrFbVerified;
    }

    @Basic
    @Column(name = "usr_fb_accesstoken", nullable = true, insertable = true, updatable = true)
    public String getUsrFbAccesstoken() {
        return usrFbAccestoken;
    }

    public void setUsrFbAccesstoken(String usrFbAccestoken) {
        this.usrFbAccestoken = usrFbAccestoken;
    }

    @Transient
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Transient
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    @Transient
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Transient
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
