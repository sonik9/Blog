package pl.upir.blog.web.restful.controller;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.upir.blog.entity.*;
import pl.upir.blog.service.*;
import pl.upir.blog.service.security.BlgUserSecurityServiceImpl;
import pl.upir.blog.web.form.Message;
import pl.upir.blog.web.util.ImageCropper;
import pl.upir.blog.web.util.MD5Encoder;
import pl.upir.blog.web.util.UrlUtil;
import pl.upir.blog.wrapper.*;


import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.*;

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

    @Autowired
    private BlgUserSecurityServiceImpl blgUserSecurityService;

    @Autowired
    BlgPostService blgPostService;

    @Autowired
    BlgDicCategoryService blgDicCategoryService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getUser(Principal principal, HttpServletRequest httpServletRequest) {
        //principal.getName()

        return "HALLO";
    }

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
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
    @ResponseBody
    public ResponseEntity registerJson(@Valid @RequestBody WrapperUserDetailJson wrapperUserDetailJson, BindingResult bindingResult) throws NoSuchAlgorithmException {

        if (bindingResult.hasErrors()) {
            Map<String, Map<String, String>> mapError = new HashMap<>();
            Map<String, String> map = new HashMap<>();
            bindingResult.getFieldErrors().forEach(f -> map.put(f.getField(), f.getDefaultMessage()));
            mapError.put("Error", map);
            return new ResponseEntity(mapError, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (blgUserService.findByUsrLogin(wrapperUserDetailJson.getLogin()) == null) {

            BlgUser blgUser = new BlgUser();
            blgUser.setUsrLogin(wrapperUserDetailJson.getLogin());
            blgUser.setUsrPassword(BCrypt.hashpw(wrapperUserDetailJson.getPassword(), BCrypt.gensalt()));
            BlgUserDetail blgUserDetail = new BlgUserDetail();
            blgUser.setBlgUserDetail(blgUserDetail);
            blgUser.getBlgUserDetail().setUsrDetFirstname(wrapperUserDetailJson.getFirstname());
            blgUser.getBlgUserDetail().setUsrDetLastname(wrapperUserDetailJson.getLastname());
            BlgDicRole blgDicRole = blgDicRoleService.findById(2);
            blgUser.getBlgUserRoleSet().add(blgDicRole);
            blgUser.getBlgUserDetail().setBlgUser(blgUser);
            blgUserService.save(blgUser);
            return new ResponseEntity(wrapperUserDetailJson, HttpStatus.OK);
        } else
            return new ResponseEntity("Login: " + wrapperUserDetailJson.getLogin() + ", is already exist!", HttpStatus.FORBIDDEN);
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
    public ResponseEntity updateUser(@Valid @RequestBody WrapperUserDetailJson wrapperUserDetailJson, BindingResult bindingResult) throws AuthenticationException {
        /*Validation filed through Validator*/
        //new WrapperUserDetailJsonValidator().validate(wrapperUserDetailJson,bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new Message("", "Error", bindingResult.getAllErrors().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @RequestMapping(value = "/api/updatecurrentuser/uploadimage", method = RequestMethod.POST)
    @PreAuthorize("isFullyAuthenticated()")
    @ResponseBody
    public ResponseEntity uploadImage(@RequestParam(value = "image", required = false) MultipartFile file,
                                      @RequestParam(value = "top") int top, @RequestParam(value = "left") int left,
                                      @RequestParam(value = "height") int height, @RequestParam(value = "width") int width,
                                      @RequestParam(value = "origHeight") int origHeight, @RequestParam(value = "origWidth") int origWidth,
                                      HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Authentication auth = (Authentication) principal;


        BlgUser blgUserUpdate = blgUserService.findById(((BlgUser) principal).getUsrId());
        if (blgUserUpdate.getBlgUserFacebook() == null) {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                byte[] bytes = new byte[0];
                try {
                    bytes = file.getBytes();
                    String path = request.getRealPath("/");
                    File serverFile = new File(path + "public/images/" + fileName);
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();
                    ImageCropper.resizeImage(serverFile, origHeight, origWidth, "png");
                    ImageCropper.cropp(serverFile, "png", height, width, left, top);
                    String oldFilePath = blgUserUpdate.getBlgUserDetail().getUsrPhotoLink();

                    if (!oldFilePath.equals("/resources/images/" + fileName)) {
                        File oldFile = new File(path + "public/images/" + oldFilePath.substring(oldFilePath.lastIndexOf("/") + 1, oldFilePath.length()));
                        if (oldFile.delete())
                            logger.info("File " + serverFile + " is deleted!");
                        else
                            logger.error("Delete operation is faild!");
                    }

                    blgUserUpdate.getBlgUserDetail().setUsrPhotoLink("/resources/images/" + fileName);
                    blgUserService.save(blgUserUpdate);
                    /*Principal update*/
                    Authentication authentication = blgUserSecurityService.trust(blgUserSecurityService.loadUserByUsername(blgUserUpdate.getUsrLogin()));
                    //authentication.setAuthenticated(true);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                } catch (IOException e) {
                    e.printStackTrace();
                    return new ResponseEntity(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return new ResponseEntity("Your image was updated!", HttpStatus.OK);
            } else
                return new ResponseEntity("Invalid file!", HttpStatus.BAD_REQUEST);

        } else
            return new ResponseEntity("Your profile image uploaded from facebook account!", HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/api/post", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity listData(@RequestParam(value = "rows") int rows, @RequestParam(value = "page") int page) {
        Page<BlgPost> blgPostPage = blgPostService.findAllByPage(new PageRequest(page, rows));
        //List<BlgPost> blgPostList = blgPostService.findAll();
        List<WrapperPost> wrapperPostList = new ArrayList<>();

        blgPostPage.forEach(blgPost -> {
            //BlgPost blgPost = blgPostService.findById(1);

            WrapperPost wrapperPost = new WrapperPost();
            List<BlgUser> blgUserSet = Lists.newArrayList(blgPost.getBlgUserSet());
            wrapperPost.setFirstname(blgUserSet.iterator().next().getBlgUserDetail().getUsrDetFirstname());
            wrapperPost.setLastname(blgUserSet.iterator().next().getBlgUserDetail().getUsrDetLastname());
            wrapperPost.setPhotoLink(blgUserSet.iterator().next().getBlgUserDetail().getUsrPhotoLink());
            wrapperPost.setBlgDicTags(Lists.newArrayList(blgPost.getBlgDicTagSet()));
            wrapperPost.setBlgDicCategorySet(blgPost.getBlgDicCategorySet());
            wrapperPost.setPstTitle(blgPost.getPstTitle());
            wrapperPost.setPstTitleImage(blgPost.getPstTitleImage());
            wrapperPost.setPstUrl(blgPost.getPstUrl());
            wrapperPost.setPstDocument(blgPost.getPstDocument());
            wrapperPost.setPstTimeCreate(blgPost.getPstTimeCreate());
            wrapperPost.setPstTimeModify(blgPost.getPstTimeModify());
            wrapperPost.setPstCountLike(blgPost.getPstCountLike());
            wrapperPost.setPstCountDislike(blgPost.getPstCountDislike());
            wrapperPost.setPstCountComm(blgPost.getPstCountComm());

            wrapperPostList.add(wrapperPost);

        });


        return new ResponseEntity(wrapperPostList, HttpStatus.OK);
    }

    /*@RequestMapping(value = "/{id}", method = RequestMethod.GET)
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
