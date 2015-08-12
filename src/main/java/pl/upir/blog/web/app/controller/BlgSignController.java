package pl.upir.blog.web.app.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pl.upir.blog.entity.BlgDicRole;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.BlgUserDetail;
import pl.upir.blog.entity.FacebookUser;
import pl.upir.blog.security.BlgUserSecurity;
import pl.upir.blog.service.BlgDicRoleService;
import pl.upir.blog.service.BlgUserDetailService;
import pl.upir.blog.service.BlgUserService;
import pl.upir.blog.web.form.Message;
import pl.upir.blog.web.util.UrlUtil;
import pl.upir.blog.wrapper.WrapperRegister;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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

    public static String FACEBOOK_URL="https://www.facebook.com/dialog/oauth";
    public static String FACEBOOK_API_KEY="777058695740737";
    final public static String FACEBOOK_API_SECRET ="2e270ccbeb947b9596b5a9c8a63c17f2";
    public static String FACEBOOK_URL_CALLBACK_REGISTRATION="http://46.101.231.222:8080/sign/facebook/register";
    final public static String FACEBOOK_URL_ACCESS_TOKEN = "https://graph.facebook.com/oauth/access_token";
    final public static String FACEBOOK_URL_ME = "https://graph.facebook.com/me";
    final public static String FACEBOOK_URL_ME_AVATAR="https://graph.facebook.com/me/picture";
    @RequestMapping(value = "facebook", method = RequestMethod.GET)
    public String signinFacebook(Model model)throws Exception{

        return "redirect:"+FACEBOOK_URL + "?client_id=" + FACEBOOK_API_KEY
                + "&redirect_uri=" + FACEBOOK_URL_CALLBACK_REGISTRATION
                + "&scope=email,user_location&state=registration";
    }

    @ RequestMapping(value = "/facebook/register", params = "code")
    public
    String registrationAccessCode(@RequestParam("code") String code, HttpServletRequest request) throws Exception {
        String authRequest = UrlUtil.sendHttpRequest("GET", FACEBOOK_URL_ACCESS_TOKEN, new String[]{"client_id", "redirect_uri", "client_secret", "code"}, new String[]{FACEBOOK_API_KEY, FACEBOOK_URL_CALLBACK_REGISTRATION, FACEBOOK_API_SECRET, code});
        String token = UrlUtil.parseURLQuery(authRequest).get("access_token");
        String tokenRequest = UrlUtil.sendHttpRequest("GET", FACEBOOK_URL_ME, new String[]{"access_token"}, new String[]{token});
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FacebookUser facebookUser = new FacebookUser();
        facebookUser=mapper.readValue(tokenRequest, FacebookUser.class);
        List<URI> imageAvatarUrl=UrlUtil.sendHttpRequestRedirectUrl("GET",FACEBOOK_URL_ME_AVATAR, new String[]{"type","access_token"}, new String[]{"large",token});
        //Request.Get()
        return "redirect:"+imageAvatarUrl.get(0).toString();
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
