package pl.upir.blog.web.form;

import pl.upir.blog.entity.BlgPost;

import java.util.List;

/**
 * Created by Vitalii on 27/08/2015.
 */
public class FormPostPagination {
    private int totalPage;
    private int currentPage;
    private long totalRecords;
    private List<BlgPost> blgPostList;



    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<BlgPost> getBlgPostList() {
        return blgPostList;
    }

    public void setBlgPostList(List<BlgPost> blgPostList) {
        this.blgPostList = blgPostList;
    }


}
