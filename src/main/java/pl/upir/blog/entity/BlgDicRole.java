package pl.upir.blog.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vitalii on 01.07.2015.
 */
@Entity
@Table(name = "blg_dic_role", schema = "", catalog = "java_blog")
public class BlgDicRole implements Serializable {
    private int roleId;
    private String roleName;


    private Set<BlgUser> blgUserSet = new HashSet<BlgUser>();


    @Id
    @Column(name = "role_id", nullable = false, insertable = true, updatable = true)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "role_name", nullable = false, insertable = true, updatable = true, length = 20)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlgDicRole that = (BlgDicRole) o;

        if (roleId != that.roleId) return false;
        if (roleName != null ? !roleName.equals(that.roleName) : that.roleName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        return result;
    }
    @ManyToMany( mappedBy = "blgUserRoleSet", fetch = FetchType.EAGER)
    private Set<BlgUser> getBlgUserSet() {
        return blgUserSet;
    }

    public void setBlgUserSet(Set<BlgUser> blgUserSet) {
        this.blgUserSet = blgUserSet;
    }
}
