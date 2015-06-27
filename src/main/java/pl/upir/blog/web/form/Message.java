package pl.upir.blog.web.form;

/**
 * Created by Vitalii on 23.06.2015.
 */
public class Message {
    private String message;
    private String type;
    private String firstWord;

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public Message() {

    }

    public Message( String type,String firstWord,String message) {
        this.message = message;
        this.type = type;
        this.firstWord = firstWord;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
