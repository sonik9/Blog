package pl.upir.blog.web.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.upir.blog.entity.BlgDicTag;
import pl.upir.blog.entity.BlgPost;
import pl.upir.blog.entity.BlgPostCategories;
import pl.upir.blog.service.BlgDicTagService;
import pl.upir.blog.service.BlgPostCategoriesService;
import pl.upir.blog.service.BlgPostService;
import pl.upir.blog.service.BlgUserService;
import pl.upir.blog.service.security.BlgUserSecurityServiceImpl;
import pl.upir.blog.web.form.FormPostPagination;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    BlgPostCategoriesService blgPostCategoriesService;
    @Autowired
    BlgDicTagService blgDicTagService;

    static class BlgTagCat{
        public List<BlgPostCategories> blgPostCategories;
        public List<BlgDicTag> blgDicTags;


    }
    @Secured("IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/{firstName}.{lastName}/post/create", method = RequestMethod.GET)
    public String home(Model model) {

        Set<BlgDicTag> blgDicTagList = new HashSet<>(blgDicTagService.findAll());
        Set<BlgPostCategories> blgPostCategories = new HashSet<>(blgPostCategoriesService.findAll());
        BlgPost blgPost = new BlgPost();
        model.addAttribute("blgPostCatList", blgPostCategories);
        model.addAttribute("blgPostTagList", blgDicTagList);
        model.addAttribute("blgPost", blgPost);
        return "post/create";
    }
}
