package pl.upir.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Vitalii on 11.08.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookUser implements Serializable{
    public enum Gender {male,female};
   /* public static class Name {
        private String first, last;

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }
    }*/
    @JsonProperty("id")
    private long id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("gender")
    private Gender gender;
    //private Name name;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("verified")
    private boolean isVerified;
    private byte[] userImage;
//getters
    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }


    public boolean isVerified() {
        return isVerified;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
//setters
    public void setEmail(String email) {
        this.email = email;
    }


    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
