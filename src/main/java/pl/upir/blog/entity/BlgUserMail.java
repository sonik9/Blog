package pl.upir.blog.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vitalii on 22.06.2015.
 */
@Entity
@Table(name = "blg_user_mail", schema = "", catalog = "java_blog")
public class BlgUserMail implements Serializable{
    private int usrMailId;
    private int usrId;
    private String usrMail;
    private String usrMailDomain;

    @Id
    @Column(name = "usr_mail_id", nullable = false, insertable = true, updatable = true)
    public int getUsrMailId() {
        return usrMailId;
    }

    public void setUsrMailId(int usrMailId) {
        this.usrMailId = usrMailId;
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
    @Column(name = "usr_mail", nullable = false, insertable = true, updatable = true, length = 50)
    public String getUsrMail() {
        return usrMail;
    }

    public void setUsrMail(String usrMail) {
        this.usrMail = usrMail;
    }

    @Basic
    @Column(name = "usr_mail_domain", nullable = false, insertable = true, updatable = true, length = 20)
    public String getUsrMailDomain() {
        return usrMailDomain;
    }

    public void setUsrMailDomain(String usrMailDomain) {
        this.usrMailDomain = usrMailDomain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlgUserMail that = (BlgUserMail) o;

        if (usrMailId != that.usrMailId) return false;
        if (usrId != that.usrId) return false;
        if (usrMail != null ? !usrMail.equals(that.usrMail) : that.usrMail != null) return false;
        if (usrMailDomain != null ? !usrMailDomain.equals(that.usrMailDomain) : that.usrMailDomain != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = usrMailId;
        result = 31 * result + usrId;
        result = 31 * result + (usrMail != null ? usrMail.hashCode() : 0);
        result = 31 * result + (usrMailDomain != null ? usrMailDomain.hashCode() : 0);
        return result;
    }
}
