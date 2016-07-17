package pl.upir.blog.web.app.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.upir.blog.entity.BlgDicCategory;
import pl.upir.blog.entity.BlgDicTag;
import pl.upir.blog.entity.BlgPost;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.service.BlgDicCategoryService;
import pl.upir.blog.service.BlgDicTagService;
import pl.upir.blog.service.BlgPostService;
import pl.upir.blog.service.BlgUserService;
import pl.upir.blog.service.security.BlgUserSecurityServiceImpl;
import pl.upir.blog.web.error.BlgExceptionForbiden;
import pl.upir.blog.web.form.FormPostPagination;
import pl.upir.blog.web.form.Message;
import pl.upir.blog.web.util.ImageCropper;
import pl.upir.blog.web.util.UrlUtil;
import pl.upir.blog.web.validator.BlgPostCustomValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Autowired
    BlgDicCategoryService blgDicCategoryService;
    @Autowired
    BlgDicTagService blgDicTagService;

    @Autowired
    BlgPostCustomValidator blgPostCustomValidator;

    @RequestMapping(value = "/{year:\\d+}/{month:\\d+}/{day:\\d+}/{id}", method = RequestMethod.GET)
    public String home(@PathVariable("year") String year, @PathVariable("month") String month, @PathVariable("day") String day,
                       @PathVariable(value = "id") int id, Model model, HttpServletRequest request) throws URISyntaxException {
        BlgPost blgPost = blgPostService.findById(id);


        String fbContent = Jsoup.parse(blgPost.getPstDocumentShort()).text();
        Document document = Jsoup.parse(blgPost.getPstDocument());
        document.setBaseUri(new URI(UrlUtil.sourcePathUrl(request, "")).toString());

        Elements el = document.getElementsByTag("img");
        List<String> fbImages = new ArrayList<>();

        el.forEach(el1 -> {
            if (el1.absUrl("src").isEmpty()) {
                fbImages.add(el1.attr("abs:src"));
            } else
                fbImages.add(el1.absUrl("src"));
        });
        model.addAttribute("fbImages", fbImages);
        model.addAttribute("fbContent", fbContent);
        model.addAttribute("post", blgPost);
        return "post/view";
    }

    @Secured("IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/{firstName}.{lastName}/post/create", method = RequestMethod.GET)
    //@RequestMapping(value = "/post/create", method = RequestMethod.GET)
    public String home(Model model) {

        //Set<BlgDicTag> blgDicTagList = new HashSet<>(blgDicTagService.findAll());
        List<BlgDicTag> blgDicTagList = blgDicTagService.findAll();
        //Set<BlgPostCategory> blgPostCategories = new HashSet<>(blgDicCategoryService.findAll());
        List<BlgDicCategory> blgPostCategoriesList = blgDicCategoryService.findAll();
        BlgPost blgPost = new BlgPost();

        model.addAttribute("blgPostCatList", blgPostCategoriesList);
        model.addAttribute("blgPostTagList", blgDicTagList);
        model.addAttribute("blgPost", blgPost);
        return "post/create";
    }

    @ModelAttribute("tagCache")
    public List<BlgDicTag> getDicTag() {
        return blgDicTagService.findAll();
    }

    @Secured("IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/{firstName}.{lastName}/post/edit/{id}", method = RequestMethod.GET)
    public String editPost(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName,
                           @PathVariable("id") int id, Model model, HttpServletRequest request,Locale locale) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BlgUser blgUser = blgUserService.findById(((BlgUser) principal).getUsrId());
        BlgPost blgPost = blgPostService.findById(id);
        int usrId = blgPost.getBlgUserSet().iterator().next().getUsrId();
        if (Objects.equals(blgUser.getBlgUserDetail().getUsrDetFirstname(), firstName) && Objects.equals(blgUser.getBlgUserDetail().getUsrDetLastname(), lastName)
                && usrId == blgUser.getUsrId()|| request.isUserInRole("ADMIN")) {
            List<BlgDicTag> blgDicTagList = blgDicTagService.findAll();
            List<BlgDicCategory> blgPostCategoriesList = blgDicCategoryService.findAll();
            //model.addAttribute("message", new Message("alert alert-danger", "Oh snap!", messageSource.getMessage("save_fail_smart", new Object[]{}, locale)));
            model.addAttribute("blgPost", blgPost);
            model.addAttribute("blgPostCatList", blgPostCategoriesList);
            model.addAttribute("blgPostTagList", blgDicTagList);
            return "post/create";

        }else
         {
            throw new BlgExceptionForbiden();
        }
    }

    /*protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Set.class, "blgDicTagSet", new CustomCollectionEditor(Set.class)
        {
            protected Object convertElement(Object element)
            {
                String name = "";
                int id = 0;

                if (element instanceof String) {
                    name = (String) element;
                    BlgDicTag blgDicTag = blgDicTagService.finByDicTagName(name);
                    return name != null ? blgDicTag : null;
                }
                else
                    return null;
            }
        });


    }*/

    @InitBinder("blgPost")
    protected void initBinder(WebDataBinder binder) {

        binder.addValidators(blgPostCustomValidator);
    }

    @Secured("IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/{firstName}.{lastName}/post/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("blgPost") BlgPost blgPost, BindingResult bindingUser,
                         @RequestParam(value = "public", required = false) boolean publication,
                         @RequestParam(value = "pstId",required = false) int pstId,
                         @RequestParam(value = "moderate", required = false) boolean moderate,
                         @RequestParam(value = "preview", required = false) boolean preView,
                         Model model, HttpServletRequest request,
                         RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingUser.hasErrors()) {
            List<BlgDicTag> blgDicTagList = blgDicTagService.findAll();
            List<BlgDicCategory> blgPostCategoriesList = blgDicCategoryService.findAll();
            model.addAttribute("message", new Message("alert alert-danger", "Oh snap!", messageSource.getMessage("save_fail_smart", new Object[]{}, locale)));
            model.addAttribute("blgPost", blgPost);
            model.addAttribute("blgPostCatList", blgPostCategoriesList);
            model.addAttribute("blgPostTagList", blgDicTagList);
            return "post/create";
        }
        model.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("alert alert-success", "Well done!", messageSource.getMessage("save_success", new Object[]{}, locale)));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BlgUser blgUser = blgUserService.findById(((BlgUser) principal).getUsrId());

        if(pstId>0){
           blgPost.setPstId(pstId);
        }
        Set<BlgDicTag> blgDicTagSet = blgPost.getBlgDicTagSet();
        blgDicTagSet.forEach(blgDicTag -> blgDicTag.setDicTagId(blgDicTagService.finByDicTagName(blgDicTag.getDicTagName()).getDicTagId()));
        Set<BlgDicCategory> blgDicCategorySet = new HashSet<>();
        blgPost.getBlgDicCategorySet().forEach(blgDicCategory -> {
            blgDicCategory = blgDicCategoryService.findByTitle(blgDicCategory.getDicCatName());
            blgDicCategorySet.add(blgDicCategory);
        });

        int target = 50;
        String substr = "";
        //Document document = Jsoup.parse(blgPost.getPstDocument());
        String patt = "\\s|\\n";
        //Delete all space between tags
        String documentShort = blgPost.getPstDocument().replaceAll("[>][\\s*]+[<]", "><");
        documentShort = documentShort.replaceAll("&nbsp;", " ");
        //all spaces and \n
        Pattern pattern1 = Pattern.compile("\\s|\\n");
        //end of sentence
        Pattern pattern = Pattern.compile("[.][\\s]");
        Pattern patternCut = Pattern.compile("<cutblog>.....</cutblog>");
        Matcher matcher = patternCut.matcher(documentShort);
        if (matcher.find()) {
            target = matcher.start();
        } else {


            Matcher matcher1 = pattern1.matcher(documentShort);
            int i = 0;
            while (matcher1.find()) {
                if (i == target) {
                    target = matcher1.end();
                    break;
                }
                i++;
            }
            int j = 0;
            matcher1 = pattern.matcher(documentShort).region(target, documentShort.length());
            while (matcher1.find()) {
                if (matcher1.end() > target) {
                    target = matcher1.end();
                    j++;
                    break;
                }
            }
            pattern1 = Pattern.compile("<pre");
            matcher1 = pattern1.matcher(documentShort).region(0, target);
            if (matcher1.find()) {
                pattern1 = Pattern.compile("</pre>");
                matcher1 = pattern1.matcher(documentShort).region(target, documentShort.length());
                if (matcher1.find()) {
                    target = matcher1.end();
                }
            }
        }
        /*pattern1 = Pattern.compile("<iframe");
        matcher1 = pattern1.matcher(documentShort).region(0, target);*/
        substr = documentShort.substring(0, target);

        blgPost.setPstDocumentShort(substr);
        blgPost.setBlgDicTagSet(blgDicTagSet);
        blgPost.setBlgDicCategorySet(blgDicCategorySet);
        blgPost.getBlgUserSet().add(blgUser);



        MultipartFile file = blgPost.file;


        if (file != null) {
            String fileName = file.getOriginalFilename();
            byte[] bytes = new byte[0];
            try {
                bytes = file.getBytes();
                String path = request.getRealPath("/");
                File serverFile = new File(path + "public/images/post/" + fileName);
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                //ImageCropper.resizeImage(serverFile, origHeight, origWidth, "png");
                if (ImageCropper.getSize(serverFile).get("height") > 800 && ImageCropper.getSize(serverFile).get("height") > 600) {
                    model.addAttribute("message", new Message("alert alert-danger", "Oh snap!", messageSource.getMessage("post.image.size", new Object[]{}, locale)));
                    model.addAttribute("blgPost", blgPost);
                    List<BlgDicTag> blgDicTagList = blgDicTagService.findAll();
                    List<BlgDicCategory> blgPostCategoriesList = blgDicCategoryService.findAll();
                    model.addAttribute("blgPostCatList", blgPostCategoriesList);
                    model.addAttribute("blgPostTagList", blgDicTagList);
                    return "/{firstName}.{lastName}/post/create";
                }
                //ImageCropper.cropp(serverFile, "png", height, width, left, top);
                //String oldFilePath = blgUserUpdate.getBlgUserDetail().getUsrPhotoLink();

                /*if (!oldFilePath.equals(UrlUtil.sourcePathFile(request, "/resources/images/" + fileName))) {
                    File oldFile = new File(path + "public/images/" + oldFilePath.substring(oldFilePath.lastIndexOf("/") + 1, oldFilePath.length()));
                    if (oldFile.delete())
                        logger.info("File " + serverFile + " is deleted!");
                    else
                        logger.error("Delete operation is faild!");
                }*/

                blgPost.setPstTitleImage("/resources/images/post/" + fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
                //return ex.toString();
            }
        }

        LocalDate date = LocalDate.now();
        blgPost.setPstUrl("/" + date.getYear() + "/" + date.getMonthValue() + "/" + date.getDayOfMonth() + "/" + blgPostService.getNextAutoincrement());
        //blgPost=blgPostService.save(blgPost);

        blgPostService.save(blgPost);
        if(moderate) {

            return "redirect:/";
        }
        else if(preView) {
            redirectAttributes.addAttribute("preview",true);
            return "redirect:/{firstName}.{lastName}/post/edit/" + blgPost.getPstId();
        }else if(publication){
            if (request.isUserInRole("ADMIN")) {
                blgPost.setPstEnable(publication);
            }
            return "redirect:/";
        }
        return "redirect:/{firstName}.{lastName}/post/edit/" + blgPost.getPstId();
    }
}
