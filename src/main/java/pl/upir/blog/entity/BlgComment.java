package pl.upir.blog.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created by Vitalii on 26/08/2015.
 */
@Entity
@Table(name = "blg_comment", schema = "", catalog = "java_blog")
public class BlgComment {
    private int commId;
    private byte[] commDocument;
    private Timestamp commTimeCreate;
    private byte commEnable;
    private int commCountLike;

    @Id
    @Column(name = "comm_id", nullable = false, insertable = true, updatable = true)
    public int getCommId() {
        return commId;
    }

    public void setCommId(int commId) {
        this.commId = commId;
    }

    @Basic
    @Column(name = "comm_document", nullable = false, insertable = true, updatable = true)
    public byte[] getCommDocument() {
        return commDocument;
    }

    public void setCommDocument(byte[] commDocument) {
        this.commDocument = commDocument;
    }

    @Basic
    @Column(name = "comm_time_create", nullable = false, insertable = true, updatable = true)
    public Timestamp getCommTimeCreate() {
        return commTimeCreate;
    }

    public void setCommTimeCreate(Timestamp commTimeCreate) {
        this.commTimeCreate = commTimeCreate;
    }

    @Basic
    @Column(name = "comm_enable", nullable = false, insertable = true, updatable = true)
    public byte getCommEnable() {
        return commEnable;
    }

    public void setCommEnable(byte commEnable) {
        this.commEnable = commEnable;
    }

    @Basic
    @Column(name = "comm_count_like", nullable = false, insertable = true, updatable = true)
    public int getCommCountLike() {
        return commCountLike;
    }

    public void setCommCountLike(int commCountLike) {
        this.commCountLike = commCountLike;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlgComment that = (BlgComment) o;

        if (commId != that.commId) return false;
        if (commEnable != that.commEnable) return false;
        if (commCountLike != that.commCountLike) return false;
        if (!Arrays.equals(commDocument, that.commDocument)) return false;
        if (commTimeCreate != null ? !commTimeCreate.equals(that.commTimeCreate) : that.commTimeCreate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commId;
        result = 31 * result + (commDocument != null ? Arrays.hashCode(commDocument) : 0);
        result = 31 * result + (commTimeCreate != null ? commTimeCreate.hashCode() : 0);
        result = 31 * result + (int) commEnable;
        result = 31 * result + commCountLike;
        return result;
    }
}
