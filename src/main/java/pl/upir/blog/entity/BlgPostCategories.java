package pl.upir.blog.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Vitalii on 26/08/2015.
 */
@Entity
@Table(name = "blg_post_categories", schema = "", catalog = "java_blog")
public class BlgPostCategories {
    private int pstCatId;
    private String pstCatName;
    private byte pstCatEnable;
    private int pstCatPstcount;


    private Set<BlgPost> blgPostSet;

    @Id
    @Column(name = "pst_cat_id", nullable = false, insertable = true, updatable = true)
    public int getPstCatId() {
        return pstCatId;
    }

    public void setPstCatId(int pstCatId) {
        this.pstCatId = pstCatId;
    }

    @Basic
    @Column(name = "pst_cat_name", nullable = false, insertable = true, updatable = true, length = 50)
    public String getPstCatName() {
        return pstCatName;
    }

    public void setPstCatName(String pstCatName) {
        this.pstCatName = pstCatName;
    }

    @Basic
    @Column(name = "pst_cat_enable", nullable = false, insertable = true, updatable = true)
    public byte getPstCatEnable() {
        return pstCatEnable;
    }

    public void setPstCatEnable(byte pstCatEnable) {
        this.pstCatEnable = pstCatEnable;
    }

    @Basic
    @Column(name = "pst_cat_pstcount", nullable = false, insertable = true, updatable = true)
    public int getPstCatPstcount() {
        return pstCatPstcount;
    }

    public void setPstCatPstcount(int pstCatPstcount) {
        this.pstCatPstcount = pstCatPstcount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlgPostCategories that = (BlgPostCategories) o;

        if (pstCatId != that.pstCatId) return false;
        if (pstCatEnable != that.pstCatEnable) return false;
        if (pstCatPstcount != that.pstCatPstcount) return false;
        if (pstCatName != null ? !pstCatName.equals(that.pstCatName) : that.pstCatName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pstCatId;
        result = 31 * result + (pstCatName != null ? pstCatName.hashCode() : 0);
        result = 31 * result + (int) pstCatEnable;
        result = 31 * result + pstCatPstcount;
        return result;
    }
    @OneToMany(mappedBy = "blgPostCategories")
    public Set<BlgPost> getBlgPostSet() {
        return blgPostSet;
    }

    public void setBlgPostSet(Set<BlgPost> blgPostSet) {
        this.blgPostSet = blgPostSet;
    }
}
