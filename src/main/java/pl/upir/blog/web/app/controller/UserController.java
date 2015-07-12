package pl.upir.blog.web.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.security.BlgUserSecurity;
import pl.upir.blog.service.BlgUserService;
import pl.upir.blog.web.form.Message;

import java.util.Locale;

/**
 * Created by Vitalii on 01.07.2015.
 */
@Controller
public class UserController {

    private static BlgUserService blgUserService;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    public void setBlgUserService(BlgUserService blgUserService) {
        UserController.blgUserService = blgUserService;
    }

    public static BlgUser getCurrentUser()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails)
        {
            String usrLogin = ((UserDetails) principal).getUsername();
            BlgUser blgUser = (BlgUser) blgUserService.findByUsrLogin(usrLogin);

            return new BlgUserSecurity(blgUser);
        }

        return null;
    }



}
