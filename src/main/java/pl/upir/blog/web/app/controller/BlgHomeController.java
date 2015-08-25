package pl.upir.blog.web.app.controller;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.BlgUserDetail;
import pl.upir.blog.entity.Gender;
import pl.upir.blog.service.BlgUserService;
import pl.upir.blog.service.security.BlgUserSecurityServiceImpl;
import pl.upir.blog.web.form.Message;
import pl.upir.blog.web.util.ImageCropper;
import pl.upir.blog.web.util.MD5Encoder;
import pl.upir.blog.web.util.UrlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
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
    @Autowired
    BlgUserService blgUserService;
    @Autowired
    private BlgUserSecurityServiceImpl blgUserSecurityService ;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        DateTime dateTime = new DateTime();
        model.addAttribute("dateTime",dateTime);
    return "home";
    }
    @RequestMapping(params = "error")
    public  String error(@RequestParam("error") String error,Model model,  Locale locale, HttpServletRequest httpServletRequest){


        if(error.equals("access_denied"))
            model.addAttribute("message", new Message("alert alert-danger", "Oh snap!", messageSource.getMessage("sign.access.denied", new Object[]{}, locale)));
    return "redirect:"+httpServletRequest.getRequestURI();
    }


    @RequestMapping(value = "/{firstName}.{lastName}", method = RequestMethod.GET)
    @PreAuthorize("isFullyAuthenticated()")
    public String showCurrentUser(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, Model model) {
        /*TODO log*/
        //logger.info("Listing users");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(firstName.equals(((BlgUser)principal).getBlgUserDetail().getUsrDetFirstname())&&lastName.equals(((BlgUser)principal).getBlgUserDetail().getUsrDetLastname())) {
            BlgUser blgUser = blgUserService.findById(((BlgUser) principal).getUsrId());
            blgUser.setUsrPassword("");
            model.addAttribute("blgUser", blgUser);
            model.addAttribute("gender", Gender.values());
            //logger.info("No. of users" + blgUser.size());
            return "users/show";
        }else {
            return "home";
        }
    }
/*Update user details*/
    @RequestMapping(value = "/{firstName}.{lastName}/save",method = RequestMethod.POST)
    public String updateUserDetail(@Valid @ModelAttribute("blgUser") BlgUser blgUser, BindingResult bindingUser,
                         Model model, HttpServletRequest httpServletRequest,
                         RedirectAttributes redirectAttributes, Locale locale){
        logger.info("Update user");
        if(bindingUser.hasErrors()){
            model.addAttribute("message",new Message("alert alert-danger","Oh snap!",messageSource.getMessage("save_fail", new Object[]{},locale)));
            model.addAttribute("blgUser",blgUser);
            return "users/show";
        }
        model.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("alert alert-success","Well done!", messageSource.getMessage("save_success",new Object[]{},locale)));
        logger.info("User id:"+blgUser.getUsrId());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BlgUser blgUserUpdate=blgUserService.findById(((BlgUser) principal).getUsrId());
        if(new MD5Encoder().matches(blgUser.getUsrPassword(),blgUserUpdate.getUsrPassword())) {
            blgUserUpdate.setUsrLogin(blgUser.getUsrLogin());
            blgUserUpdate.getBlgUserDetail().setUsrDetFirstname(blgUser.getBlgUserDetail().getUsrDetFirstname());
            blgUserUpdate.getBlgUserDetail().setUsrDetLastname(blgUser.getBlgUserDetail().getUsrDetLastname());
            blgUserUpdate.getBlgUserDetail().setUsrGender(blgUser.getBlgUserDetail().getUsrGender());
            blgUserService.save(blgUserUpdate);

            /*Principal update*/
            //Authentication authentication =new BlgUserSecurityServiceImpl().trust(userDetailsService.loadUserByUsername(blgUser.getUsrLogin()));
            Authentication authentication = blgUserSecurityService.trust(blgUserSecurityService.loadUserByUsername(blgUser.getUsrLogin()));
            //authentication.setAuthenticated(true);
            //SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
            model.addAttribute("message",new Message("alert alert-danger","Oh snap!",messageSource.getMessage("save_fail", new Object[]{},locale)));
            model.addAttribute("blgUser",blgUser);
            return "users/show";
        }

        return "redirect:/{firstName}.{lastName}";
    }

/*TODO translate & comments*/
    @RequestMapping(value = "/{firstName}.{lastName}/uploadphoto",method = RequestMethod.POST)
    public ResponseEntity uploadphoto(@RequestParam(value = "image", required = false) MultipartFile file,
                                      @RequestParam(value = "top") int top, @RequestParam(value = "left") int left,
                                      @RequestParam(value = "height") int height, @RequestParam(value = "width") int width,
                                      @RequestParam(value = "origHeight") int origHeight, @RequestParam(value = "origWidth") int origWidth,
                                      HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BlgUser blgUserUpdate=blgUserService.findById(((BlgUser) principal).getUsrId());
        if(blgUserUpdate.getBlgUserFacebook()==null) {
            if(!file.isEmpty()) {
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
                    ImageCropper.resizeImage(serverFile,origHeight,origWidth,"png");
                    ImageCropper.cropp(serverFile,"png",height,width, left, top);
                    String oldFilePath = blgUserUpdate.getBlgUserDetail().getUsrPhotoLink();

                    if(!oldFilePath.equals(UrlUtil.sourcePathFile(request,"/resources/images/"+fileName))){
                        File oldFile= new File(path+"public/images/"+oldFilePath.substring(oldFilePath.lastIndexOf("/") + 1, oldFilePath.length()));
                        if(oldFile.delete())
                            logger.info("File "+serverFile+" is deleted!");
                        else
                            logger.error("Delete operation is faild!");
                    }

                    blgUserUpdate.getBlgUserDetail().setUsrPhotoLink(UrlUtil.sourcePathFile(request,"/resources/images/"+fileName));
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
            }else
                return new ResponseEntity("Invalid file!",HttpStatus.BAD_REQUEST);

        }else
        return  new ResponseEntity("Your profile image uploaded from facebook account!", HttpStatus.CONFLICT);
    }



}
