package pl.upir.blog.wrapper;

import org.springframework.beans.factory.annotation.Autowired;
import pl.upir.blog.service.BlgUserService;

/**
 * Created by Vitalii on 09.07.2015.
 */
public class WrapperUserDetailJson {


    @Autowired
    BlgUserService blgUserService;

    private String login;
    private String firstname;
    private String lastname;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
}
