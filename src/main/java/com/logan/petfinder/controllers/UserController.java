package com.logan.petfinder.controllers;

import com.logan.petfinder.Dao.RoleDao;
import com.logan.petfinder.Dao.UserDao;
import com.logan.petfinder.models.Role;
import com.logan.petfinder.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupForm(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }
    @RequestMapping(value = "/signup" , method = RequestMethod.POST)
    public String signupForm(Model model,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam("username") String username){
        User user = new User();
        Role userRole = roleDao.findByName("ROLE_USER");
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        user.setEmail(email);
        user.setRole(userRole);
        user.setUsername(username);
        user.setActive(true);

        userDao.save(user);
        return "redirect:/login";
    }

}