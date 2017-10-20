package com.logan.petfinder.controllers;
import com.logan.petfinder.Dao.FriendDao;
import com.logan.petfinder.Dao.UserDao;
import com.logan.petfinder.Dao.ZipDao;
import com.logan.petfinder.models.User;
import com.logan.petfinder.models.Zip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
@Controller
public class UserProfileController {
    @Autowired
    ZipDao zipDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FriendDao friendDao;
    @RequestMapping(value = "/user/{userId}" )
    public String home( Principal principal,
                        @PathVariable("userId") long userId,
                        Model model){
        User me = userDao.findByUsername(principal.getName());
        model.addAttribute("user", me);
        if (userId == me.getId()) {
            if (me.getZip() == null){
                model.addAttribute("myzip" );
                return "myProfile";
            }
            String myZip = me.getZip().getZipcode();
            model.addAttribute("myzip", myZip);
            return "myProfile";
        } else {
            if (userDao.findById(userId) == null) {
                return "redirect:/home";
            }
            User notMe = userDao.findById(userId);
            model.addAttribute("otherUser", notMe);
            return "profile";
        }
    }
    @RequestMapping(value = "/user/update/{userId}", method = RequestMethod.POST)
    public String home(@PathVariable("userId") long userId,
                       @RequestParam("firstName") String firstName,
                       @RequestParam("lastName") String lastName,
                       @RequestParam("phoneNum") Integer phoneNum,
                       @RequestParam("emailAddress") String emailAddress,
                       @RequestParam("zip") String strZip,
                       Principal principal,
                       Model model){
        User me = userDao.findByUsername(principal.getName());
        Zip zipTst = zipDao.findByZipcode(strZip);
        //tst.getId();
       System.out.println(zipTst.getZipcode());

        me.setFname(firstName);
        me.setLname(lastName);
        me.setPhone(phoneNum);
        me.setEmail(emailAddress);
        //check for full null for safety
        me.setZip(zipTst);
        userDao.save(me);
        return "redirect:/user/" + me.getId();
    }

}