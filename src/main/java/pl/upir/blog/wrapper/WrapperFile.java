package pl.upir.blog.wrapper;

/**
 * Created by Vitalii on 15/09/2015.
 */
public class WrapperFile {
    private String fileName;
    private long size;
    private String path;

    public WrapperFile(String fileName, long size, String path) {
        this.fileName = fileName;
        this.size = size;
        this.path = path;
    }

    public WrapperFile(long length) {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
