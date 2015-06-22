package pl.upir.blog.web.app.controller;

import org.apache.activemq.broker.region.policy.LastImageSubscriptionRecoveryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.jsp.PageContext;
import java.util.List;

/**
 * Created by Vitalii on 19.06.2015.
 */
@RequestMapping("/users")
@Controller
public class BlgUsersController {
    final Logger logger = LoggerFactory.getLogger(BlgUsersController.class);

    /*@Autowired
    private BlgUsersService blgUsersService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model){

        logger.info("Listing users");
        List<BlgUsers> blgUsersList = blgUsersService.findAll();
        model.addAttribute("blgUsers", blgUsersList);
        logger.info("No. of users"+blgUsersList.size());
        return "users/list";
    }
*/
}
