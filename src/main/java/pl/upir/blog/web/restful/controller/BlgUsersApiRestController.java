package pl.upir.blog.web.restful.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.upir.blog.entity.BlgDicRole;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.BlgUserDetail;
import pl.upir.blog.entity.Gender;
import pl.upir.blog.service.BlgDicRoleService;
import pl.upir.blog.service.BlgUserDetailService;
import pl.upir.blog.service.BlgUserService;
import pl.upir.blog.web.form.Message;
import pl.upir.blog.web.util.MD5Encoder;
import pl.upir.blog.wrapper.WrapperRegister;
import pl.upir.blog.wrapper.WrapperUserDetailJson;
import pl.upir.blog.wrapper.WrapperUserDetailJsonValidator;


import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Vitalii on 17.06.2015.
 */
@RestController
@RequestMapping("/restful")
public class BlgUsersApiRestController {
    private final Logger logger = LoggerFactory.getLogger(BlgUsersApiRestController.class);

    @Autowired(required = false)
    private BlgUserService blgUserService;

    @Autowired
    private MessageSource messageSource;


    @Autowired
    private BlgDicRoleService blgDicRoleService;

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    String getUser(Principal principal, HttpServletRequest httpServletRequest) {
        //principal.getName()

        return "HALLO";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public BlgUser getString(Principal principal, HttpServletRequest request) {
        Authentication auth = (Authentication) principal;
        if (auth instanceof OAuth2Authentication) {
            return ((BlgUser) auth.getPrincipal());
        }

        return null;
    }

    /*TODO Log & comments*/

    /*{"blgUser":{"usrLogin":"test@test","usrPassword":"test"},"blgUserDetail":{"usrDetFirstname":"test","usrDetLastname":"test"}}*/

    @RequestMapping(value = "/api/signup", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<WrapperRegister> registerJson(@RequestBody WrapperRegister wrapperRegister) throws NoSuchAlgorithmException {
        BlgUser blgUser = wrapperRegister.getBlgUser();
        blgUser.setBlgUserDetail(wrapperRegister.getBlgUserDetail());

        blgUser.setUsrPassword(BCrypt.hashpw(blgUser.getUsrPassword(), BCrypt.gensalt()));
        BlgDicRole blgDicRole = blgDicRoleService.findById(2);
        blgUser.getBlgUserRoleSet().add(blgDicRole);
        blgUser.getBlgUserDetail().setBlgUser(blgUser);
        blgUserService.save(blgUser);
        return new ResponseEntity<WrapperRegister>(wrapperRegister, HttpStatus.OK);
    }


    @RequestMapping(value = "/api/currentuser", method = RequestMethod.POST)
    @PreAuthorize("isFullyAuthenticated()")
    public WrapperUserDetailJson getUser(Principal principal) throws AuthenticationException {
        Authentication auth = (Authentication) principal;
        WrapperUserDetailJson wrapperUserDetailJson = new WrapperUserDetailJson();
        if (auth instanceof OAuth2Authentication) {
            String usrLogin = ((BlgUser) auth.getPrincipal()).getUsrLogin();
            BlgUser blgUser = blgUserService.findByUsrLogin(usrLogin);
            // httpServletResponse.addHeader("ContentType","application/json");

            wrapperUserDetailJson.setLogin(blgUser.getUsrLogin());
            wrapperUserDetailJson.setFirstname(blgUser.getBlgUserDetail().getUsrDetFirstname());
            wrapperUserDetailJson.setLastname(blgUser.getBlgUserDetail().getUsrDetLastname());
            wrapperUserDetailJson.setGender(blgUser.getBlgUserDetail().getUsrGender().getValue());
            wrapperUserDetailJson.setPhotoLink(blgUser.getBlgUserDetail().getUsrPhotoLink());
        }
        return wrapperUserDetailJson;
    }

   /* @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new WrapperUserDetailJsonValidator());
    }*/
    /*{"login":"test@test", "password":"test", "firstname":"test", "lastname":"test", "gender":"Female", "photoLink":"null"}*/
    @RequestMapping(value = "/api/updatecurrentuser", method = RequestMethod.POST)
    @PreAuthorize("isFullyAuthenticated()")
    @ResponseBody
    public ResponseEntity updateUser( @Valid @RequestBody WrapperUserDetailJson wrapperUserDetailJson, BindingResult bindingResult, Map map) throws AuthenticationException {
        /*Validation filed through Validator*/
        //new WrapperUserDetailJsonValidator().validate(wrapperUserDetailJson,bindingResult);

        if(bindingResult.hasErrors()){
            return new  ResponseEntity(new Message("","Error",bindingResult.getAllErrors().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BlgUser blgUserUpdate = blgUserService.findById(((BlgUser) principal).getUsrId());
        if (new MD5Encoder().matches(wrapperUserDetailJson.getPassword(), blgUserUpdate.getUsrPassword())) {
            blgUserUpdate.setUsrLogin(wrapperUserDetailJson.getLogin());
            blgUserUpdate.getBlgUserDetail().setUsrDetFirstname(wrapperUserDetailJson.getFirstname());
            blgUserUpdate.getBlgUserDetail().setUsrDetLastname(wrapperUserDetailJson.getLastname());
            blgUserUpdate.getBlgUserDetail().setUsrGender(Gender.valueOf(wrapperUserDetailJson.getGender()));
            //blgUser.setUsrPassword(BCrypt.hashpw(blgUser.getUsrPassword(), BCrypt.gensalt()));
            //blgUserUpdate.setUsrPassword(blgUser.getUsrPassword());
            blgUserService.save(blgUserUpdate);

       /* blgUser.setUsrId(((BlgUser) principal).getUsrId());
        blgUser.setUsrPassword(BCrypt.hashpw(blgUser.getUsrPassword(), BCrypt.gensalt()));
        blgUser = blgUserService.save(blgUser);
        BlgUserDetail blgUserDetail = wrapperRegister.getBlgUserDetail();*/

        /*blgUserDetail.setUsrId(blgUser.getUsrId());
        blgUserDetailService.save(blgUserDetail);*/
            return new ResponseEntity(wrapperUserDetailJson, HttpStatus.OK);
        } else {
            return new ResponseEntity("Wrong password", HttpStatus.FORBIDDEN);
        }
    }

    /*

    @RequestMapping(value = "/listdata", method = RequestMethod.GET)
    @ResponseBody
    public BlgUsersS listData(){
        return new BlgUsersS(blgUsersService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BlgUsers findByBlgUsersId(@PathVariable int id){
        return blgUsersService.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public BlgUsers create(@RequestBody @Valid BlgUsers blgUsers){
        logger.info("Creating user:"+ blgUsers);
        blgUsersService.save(blgUsers);
        logger.info("User was created successful"+ blgUsers);
        return blgUsers;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody BlgUsers blgUsers, @PathVariable int id) {
        logger.info("Updating contact: " + blgUsers);
        blgUsersService.save(blgUsers);
        logger.info("Contact updated successfully with info: " + blgUsers);
        //return contact;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable int id) {
        logger.info("Deleting contact with id: " + id);
        BlgUsers blgUsers = blgUsersService.findById(id);
        blgUsersService.delete(blgUsers);
        logger.info("Contact deleted successfully");
    }*/

}
