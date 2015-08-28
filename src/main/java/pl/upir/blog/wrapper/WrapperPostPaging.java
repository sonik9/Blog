package pl.upir.blog.wrapper;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vitalii on 27/08/2015.
 */
public class WrapperPostPaging {

    private String name;


    private Set<WrapperPost> wrapperPost = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<WrapperPost> getWrapperPost() {
        return wrapperPost;
    }

    public void setWrapperPost(Set<WrapperPost> wrapperPost) {
        this.wrapperPost = wrapperPost;
    }
}
