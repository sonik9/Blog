package pl.upir.blog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vitalii on 22.06.2015.
 */
@Entity
@Table(name = "blg_user", schema = "", catalog = "java_blog")
public class BlgUser implements Serializable {
    private static final long serialVersionUID=1L;
    private int usrId;

    private String usrLogin;
    private String usrPassword;
    private Timestamp usrDateTimeChange;
    private BlgUserDetail blgUserDetail;
    private BlgUserFacebook blgUserFacebook;
    private Set<BlgUserMail> blgUserMailSet;

    private Set<BlgDicRole> blgDicRoleSet = new HashSet<BlgDicRole>();
    //private BlgUserRole blgUserRole;

    private Set<BlgPost> blgPostSet;


    @Id
    @Column(name = "usr_id", nullable = false, insertable = true, updatable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getUsrId() {
        return usrId;
    }

    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }

    @Basic
    @Column(name = "usr_login", nullable = false, insertable = true, updatable = true, length = 30)
    @Size(min = 4, max = 30, message = "{validation.usrlogin.Size.message}")
    @NotEmpty(message = "{validation.usrlogin.NotEmpty.message}")
    public String getUsrLogin() {
        return usrLogin;
    }


    public void setUsrLogin(String usrLogin) {
        this.usrLogin = usrLogin;
    }

    @Basic
    @Column(name = "usr_password", nullable = false, insertable = true, updatable = true, length = 100)
    @Size(min = 4, message = "{validation.usrpassword.Size.message}")
    @NotEmpty(message = "{validation.usrpassword.NotEmpty.message}")
    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    @Basic
    @Column(name = "usr_dateTimeChange", nullable = false, insertable = false, updatable = false)
    public Timestamp getUsrDateTimeChange() {
        return usrDateTimeChange;
    }

    public void setUsrDateTimeChange(Timestamp usrDateTimeChange) {
        this.usrDateTimeChange = usrDateTimeChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlgUser blgUser = (BlgUser) o;

        if (usrId != blgUser.usrId) return false;
        if (usrLogin != null ? !usrLogin.equals(blgUser.usrLogin) : blgUser.usrLogin != null) return false;
        if (usrPassword != null ? !usrPassword.equals(blgUser.usrPassword) : blgUser.usrPassword != null) return false;
        if (usrDateTimeChange != null ? !usrDateTimeChange.equals(blgUser.usrDateTimeChange) : blgUser.usrDateTimeChange != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = usrId;
        result = 31 * result + (usrLogin != null ? usrLogin.hashCode() : 0);
        result = 31 * result + (usrPassword != null ? usrPassword.hashCode() : 0);
        result = 31 * result + (usrDateTimeChange != null ? usrDateTimeChange.hashCode() : 0);
        return result;
    }
    @JsonBackReference(value = "detail")
    @OneToOne(mappedBy = "blgUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public BlgUserDetail getBlgUserDetail() {
        return blgUserDetail;
    }

    public void setBlgUserDetail(BlgUserDetail getBlgUserDetail) {
        this.blgUserDetail = getBlgUserDetail;
    }
    //@JsonManagedReference(value="user")
    @JsonBackReference(value = "fb")
    @OneToOne(mappedBy = "blgUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public BlgUserFacebook getBlgUserFacebook() {
        return blgUserFacebook;
    }

    public void setBlgUserFacebook(BlgUserFacebook blgUserFacebook) {
        this.blgUserFacebook = blgUserFacebook;
    }

    /*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "blgUser")
    public Set<BlgUserMail> getBlgUserMailSet() {
        return blgUserMailSet;
    }

    public void setBlgUserMailSet(Set<BlgUserMail> blgUserMailSet) {
        this.blgUserMailSet = blgUserMailSet;
    }*/
    @JsonBackReference(value = "role")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "blg_user_role",
            joinColumns = {
                    @JoinColumn(name = "usr_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")
            })
    public Set<BlgDicRole> getBlgUserRoleSet() {
        return blgDicRoleSet;
    }

    public void setBlgUserRoleSet(Set<BlgDicRole> blgDicRoleSet) {
        this.blgDicRoleSet = blgDicRoleSet;
    }

    @JsonBackReference(value = "usr")
    @ManyToMany( mappedBy = "blgUserSet")
    public Set<BlgPost> getBlgPostSet() {
        return blgPostSet;
    }

    public void setBlgPostSet(Set<BlgPost> blgPostSet) {
        this.blgPostSet = blgPostSet;
    }
}
