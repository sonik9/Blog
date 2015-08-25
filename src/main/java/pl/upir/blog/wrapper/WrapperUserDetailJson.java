package pl.upir.blog.wrapper;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import pl.upir.blog.entity.Gender;
import pl.upir.blog.service.BlgUserService;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Vitalii on 09.07.2015.
 */
public class WrapperUserDetailJson {


    //@Autowired
    //BlgUserService blgUserService;

    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;

    private String gender;
    private String photoLink;

    @NotEmpty(message = "Empty field")
    @Email(message = "Email have pattern name@host.")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    @NotEmpty(message = "Empty field")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    @NotEmpty(message = "Empty field")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    @Pattern(regexp = "^[F-M].*$", message = "{validation.gender.regexp}")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String usrPhotoLink) {
        this.photoLink = usrPhotoLink;
    }

    @NotEmpty
    @NotNull
    @Size(min = 4,max = 10)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
