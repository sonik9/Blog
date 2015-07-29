package pl.upir.blog.web.restful.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.upir.blog.entity.BlgDicRole;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.BlgUserDetail;
import pl.upir.blog.service.BlgUserDetailService;
import pl.upir.blog.service.BlgUserService;
import pl.upir.blog.wrapper.WrapperRegister;
import pl.upir.blog.wrapper.WrapperUserDetailJson;


import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

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
    private BlgUserDetailService blgUserDetailService;

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
        BlgUserDetail blgUserDetail = wrapperRegister.getBlgUserDetail();

        blgUser.setUsrPassword(BCrypt.hashpw(blgUser.getUsrPassword(), BCrypt.gensalt()));
        blgUser = blgUserService.save(blgUser);
        blgUserDetail.setUsrId(blgUser.getUsrId());
        blgUserDetailService.save(blgUserDetail);
        return new ResponseEntity<WrapperRegister>(wrapperRegister, HttpStatus.OK);
    }


    @RequestMapping(value = "/api/currentuser", method = RequestMethod.POST)
    public WrapperUserDetailJson getUser(Principal principal) throws AuthenticationException{
        Authentication auth = (Authentication) principal;
        WrapperUserDetailJson wrapperUserDetailJson = new WrapperUserDetailJson();
        if (auth instanceof OAuth2Authentication) {
            String usrLogin = ((BlgUser) auth.getPrincipal()).getUsrLogin();
            BlgUser blgUser = blgUserService.findByUsrLogin(usrLogin);
            // httpServletResponse.addHeader("ContentType","application/json");

            wrapperUserDetailJson.setLogin(blgUser.getUsrLogin());
            wrapperUserDetailJson.setFirstname(blgUser.getGetBlgUserDetail().getUsrDetFirstname());
            wrapperUserDetailJson.setLastname(blgUser.getGetBlgUserDetail().getUsrDetLastname());
        }
        return wrapperUserDetailJson;
    }
    /*{"blgUser":{"usrLogin":"test@test","usrPassword":"test"},"blgUserDetail":{"usrDetFirstname":"test","usrDetLastname":"test"}}*/
    @RequestMapping(value = "/api/updatecurrentuser", method = RequestMethod.POST)
    public ResponseEntity<WrapperRegister> updateUser(@RequestBody WrapperRegister wrapperRegister) throws AuthenticationException{

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        BlgUser blgUser = wrapperRegister.getBlgUser();
        blgUser.setUsrId(((BlgUser) principal).getUsrId());
        blgUser.setUsrPassword(BCrypt.hashpw(blgUser.getUsrPassword(), BCrypt.gensalt()));
        blgUser = blgUserService.save(blgUser);
        BlgUserDetail blgUserDetail = wrapperRegister.getBlgUserDetail();

        /*blgUserDetail.setUsrId(blgUser.getUsrId());
        blgUserDetailService.save(blgUserDetail);*/
        return new ResponseEntity<WrapperRegister>(wrapperRegister, HttpStatus.OK);
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