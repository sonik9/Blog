package pl.upir.blog.web.restful.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.service.BlgUserService;


import javax.validation.Valid;
import java.util.List;

/**
 * Created by Vitalii on 17.06.2015.
 */
@Controller
@RequestMapping(value = "/rest")
public class BlgUsersController {
    private final Logger logger = LoggerFactory.getLogger(BlgUsersController.class);

    @Autowired(required = false)
    private BlgUserService blgUserService;

    @RequestMapping(value = "/userlist", method = RequestMethod.GET)
    public @ResponseBody
    List<BlgUser> listJson(Model model){
        return blgUserService.findAll();
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
