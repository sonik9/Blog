package pl.upir.blog.web.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.BlgUserDetail;
import pl.upir.blog.service.BlgUserDetailService;
import pl.upir.blog.service.BlgUserService;
import pl.upir.blog.web.form.Message;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;


/**
 * Created by Vitalii on 19.06.2015.
 * efewfef
 * wefwef
 */
@RequestMapping("/users")
@Controller
public class BlgUserController {
    private final Logger logger = LoggerFactory.getLogger(BlgUserController.class);

    @Autowired
    private BlgUserService blgUserService;
    @Autowired
    private BlgUserDetailService blgUserDetailService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {

        logger.info("Listing users");
        List<BlgUser> blgUserList = blgUserService.findAll();
        List<BlgUserDetail> blgUserDetailList = blgUserDetailService.findAll();
        model.addAttribute("blgUser", blgUserList);
        model.addAttribute("blgUserDetail", blgUserDetailList);
        logger.info("No. of users" + blgUserList.size());
        return "users/list";
    }
/*
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") int id, Model model) {
        *//*TODO log*//*
        logger.info("Listing users");
        BlgUser blgUser = blgUserService.findById(id);
        model.addAttribute("blgUser", blgUser);
        //logger.info("No. of users" + blgUser.size());
        return "users/show";
    }*/

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, params = "form")
    public String update(@Valid @ModelAttribute("blgUser") BlgUser blgUser, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest,
                         RedirectAttributes redirectAttributes, Locale locale){
        logger.info("Update user");
        if(bindingResult.hasErrors()){
            model.addAttribute("message",new Message("alert alert-danger","Oh snap!",messageSource.getMessage("save_fail", new Object[]{},locale)));
            model.addAttribute("blgUser",blgUser);
            return "users/update";
        }
        model.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("alert alert-success","Well done!", messageSource.getMessage("save_success",new Object[]{},locale)));
        blgUserService.save(blgUser);
        return "redirect:/users/"+blgUser.getUsrId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, params = "form")
    public String updateForm(@PathVariable("id") int id, Model model){
        model.addAttribute("blgUser", blgUserService.findById(id));
        return "users/update";
    }

    /*Add or update*/
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("blgUser") BlgUser blgUser, BindingResult bindingUser,
                         BlgUserDetail blgUserDetail, Model model, HttpServletRequest httpServletRequest,
                         RedirectAttributes redirectAttributes, Locale locale){
        logger.info("Create user");
        if(bindingUser.hasErrors()){
            model.addAttribute("message",new Message("alert alert-danger","Oh snap!",messageSource.getMessage("save_fail", new Object[]{},locale)));
            model.addAttribute("blgUser",blgUser);
            return "users/create";
        }
        model.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("alert alert-success","Well done!", messageSource.getMessage("save_success",new Object[]{},locale)));
        logger.info("User id:"+blgUser.getUsrId());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BlgUser blgUserUpdate=blgUserService.findById(((BlgUser) principal).getUsrId());

        blgUserUpdate.setUsrLogin(blgUser.getUsrLogin());
        blgUserUpdate.getBlgUserDetail().setUsrDetFirstname(blgUser.getBlgUserDetail().getUsrDetFirstname());
        blgUserUpdate.getBlgUserDetail().setUsrDetLastname(blgUser.getBlgUserDetail().getUsrDetLastname());
         blgUser.setUsrPassword(BCrypt.hashpw(blgUser.getUsrPassword(), BCrypt.gensalt()));
        blgUserUpdate.setUsrPassword(blgUser.getUsrPassword());

        blgUserService.save(blgUserUpdate);

        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.GET, params = "form")
    public String createForm(Model model){
        BlgUser blgUser = new BlgUser();
        model.addAttribute("blgUser", blgUser);
        return "users/create";
    }

    @RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id, Model model, Locale locale, RedirectAttributes redirectAttributes){
        logger.info("Delete user");
        BlgUser blgUser= blgUserService.findById(id);
        if(blgUser==null){
            model.addAttribute("message",new Message("alert alert-danger","Oh snap!",messageSource.getMessage("delete_fail", new Object[]{},locale)));
            model.addAttribute("blgUser",blgUser);
            return "users/";
        }

        model.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("alert alert-success","Well done!", messageSource.getMessage("delete_success",new Object[]{},locale)));
        blgUserService.delete(blgUser);
        return "redirect:/users/";
    }




    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String showUserDetail(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ((BlgUser)principal).setUsrPassword("");
        model.addAttribute("blgUser", ((BlgUser)principal));
        model.addAttribute("blgUserDetail", ((BlgUser)principal).getBlgUserDetail());
        return "users/update";
    }


}
