package pl.upir.blog.web.app.controller;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.upir.blog.web.form.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by Vitalii on 22.06.2015.
 */
@Controller
@RequestMapping("/")
public class BlgHomeController {
    final Logger logger = LoggerFactory.getLogger(BlgHomeController.class);

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        DateTime dateTime = new DateTime();
        model.addAttribute("dateTime",dateTime);
    return "home/home";
    }
    @RequestMapping(params = "error")
    public  String error(@RequestParam("error") String error,Model model,  Locale locale, HttpServletRequest httpServletRequest){


        if(error.equals("access_denied"))
            model.addAttribute("message", new Message("alert alert-danger", "Oh snap!", messageSource.getMessage("sign.access.denied", new Object[]{}, locale)));
    return "redirect:"+httpServletRequest.getRequestURI();
    }


}
