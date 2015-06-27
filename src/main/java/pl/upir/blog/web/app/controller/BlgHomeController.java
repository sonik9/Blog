package pl.upir.blog.web.app.controller;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Vitalii on 22.06.2015.
 */
@Controller
@RequestMapping("/")
public class BlgHomeController {
    final Logger logger = LoggerFactory.getLogger(BlgHomeController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        DateTime dateTime = new DateTime();
        model.addAttribute("dateTime",dateTime);
    return "home/home";
    }

    @RequestMapping(value = "/sign", method = RequestMethod.GET)
    public String registration(Model model) {

        return "sign";
    }
}
