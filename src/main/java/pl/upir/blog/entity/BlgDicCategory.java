package pl.upir.blog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Vitalii on 1/09/2015.
 */
@Entity
@Table(name = "blg_dic_category", schema = "", catalog = "java_blog")
public class BlgDicCategory {
    private int dicCatId;
    private String dicCatName;
    private byte dicCatEnable;
    private int dicCatPstcount;

    Set<BlgPost> blgPostSet;

    public BlgDicCategory() {
    }

    public BlgDicCategory(String dicCatName) {
        this.dicCatName = dicCatName;
    }

    @Id
    @Column(name = "dic_cat_id", nullable = false, insertable = true, updatable = true)
    public int getDicCatId() {
        return dicCatId;
    }

    public void setDicCatId(int dicCatId) {
        this.dicCatId = dicCatId;
    }

    @Basic
    @Column(name = "dic_cat_name", nullable = false, insertable = true, updatable = true, length = 50)
    public String getDicCatName() {
        return dicCatName;
    }

    public void setDicCatName(String dicCatName) {
        this.dicCatName = dicCatName;
    }

    @Basic
    @Column(name = "dic_cat_enable", nullable = false, insertable = true, updatable = true)
    public byte getDicCatEnable() {
        return dicCatEnable;
    }

    public void setDicCatEnable(byte dicCatEnable) {
        this.dicCatEnable = dicCatEnable;
    }

    @Basic
    @Column(name = "dic_cat_pstcount", nullable = false, insertable = true, updatable = true)
    public int getDicCatPstcount() {
        return dicCatPstcount;
    }

    public void setDicCatPstcount(int dicCatPstcount) {
        this.dicCatPstcount = dicCatPstcount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlgDicCategory that = (BlgDicCategory) o;

        if (dicCatId != that.dicCatId) return false;
        if (dicCatEnable != that.dicCatEnable) return false;
        if (dicCatPstcount != that.dicCatPstcount) return false;
        if (dicCatName != null ? !dicCatName.equals(that.dicCatName) : that.dicCatName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dicCatId;
        result = 31 * result + (dicCatName != null ? dicCatName.hashCode() : 0);
        result = 31 * result + (int) dicCatEnable;
        result = 31 * result + dicCatPstcount;
        return result;
    }

    @JsonBackReference(value = "cat")
    @ManyToMany( mappedBy = "blgDicCategorySet",cascade = CascadeType.MERGE)
    public Set<BlgPost> getBlgPostSet() {
        return blgPostSet;
    }

    public void setBlgPostSet(Set<BlgPost> blgPostSet) {
        this.blgPostSet = blgPostSet;
    }
}
