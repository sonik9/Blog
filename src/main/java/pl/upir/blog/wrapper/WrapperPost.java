package pl.upir.blog.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.upir.blog.entity.BlgDicTag;
import pl.upir.blog.entity.BlgPost;

import java.util.List;

/**
 * Created by Vitalii on 27/08/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperPost extends BlgPost{

    private List<BlgDicTag> blgDicTags;
    private String firstname;
    private String lastname;
    private String photoLink;


    public List<BlgDicTag> getBlgDicTags() {
        return blgDicTags;
    }

    public void setBlgDicTags(List<BlgDicTag> blgDicTags) {
        this.blgDicTags = blgDicTags;

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }
}
