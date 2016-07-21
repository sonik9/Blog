package pl.upir.blog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Vitalii on 26/08/2015.
 */
@Entity
/*@SqlResultSetMappings({
        @SqlResultSetMapping(name = "SqlResultSetMapping.count", columns = @ColumnResult(name = "cnt"))
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "BlgPost.findByCat",
                resultClass = BlgPost.class,
                query = "SELECT * FROM blg_post p" +
                        "JOIN blg_post_category bpc ON(bpc.pst_id = p.pst_id)" +
                        "JOIN blg_dic_category bdc ON(bdc.dic_cat_id = bpc.dic_cat_id)" +
                        "WHERE bdc.dic_cat_name=\"Administration\""
        ),
        @NamedNativeQuery(
                name = "BlgPost.findByCat.count",
                resultSetMapping = "SqlResultSetMapping.count",
                query = "SELECT count(*) as cnt FROM blg_post p" +
                        "JOIN blg_post_category bpc ON(bpc.pst_id = p.pst_id)" +
                        "JOIN blg_dic_category bdc ON(bdc.dic_cat_id = bpc.dic_cat_id)" +
                        "WHERE bdc.dic_cat_name=\"Administration\""
        )
})*/

@Table(name = "blg_post", schema = "", catalog = "java_blog")
public class BlgPost {
    private int pstId;
    private String pstTitle;
    private String pstDocument;
    private String pstDocumentShort;
    private String pstTitleImage;
    private Timestamp pstTimeCreate;
    private Timestamp pstTimeModify;
    private boolean pstEnable;
    private int pstCountLike;
    private int pstCountDislike;
    private int pstCountComm;
    private String pstUrl;

    private Set<BlgDicCategory> blgDicCategorySet=new HashSet<>();

    private Set<BlgUser> blgUserSet= new HashSet<>();

    private Set<BlgDicTag> blgDicTagSet = new HashSet<BlgDicTag>();
    //private List<BlgDicTag> blgDicTagSet = new ArrayList<>();

    public MultipartFile file;


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

    @Size(min = 20, max = 255, message = "{validation.pstTitle.Size.message}")
    @NotEmpty(message = "{validation.pstTitle.NotEmpty.message}")
    @Basic
    @Column(name = "pst_title", nullable = false, insertable = true, updatable = true, length = 255)
    public String getPstTitle() {
        return pstTitle;
    }

    public void setPstTitle(String pstTitle) {
        this.pstTitle = pstTitle;
    }

    @Size(min = 200, max = 100000, message = "{validation.pstDocument.Size.message}")
    @NotEmpty(message = "{validation.pstDocument.NotEmpty.message}")
    @Basic
    @Column(name = "pst_document", nullable = false, insertable = true, updatable = true)
    public String getPstDocument() {
        return pstDocument;
    }

    public void setPstDocument(String pstDocument) {
        this.pstDocument = pstDocument;
    }

    @Basic
    @Column(name = "pst_title_image", nullable = true, insertable = true, updatable = true)
    public String getPstTitleImage() {
        return pstTitleImage;
    }

    public void setPstTitleImage(String pstTitleImage) {
        this.pstTitleImage = pstTitleImage;
    }

    @Basic
    @Column(name = "pst_time_create", nullable = false, insertable = false, updatable = false)
    public Timestamp getPstTimeCreate() {
        return pstTimeCreate;
    }

    public void setPstTimeCreate(Timestamp pstTimeCreate) {
        this.pstTimeCreate = pstTimeCreate;
    }

    @Basic
    @Column(name = "pst_time_modify", nullable = false, insertable = false, updatable = false)
    public Timestamp getPstTimeModify() {
        return pstTimeModify;
    }

    public void setPstTimeModify(Timestamp pstTimeModify) {
        this.pstTimeModify = pstTimeModify;
    }

    @JsonIgnore
    @Basic
    @Column(name = "pst_enable", nullable = false, insertable = true, updatable = true)
    public boolean getPstEnable() {
        return pstEnable;
    }

    public void setPstEnable(boolean pstEnable) {
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

    @Basic
    @Column(name = "pst_document_short",nullable = false, insertable = true, updatable = true)
    public String getPstDocumentShort() {
        return pstDocumentShort;
    }

    public void setPstDocumentShort(String pstDocumentShort) {
        this.pstDocumentShort = pstDocumentShort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlgPost)) return false;
        BlgPost blgPost = (BlgPost) o;
        return java.util.Objects.equals(pstId, blgPost.pstId) &&
                java.util.Objects.equals(pstEnable, blgPost.pstEnable) &&
                java.util.Objects.equals(pstCountLike, blgPost.pstCountLike) &&
                java.util.Objects.equals(pstCountDislike, blgPost.pstCountDislike) &&
                java.util.Objects.equals(pstCountComm, blgPost.pstCountComm) &&
                java.util.Objects.equals(pstTitle, blgPost.pstTitle) &&
                java.util.Objects.equals(pstDocument, blgPost.pstDocument) &&
                java.util.Objects.equals(pstDocumentShort, blgPost.pstDocumentShort) &&
                java.util.Objects.equals(pstTitleImage, blgPost.pstTitleImage) &&
                java.util.Objects.equals(pstTimeCreate, blgPost.pstTimeCreate) &&
                java.util.Objects.equals(pstTimeModify, blgPost.pstTimeModify) &&
                java.util.Objects.equals(pstUrl, blgPost.pstUrl) &&
                java.util.Objects.equals(blgDicCategorySet, blgPost.blgDicCategorySet) &&
                java.util.Objects.equals(blgUserSet, blgPost.blgUserSet) &&
                java.util.Objects.equals(blgDicTagSet, blgPost.blgDicTagSet) &&
                java.util.Objects.equals(file, blgPost.file);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(pstId, pstTitle, pstDocument, pstDocumentShort, pstTitleImage, pstTimeCreate, pstTimeModify, pstEnable, pstCountLike, pstCountDislike, pstCountComm, pstUrl, blgDicCategorySet, blgUserSet, blgDicTagSet);
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
                ", blgPostCategorySet=" + blgDicCategorySet +
                ", blgUserSet=" + blgUserSet +
                ", blgDicTagSet=" + blgDicTagSet +
                '}';
    }

    /*

    @JsonBackReference(value = "post")
    @ManyToOne(optional = false)
    @JoinColumn(name = "usr_id", nullable = false,insertable = false, updatable = false)
    public BlgUser getBlgUser() {
        return blgUser;
    }

    public void setBlgUser(BlgUser blgUser) {
        this.blgUser = blgUser;
    }
*/
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "blg_post_category",
            joinColumns = {
                    @JoinColumn(name = "pst_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "dic_cat_id")
            })
    public Set<BlgDicCategory> getBlgDicCategorySet() {
        return blgDicCategorySet;
    }

    public void setBlgDicCategorySet(Set<BlgDicCategory> blgDicCategorySet) {
        this.blgDicCategorySet = blgDicCategorySet;
    }
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "blg_post_user",
            joinColumns = {
                    @JoinColumn(name = "pst_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "usr_id")
            })
    public Set<BlgUser> getBlgUserSet() {
        return blgUserSet;
    }

    public void setBlgUserSet(Set<BlgUser> blgUserSet) {
        this.blgUserSet = blgUserSet;
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

    @Transient
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
