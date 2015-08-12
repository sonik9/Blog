package pl.upir.blog.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vitalii on 01.07.2015.
 */
@Entity
@Table(name = "blg_user_role", schema = "", catalog = "java_blog" )
public class BlgUserRole implements Serializable {

    @Id
    private Integer id;
    /*@Id
    @Column(name = "usr_role_id", nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usrRoleId;
*/
    private int usrId;
    private int roleId;
    //@ManyToMany(mappedBy = "blgUserRoleSet")
    //private BlgUser blgUser;
    //private Set<BlgDicRole> blgDicRoleSet = new HashSet<BlgDicRole>();



    @Basic
    @Column(name = "usr_id", nullable = false, insertable = true, updatable = true)
    public int getUsrId() {
        return usrId;
    }

    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }

    @Basic
    @Column(name = "role_id", nullable = false, insertable = true, updatable = true)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /*public void setUsrRoleId(int usrRoleId) {
        this.usrRoleId = usrRoleId;
    }

    public int getUsrRoleId() {
        return usrRoleId;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlgUserRole that = (BlgUserRole) o;

        if (usrId != that.usrId) return false;
        if (roleId != that.roleId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = usrId;
        result = 31 * result + roleId;
        return result;
    }


   /* @OneToOne
    @JoinColumn(name = "usr_id", insertable = false, updatable = false)
    public BlgUser getBlgUser() {
        return blgUser;
    }

    public void setBlgUser(BlgUser blgUser) {
        this.blgUser = blgUser;
    }*/

//    @ManyToMany(mappedBy = "roleName")
//    public Set<BlgDicRole> getBlgDicRoleSet() {
//        return blgDicRoleSet;
//    }
//
//    public void setBlgDicRoleSet(Set<BlgDicRole> blgDicRoleSet) {
//        this.blgDicRoleSet = blgDicRoleSet;
//    }
}
