package pl.upir.blog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Vitalii on 26/08/2015.
 */
@Entity
@Table(name = "blg_dic_tag", schema = "", catalog = "java_blog")
public class BlgDicTag {
    private int dicTagId;
    private String dicTagName;

    private Set<BlgPost> blgPostSet;

    @Id
    @Column(name = "dic_tag_id", nullable = false, insertable = true, updatable = true)
    public int getDicTagId() {
        return dicTagId;
    }

    public void setDicTagId(int dicTagId) {
        this.dicTagId = dicTagId;
    }

    @Basic
    @Column(name = "dic_tag_name", nullable = false, insertable = true, updatable = true, length = 50)
    public String getDicTagName() {
        return dicTagName;
    }

    public void setDicTagName(String dicTagName) {
        this.dicTagName = dicTagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlgDicTag blgDicTag = (BlgDicTag) o;

        if (dicTagId != blgDicTag.dicTagId) return false;
        if (dicTagName != null ? !dicTagName.equals(blgDicTag.dicTagName) : blgDicTag.dicTagName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dicTagId;
        result = 31 * result + (dicTagName != null ? dicTagName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("dicTagId", dicTagId)
                .add("dicTagName", dicTagName)
                .add("blgPostSet", blgPostSet)
                .toString();
    }

    @JsonBackReference(value = "tag")
    @ManyToMany( mappedBy = "blgDicTagSet")
    public Set<BlgPost> getBlgPostSet() {
        return blgPostSet;
    }

    public void setBlgPostSet(Set<BlgPost> blgPostSet) {
        this.blgPostSet = blgPostSet;
    }
}
