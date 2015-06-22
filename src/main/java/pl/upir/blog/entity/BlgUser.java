package pl.upir.blog.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Vitalii on 22.06.2015.
 */
@Entity
@Table(name = "blg_user", schema = "", catalog = "java_blog")
public class BlgUser implements Serializable {
    private int usrId;
    private String usrLogin;
    private String usrPassword;
    private Timestamp usrDateTimeChange;

    @Id
    @Column(name = "usr_id", nullable = false, insertable = true, updatable = true)
    public int getUsrId() {
        return usrId;
    }

    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }

    @Basic
    @Column(name = "usr_login", nullable = false, insertable = true, updatable = true, length = 20)
    public String getUsrLogin() {
        return usrLogin;
    }

    public void setUsrLogin(String usrLogin) {
        this.usrLogin = usrLogin;
    }

    @Basic
    @Column(name = "usr_password", nullable = false, insertable = true, updatable = true, length = 20)
    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    @Basic
    @Column(name = "usr_dateTimeChange", nullable = false, insertable = true, updatable = true)
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
}
