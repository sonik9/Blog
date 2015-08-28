package pl.upir.blog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vitalii on 26/08/2015.
 */
@Entity
@Table(name = "blg_post", schema = "", catalog = "java_blog")
public class BlgPost {
    private int pstId;
    private String pstTitle;
    private String pstDocument;
    private String pstTitleImage;
    private Timestamp pstTimeCreate;
    private Timestamp pstTimeModify;
    private byte pstEnable;
    private int pstCountLike;
    private int pstCountDislike;
    private int pstCountComm;
    private String pstUrl;


    private BlgPostCategories blgPostCategories;

    private BlgUser blgUser;

    private Set<BlgDicTag> blgDicTagSet = new HashSet<BlgDicTag>();


    @JsonIgnore
    @Id
    @Column(name = "pst_id", nullable = false, insertable = true, updatable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getPstId() {
        return pstId;
    }

    public void setPstId(int pstId) {
        this.pstId = pstId;
    }

    @Basic
    @Column(name = "pst_title", nullable = false, insertable = true, updatable = true, length = 255)
    public String getPstTitle() {
        return pstTitle;
    }

    public void setPstTitle(String pstTitle) {
        this.pstTitle = pstTitle;
    }

    @Basic
    @Column(name = "pst_document", nullable = false, insertable = true, updatable = true)
    public String getPstDocument() {
        return pstDocument;
    }

    public void setPstDocument(String pstDocument) {
        this.pstDocument = pstDocument;
    }

    @Basic
    @Column(name = "pst_title_image", nullable = true, insertable = true, updatable = true, length = 255)
    public String getPstTitleImage() {
        return pstTitleImage;
    }

    public void setPstTitleImage(String pstTitleImage) {
        this.pstTitleImage = pstTitleImage;
    }

    @Basic
    @Column(name = "pst_time_create", nullable = false, insertable = true, updatable = true)
    public Timestamp getPstTimeCreate() {
        return pstTimeCreate;
    }

    public void setPstTimeCreate(Timestamp pstTimeCreate) {
        this.pstTimeCreate = pstTimeCreate;
    }

    @Basic
    @Column(name = "pst_time_modify", nullable = false, insertable = true, updatable = true)
    public Timestamp getPstTimeModify() {
        return pstTimeModify;
    }

    public void setPstTimeModify(Timestamp pstTimeModify) {
        this.pstTimeModify = pstTimeModify;
    }

    @JsonIgnore
    @Basic
    @Column(name = "pst_enable", nullable = false, insertable = true, updatable = true)
    public byte getPstEnable() {
        return pstEnable;
    }

    public void setPstEnable(byte pstEnable) {
        this.pstEnable = pstEnable;
    }

    @Basic
    @Column(name = "pst_count_like", nullable = false, insertable = true, updatable = true)
    public int getPstCountLike() {
        return pstCountLike;
    }

    public void setPstCountLike(int pstCountLike) {
        this.pstCountLike = pstCountLike;
    }

    @Basic
    @Column(name = "pst_count_dislike", nullable = false, insertable = true, updatable = true)
    public int getPstCountDislike() {
        return pstCountDislike;
    }

    public void setPstCountDislike(int pstCountDislike) {
        this.pstCountDislike = pstCountDislike;
    }

    @Basic
    @Column(name = "pst_count_comm", nullable = false, insertable = true, updatable = true)
    public int getPstCountComm() {
        return pstCountComm;
    }

    public void setPstCountComm(int pstCountComm) {
        this.pstCountComm = pstCountComm;
    }

    @Basic
    @Column(name = "pst_url", nullable = false, insertable = true, updatable = true, length = 255)
    public String getPstUrl() {
        return pstUrl;
    }

    public void setPstUrl(String pstUrl) {
        this.pstUrl = pstUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlgPost blgPost = (BlgPost) o;

        if (pstId != blgPost.pstId) return false;
        if (pstEnable != blgPost.pstEnable) return false;
        if (pstCountLike != blgPost.pstCountLike) return false;
        if (pstCountDislike != blgPost.pstCountDislike) return false;
        if (pstCountComm != blgPost.pstCountComm) return false;
        if (pstTitle != null ? !pstTitle.equals(blgPost.pstTitle) : blgPost.pstTitle != null) return false;
        if (pstTitleImage != null ? !pstTitleImage.equals(blgPost.pstTitleImage) : blgPost.pstTitleImage != null)
            return false;
        if (pstTimeCreate != null ? !pstTimeCreate.equals(blgPost.pstTimeCreate) : blgPost.pstTimeCreate != null)
            return false;
        if (pstTimeModify != null ? !pstTimeModify.equals(blgPost.pstTimeModify) : blgPost.pstTimeModify != null)
            return false;
        if (pstUrl != null ? !pstUrl.equals(blgPost.pstUrl) : blgPost.pstUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pstId;
        result = 31 * result + (pstTitle != null ? pstTitle.hashCode() : 0);
        result = 31 * result + (pstTitleImage != null ? pstTitleImage.hashCode() : 0);
        result = 31 * result + (pstTimeCreate != null ? pstTimeCreate.hashCode() : 0);
        result = 31 * result + (pstTimeModify != null ? pstTimeModify.hashCode() : 0);
        result = 31 * result + (int) pstEnable;
        result = 31 * result + pstCountLike;
        result = 31 * result + pstCountDislike;
        result = 31 * result + pstCountComm;
        result = 31 * result + (pstUrl != null ? pstUrl.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "BlgPost{" +
                "pstId=" + pstId +
                ", pstTitle='" + pstTitle + '\'' +
                ", pstDocument='" + pstDocument + '\'' +
                ", pstTitleImage='" + pstTitleImage + '\'' +
                ", pstTimeCreate=" + pstTimeCreate +
                ", pstTimeModify=" + pstTimeModify +
                ", pstEnable=" + pstEnable +
                ", pstCountLike=" + pstCountLike +
                ", pstCountDislike=" + pstCountDislike +
                ", pstCountComm=" + pstCountComm +
                ", pstUrl='" + pstUrl + '\'' +
                ", blgPostCategories=" + blgPostCategories +
                ", blgUser=" + blgUser +
                ", blgDicTagSet=" + blgDicTagSet +
                '}';
    }

    @JsonBackReference(value = "cat")
    @ManyToOne()
    @JoinColumn(name = "pst_cat_id",nullable = false)
    public BlgPostCategories getBlgPostCategories() {
        return blgPostCategories;
    }

    public void setBlgPostCategories(BlgPostCategories blgPostCategories) {
        this.blgPostCategories = blgPostCategories;
    }

    @JsonBackReference(value = "post")
    @ManyToOne
    @JoinColumn(name = "usr_id", nullable = false)
    public BlgUser getBlgUser() {
        return blgUser;
    }

    public void setBlgUser(BlgUser blgUser) {
        this.blgUser = blgUser;
    }

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "blg_post_tag",
            joinColumns = {
                    @JoinColumn(name = "pst_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "dic_tag_id")
            })
    public Set<BlgDicTag> getBlgDicTagSet() {
        return blgDicTagSet;
    }

    public void setBlgDicTagSet(Set<BlgDicTag> blgDicTagSet) {
        this.blgDicTagSet = blgDicTagSet;
    }
}
