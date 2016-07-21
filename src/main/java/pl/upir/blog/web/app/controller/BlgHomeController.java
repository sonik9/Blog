package pl.upir.blog.web.app.controller;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.upir.blog.entity.BlgDicCategory;
import pl.upir.blog.entity.BlgPost;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.Gender;
import pl.upir.blog.service.BlgDicCategoryService;
import pl.upir.blog.service.BlgPostService;
import pl.upir.blog.service.BlgUserService;
import pl.upir.blog.service.security.BlgUserSecurityServiceImpl;
import pl.upir.blog.web.form.Message;
import pl.upir.blog.web.util.ImageCropper;
import pl.upir.blog.web.util.MD5Encoder;
import pl.upir.blog.wrapper.WrapperFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.*;

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
    private BlgUserSecurityServiceImpl blgUserSecurityService;
    @Autowired
    BlgPostService blgPostService;
    @Autowired
    BlgDicCategoryService blgDicCategoryService;

    @RequestMapping(value = {"", "/category/{category}", "/tag/{tag}" ,"/{y}/{m}"}, method = RequestMethod.GET)
    public String home(@RequestParam(value = "rows", defaultValue = "5", required = false) int rows,
                        @RequestParam(value = "page", defaultValue = "0", required = false) int page, Model model,
                        @PathVariable Optional<String> category, @PathVariable Optional<String> tag,
                        @PathVariable Optional<String> y, @PathVariable Optional<String> m) throws ParseException {
        Sort sort = new Sort(Sort.Direction.DESC, "pstTimeCreate");
        Page<BlgPost> blgPostPage = null;
        if (!category.isPresent()&&!tag.isPresent()&&(!y.isPresent() && !m.isPresent())) {
            blgPostPage = blgPostService.findByPstEnable(true, new PageRequest(page, rows, sort));
            model.addAttribute("currentCategory", "");
        }
        else if(category.isPresent()){
            blgPostPage = blgPostService.findByCat(category.get(), true, new PageRequest(page, rows, sort));
            model.addAttribute("currentCategory", category.get());
        }
        else if(tag.isPresent()){
            blgPostPage = blgPostService.findByTag(tag.get(), true, new PageRequest(page, rows, sort));
        }
        else if(y.isPresent()&&m.isPresent()){
            blgPostPage = blgPostService.findByPstEnableAndLikeDate(true,y.get(),
                    Month.valueOf(m.get().toUpperCase()).getValue(),
                    new PageRequest(page, rows, sort));
        }
        model.addAttribute("posts", blgPostPage);

        List<BlgDicCategory> blgDicCategoryList = blgDicCategoryService.findAll();
        model.addAttribute("categories", blgDicCategoryList);
        ArrayList<Date> allDateAndPstEnableList= blgPostService.findAllDateAndPstEnable(true);
        Map<String, ArrayList<String>> dateMap = new HashMap<>();
        allDateAndPstEnableList.forEach(item ->{
            String year=new SimpleDateFormat("YYYY").format(item);
            String month=new SimpleDateFormat("MMMM").format(item);
            if(!dateMap.containsKey(year)){
                ArrayList<String> value = new ArrayList<String>();
                value.add(month);
                dateMap.put(year, value);
            }else {
                ArrayList<String> values = dateMap.get(year);
                if(!values.contains(month)){
                    values.add(month);
                }
                dateMap.replace(year,values);
            }
        });
        model.addAttribute("history", dateMap);
        return "homePage";
    }


    @RequestMapping(params = "error")
    public String error(@RequestParam("error") String error, Model model, Locale locale, HttpServletRequest httpServletRequest) {


        if (error.equals("access_denied"))
            model.addAttribute("message", new Message("alert alert-danger", "Oh snap!", messageSource.getMessage("sign.access.denied", new Object[]{}, locale)));
        return "redirect:" + httpServletRequest.getRequestURI();
    }


    @RequestMapping(value = "/{firstName}.{lastName}", method = RequestMethod.GET)
    @PreAuthorize("isFullyAuthenticated()")
    public String showCurrentUser(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, Model model) {
        /*TODO log*/
        //logger.info("Listing users");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (firstName.equals(((BlgUser) principal).getBlgUserDetail().getUsrDetFirstname()) && lastName.equals(((BlgUser) principal).getBlgUserDetail().getUsrDetLastname())) {
            BlgUser blgUser = blgUserService.findById(((BlgUser) principal).getUsrId());
            blgUser.setUsrPassword("");
            model.addAttribute("blgUser", blgUser);
            model.addAttribute("gender", Gender.values());
            //logger.info("No. of users" + blgUser.size());
            return "users/show";
        } else {
            return "homePage";
        }
    }

    /*Update user details*/
    @RequestMapping(value = "/{firstName}.{lastName}/save", method = RequestMethod.POST)
    public String updateUserDetail(@Valid @ModelAttribute("blgUser") BlgUser blgUser, BindingResult bindingUser,
                                   Model model, HttpServletRequest httpServletRequest,
                                   RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Update user");
        if (bindingUser.hasErrors()) {
            model.addAttribute("message", new Message("alert alert-danger", "Oh snap!", messageSource.getMessage("save_fail", new Object[]{}, locale)));
            model.addAttribute("blgUser", blgUser);
            return "users/show";
        }
        model.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("alert alert-success", "Well done!", messageSource.getMessage("save_success", new Object[]{}, locale)));
        logger.info("User id:" + blgUser.getUsrId());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BlgUser blgUserUpdate = blgUserService.findById(((BlgUser) principal).getUsrId());
        if (new MD5Encoder().matches(blgUser.getUsrPassword(), blgUserUpdate.getUsrPassword())) {
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
        } else {
            model.addAttribute("message", new Message("alert alert-danger", "Oh snap!", messageSource.getMessage("save_fail", new Object[]{}, locale)));
            model.addAttribute("blgUser", blgUser);
            return "users/show";
        }

        return "redirect:/{firstName}.{lastName}";
    }

    /*TODO translate & comments*/
    @RequestMapping(value = "/{firstName}.{lastName}/uploadphoto", method = RequestMethod.POST)
    public ResponseEntity uploadphoto(@RequestParam(value = "image", required = false) MultipartFile file,
                                      @RequestParam(value = "top") int top, @RequestParam(value = "left") int left,
                                      @RequestParam(value = "height") int height, @RequestParam(value = "width") int width,
                                      @RequestParam(value = "origHeight") int origHeight, @RequestParam(value = "origWidth") int origWidth,
                                      HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BlgUser blgUserUpdate = blgUserService.findById(((BlgUser) principal).getUsrId());
        if (blgUserUpdate.getBlgUserFacebook() == null) {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                byte[] bytes = new byte[0];
                try {
                    bytes = file.getBytes();
                    String path = request.getRealPath("/");
                    File serverFile = new File(path + "public/images/" + fileName);
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();

                    ImageCropper.resizeImage(serverFile, origHeight, origWidth, "png");
                    ImageCropper.cropp(serverFile, "png", height, width, left, top);

                    String oldFilePath = blgUserUpdate.getBlgUserDetail().getUsrPhotoLink();
                    if (oldFilePath != null) {
                        if (!oldFilePath.equals("resources/images/" + fileName)) {
                            File oldFile = new File(path + "public/images/" + oldFilePath.substring(oldFilePath.lastIndexOf("/") + 1, oldFilePath.length()));
                            if (oldFile.delete())
                                logger.info("File " + serverFile + " is deleted!");
                            else
                                logger.error("Delete operation is faild!");
                        }
                    }
                    blgUserUpdate.getBlgUserDetail().setUsrPhotoLink("resources/images/" + fileName);
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

    @RequestMapping(value = "/{firstName}.{lastName}/storage", method = RequestMethod.GET)
    @PreAuthorize("isFullyAuthenticated()")
    public String storage(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName,
                          Model model, HttpServletRequest request) throws IOException {
        /*TODO log*/
        //logger.info("Listing users");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (firstName.equals(((BlgUser) principal).getBlgUserDetail().getUsrDetFirstname()) && lastName.equals(((BlgUser) principal).getBlgUserDetail().getUsrDetLastname())) {
            BlgUser blgUser = blgUserService.findById(((BlgUser) principal).getUsrId());
            String path = request.getRealPath("/") + "public/images/storage/" + blgUser.getUsrId() + "/";

            String url = "/resources/images/storage/" + blgUser.getUsrId() + "/";
            List<Object> paths = new ArrayList<>();
            if (Files.exists(Paths.get(path))) {
                //paths= Lists.newArrayList(Files.walk(Paths.get(path)));
                Files.walk(Paths.get(path)).forEach(filePath -> {
                    if (Files.isRegularFile(filePath)) {
                        paths.add(url + new File(filePath.toUri()).getName());
                    }
                });
            }
            //logger.info("No. of users" + blgUser.size());
            model.addAttribute("blgUser", blgUser);
            model.addAttribute("files", paths);
            return "users/storage";
        } else {
            return "homePage";
        }
    }

    @RequestMapping(value = "/{firstName}.{lastName}/storage/download", method = RequestMethod.GET)
    @PreAuthorize("isFullyAuthenticated()")
    public String downloadImage(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName,
                                Model model, HttpServletRequest request) throws IOException {
        /*TODO log*/
        //logger.info("Listing users");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (firstName.equals(((BlgUser) principal).getBlgUserDetail().getUsrDetFirstname()) && lastName.equals(((BlgUser) principal).getBlgUserDetail().getUsrDetLastname())) {
            BlgUser blgUser = blgUserService.findById(((BlgUser) principal).getUsrId());
            String path = request.getRealPath("/") + "public/images/storage/" + blgUser.getUsrId() + "/";
            String url = "/resources/images/storage/" + blgUser.getUsrId() + "/";
            //Map<String,Map<String,Byte>> paths= new HashMap<>();
            //paths= Lists.newArrayList(Files.walk(Paths.get(path)));
            List<WrapperFile> wrapperFileList = new ArrayList<>();
            Files.walk(Paths.get(path)).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    //Map<String, Byte> map = new HashMap<String, Byte>();
                    //map.put(url+new File(filePath.toUri()).getName(), (byte) new File(filePath.toUri()).length());
                    //paths.put(new File(filePath.toUri()).getName(),map);
                    wrapperFileList.add(new WrapperFile(new File(filePath.toUri()).getName(), new File(filePath.toUri()).length(), url + new File(filePath.toUri()).getName()));
                }
            });
            //logger.info("No. of users" + blgUser.size());
            //model.addAttribute("blgUser",blgUser);
            model.addAttribute("files", wrapperFileList);
            return "users/storage/download";
            //return new ResponseEntity(paths,HttpStatus.OK);
        } else {
            //return new ResponseEntity(HttpStatus.FORBIDDEN);
            return "homePage";
        }

    }

    @RequestMapping(value = "/{firstName}.{lastName}/storage/delete", method = RequestMethod.POST)
    @PreAuthorize("isFullyAuthenticated()")
    public ResponseEntity deleteImage(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName,
                                      @RequestParam(value = "files") List<String> files, HttpServletRequest request) throws IOException {
        /*TODO log*/
        //logger.info("Listing users");
        if (!files.isEmpty()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (firstName.equals(((BlgUser) principal).getBlgUserDetail().getUsrDetFirstname()) && lastName.equals(((BlgUser) principal).getBlgUserDetail().getUsrDetLastname())) {
                BlgUser blgUser = blgUserService.findById(((BlgUser) principal).getUsrId());

                String path = request.getRealPath("/") + "public/images/storage/" + blgUser.getUsrId() + "/";
                //String url = UrlUtil.sourcePathFile(request, "/resources/images/storage/" + blgUser.getUsrId() + "/");
                //Map<String,Map<String,Byte>> paths= new HashMap<>();
                //paths= Lists.newArrayList(Files.walk(Paths.get(path)));
                /*List<WrapperFile> wrapperFileList = new ArrayList<>();
                Files.walk(Paths.get(path)).forEach(filePath -> {
                    if (Files.isRegularFile(filePath)) {
                        //Map<String, Byte> map = new HashMap<String, Byte>();
                        //map.put(url+new File(filePath.toUri()).getName(), (byte) new File(filePath.toUri()).length());
                        //paths.put(new File(filePath.toUri()).getName(),map);
                        wrapperFileList.add(new WrapperFile(new File(filePath.toUri()).getName(), new File(filePath.toUri()).length(), url + new File(filePath.toUri()).getName()));
                    }
                });*/
                //logger.info("No. of users" + blgUser.size());
                //model.addAttribute("blgUser",blgUser);
                List<String> filesDeleted = new ArrayList<>();
                files.forEach(fileName -> {
                    File file = new File(path + fileName);
                    if (file.delete()) {
                        filesDeleted.add(fileName);
                    }
                });
                return new ResponseEntity(filesDeleted, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
        } else
            return new ResponseEntity("No files for delete!", HttpStatus.OK);
    }


    /*TODO translate & comments*/
    @RequestMapping(value = "/{firstName}.{lastName}/storage/upload", method = RequestMethod.POST)
    public ResponseEntity uploadImage(@RequestParam(value = "files") List<MultipartFile> files, HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BlgUser blgUser = blgUserService.findById(((BlgUser) principal).getUsrId());
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                return new ResponseEntity("Invalid file!", HttpStatus.BAD_REQUEST);
            } else {
                String fileName = file.getOriginalFilename();
                byte[] bytes = new byte[0];

                try {
                    bytes = file.getBytes();
                    String path = request.getRealPath("/") + "public/images/storage/" + blgUser.getUsrId() + "/";
                    if (!Files.exists(Paths.get(path))) {
                        if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
                            Files.createDirectory(Paths.get(path));
                        } else {
                            Set<PosixFilePermission> perms =
                                    PosixFilePermissions.fromString("rwxrw-rw-");
                            FileAttribute<Set<PosixFilePermission>> attr =
                                    PosixFilePermissions.asFileAttribute(perms);
                            Files.createDirectory(Paths.get(path), attr);
                        }
                    }
                    if (Files.exists(Paths.get(path)) && Files.isReadable(Paths.get(path))) {
                        File serverFile = new File(path + fileName);
                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                        stream.write(bytes);
                        stream.close();
                        //ImageCropper.resizeImage(serverFile, origHeight, origWidth, "png");
                        //ImageCropper.cropp(serverFile, "png", height, width, left, top);
                        /*String oldFilePath = blgUserUpdate.getBlgUserDetail().getUsrPhotoLink();

                        if (!oldFilePath.equals(UrlUtil.sourcePathFile(request, "/resources/images/" + fileName))) {
                            File oldFile = new File(path + "public/images/" + oldFilePath.substring(oldFilePath.lastIndexOf("/") + 1, oldFilePath.length()));
                            if (oldFile.delete())
                                logger.info("File " + serverFile + " is deleted!");
                            else
                                logger.error("Delete operation is faild!");
                        }*/

                        //blgUserUpdate.getBlgUserDetail().setUsrPhotoLink(UrlUtil.sourcePathFile(request, "/resources/images/" + fileName));
                        //blgUserService.save(blgUserUpdate);
                        /*Principal update*/
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return new ResponseEntity(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }
        }
        return new ResponseEntity("Your image was updated!", HttpStatus.OK);
    }

    /*@RequestMapping(value = "/{category}/{category}", method = RequestMethod.GET)
    public String homeWithCategory(@RequestParam(value = "rows", defaultValue = "5", required = false) int rows,
                                   @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                   @PathVariable("category") String category,
                                   Model model) {
        Sort sort = new Sort(Sort.Direction.DESC,"pstTimeCreate");
        Page<BlgPost> blgPostPage = blgPostService.findByCat(category,true,new PageRequest(page, rows, sort));

        List<BlgDicCategory> blgDicCategoryList = blgDicCategoryService.findAll();
        model.addAttribute("posts", blgPostPage);
        model.addAttribute("categories", blgDicCategoryList);
        return "homePage";
    }*/

}
