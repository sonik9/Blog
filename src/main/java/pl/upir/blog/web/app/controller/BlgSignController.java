package pl.upir.blog.web.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.upir.blog.entity.BlgDicRole;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.BlgUserDetail;
import pl.upir.blog.security.BlgUserSecurity;
import pl.upir.blog.service.BlgDicRoleService;
import pl.upir.blog.service.BlgUserDetailService;
import pl.upir.blog.service.BlgUserService;
import pl.upir.blog.web.form.Message;
import pl.upir.blog.web.util.MD5Encoder;
import pl.upir.blog.wrapper.WrapperRegister;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.PrintConversionEvent;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Locale;

/**
 * Created by Vitalii on 01.07.2015.
 */
@Controller
@RequestMapping("/sign")
public class BlgSignController {

    Logger logger = LoggerFactory.getLogger(BlgSignController.class);

    @Autowired
    private BlgUserService blgUserService;
    @Autowired
    private BlgUserDetailService blgUserDetailService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private BlgDicRoleService blgDicRoleService;

    @RequestMapping
    public String signin(Model model) {
        BlgUser blgUser = new BlgUserSecurity();
        BlgUserDetail blgUserDetail = new BlgUserDetail();
        model.addAttribute("blgUser", blgUser);
        model.addAttribute("blgUserDetail", blgUserDetail);
        logger.info("go to sign");
        return "/sign";
    }

    @RequestMapping(method = RequestMethod.GET, params = "error")
    public String error(Model model, @RequestParam("error") String error, Locale locale) {
        if (error.equals("login")) {
            model.addAttribute("message", new Message("alert alert-danger", "Oh snap!", messageSource.getMessage("sign.signin.invalidLoginPass", new Object[]{}, locale)));
        }
        signin(model);
        return "/sign";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("blgUser") BlgUser blgUser, BindingResult bindingUser,
                           @Valid @ModelAttribute("blgUserDetail") BlgUserDetail blgUserDetail,
                           BindingResult bindingUserDetail, Model model, HttpServletRequest httpServletRequest,
                           RedirectAttributes redirectAttributes, Locale locale) throws NoSuchAlgorithmException {
        //logger.info("Update user");
        if (bindingUser.hasErrors()) {
            model.addAttribute("message", new Message("alert alert-danger", "Oh snap!", messageSource.getMessage("save_fail", new Object[]{}, locale)));
            model.addAttribute("blgUser", blgUser);
            return "users/update";
        }
        model.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("alert alert-success", "Well done!", messageSource.getMessage("save_success", new Object[]{}, locale)));
        //BCryptPasswordEncoder md5Encoder = new BCryptPasswordEncoder();
        blgUser.setUsrPassword(BCrypt.hashpw(blgUser.getUsrPassword(), BCrypt.gensalt()));

        BlgDicRole blgDicRole =blgDicRoleService.findById(2);
        blgUser.getBlgUserRoleSet().add(blgDicRole);
        blgUser.getBlgUserDetail().setBlgUser(blgUser);
        blgUserService.save(blgUser);
        return "redirect:/sign";
    }

    @RequestMapping(value = "/sign_json")
    public String signinJson(Model model) {
        return "/sign_json";
    }

    @RequestMapping(value = "/signup_json", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<WrapperRegister> registerJson(@RequestBody WrapperRegister wrapperRegister) throws NoSuchAlgorithmException {
        //logger.info("Update user");
        BlgUser blgUser = wrapperRegister.getBlgUser();
        BlgUserDetail blgUserDetail = wrapperRegister.getBlgUserDetail();

        blgUser.setUsrPassword(BCrypt.hashpw(blgUser.getUsrPassword(), BCrypt.gensalt()));



        blgUser = blgUserService.save(blgUser);
        //blgUserDetail.setUsrId(blgUser.getUsrId());
        blgUserDetailService.save(blgUserDetail);
        return new ResponseEntity<WrapperRegister>(wrapperRegister, HttpStatus.OK);
    }



}
