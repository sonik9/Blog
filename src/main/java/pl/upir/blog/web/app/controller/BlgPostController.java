package pl.upir.blog.web.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.upir.blog.entity.BlgPost;
import pl.upir.blog.service.BlgPostService;
import pl.upir.blog.service.BlgUserService;
import pl.upir.blog.service.security.BlgUserSecurityServiceImpl;
import pl.upir.blog.web.form.FormPostPagination;

/**
 * Created by Vitalii on 28/08/2015.
 */
@Controller
@RequestMapping("/")
public class BlgPostController {
    final Logger logger = LoggerFactory.getLogger(BlgHomeController.class);

    @Autowired
    private MessageSource messageSource;
    @Autowired
    BlgUserService blgUserService;
    @Autowired
    private BlgUserSecurityServiceImpl blgUserSecurityService;
    @Autowired
    BlgPostService blgPostService;

    @RequestMapping(value = "/{firstName}.{lastName}/post/create", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("test","HALLOO TEST");
        return "post/create";
    }
}
