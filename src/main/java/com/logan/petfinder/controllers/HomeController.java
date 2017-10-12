package com.logan.petfinder.controllers;

import com.logan.petfinder.Dao.UserDao;
import com.logan.petfinder.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
@Controller
public class HomeController {

    @Autowired
    UserDao userDao;


    @RequestMapping(value = "/home" )
    public String home( Principal principal,
                       Model model){
        User me = userDao.findByUsername(principal.getName());
        model.addAttribute("user", me);
        return "home";
    }
    @RequestMapping(value = "/net")
    public String net( Principal principal,
                       Model model){
        User me = userDao.findByUsername(principal.getName());
        model.addAttribute("user", me);
       return "net";
    }
//

}
