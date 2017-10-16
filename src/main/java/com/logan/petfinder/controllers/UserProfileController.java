package com.logan.petfinder.controllers;

import com.logan.petfinder.Dao.FriendDao;
import com.logan.petfinder.Dao.UserDao;
import com.logan.petfinder.models.Friend;
import com.logan.petfinder.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserProfileController {


    @Autowired
    UserDao userDao;
    @Autowired
    FriendDao friendDao;


    @RequestMapping(value = "/user/{userId}"  )
    public String home( Principal principal,
                        @PathVariable("userId") long userId,
                        Model model){
        User me = userDao.findByUsername(principal.getName());
        model.addAttribute("user", me);
        if (userId == me.getId()) {
            model.addAttribute("user", me);
            return "myProfile";
        } else {
            model.addAttribute("user", me);
            return "profile";
        }
    }
    @RequestMapping(value = "/user/update/{userId}", method = RequestMethod.POST)
    public String home(@PathVariable("userId") long userId,
                       @RequestParam("firstName") String firstName,
                       @RequestParam("lastName") String lastName,
                       @RequestParam("phoneNum") Integer phoneNum,
                       @RequestParam("emailAddress") String emailAddress,
                       @RequestParam("regionLoc") String regionLoc,
                       Principal principal,
                       Model model){
        User me = userDao.findByUsername(principal.getName());
        me.setFname(firstName);
        me.setLname(lastName);
        me.setPhone(phoneNum);
        me.setEmail(emailAddress);
        me.setRegion(regionLoc);
        userDao.save(me);
        return "redirect:/user/" + me.getId();
    }

}