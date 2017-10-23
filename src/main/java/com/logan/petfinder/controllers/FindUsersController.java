package com.logan.petfinder.controllers;

import com.logan.petfinder.Dao.FriendDao;
import com.logan.petfinder.Dao.UserDao;
import com.logan.petfinder.models.Friend;
import com.logan.petfinder.models.User;
import com.logan.petfinder.models.Zip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FindUsersController {
    @Autowired
    UserDao userDao;

    @Autowired
    FriendDao friendDao;

    @RequestMapping(value = "/find" )
    public String home( Principal principal,
                        RedirectAttributes redirAttrs,
                        Model model){
        User me = userDao.findByUsername(principal.getName());
        model.addAttribute("user", me);
        Zip myZipId = me.getZip();
        if (me.getZip() == null){
            redirAttrs.addFlashAttribute("message", "please enter your zipcode");
            return "redirect:/user/" + me.getId();
        }else {
            List<User> allUsers = userDao.findByZipAndUsernameIsNot(myZipId, me.getUsername());
            if (me.getFriends().isEmpty()) {
                model.addAttribute("noFriendsFound", "No friends added");
                model.addAttribute("localUsers", allUsers);
                return "find";
            }
            List<Friend> myFriends = me.getFriends();
            List<User> friendUsers = new ArrayList<>();
            List<User> localUsers = new ArrayList<>();
            for (User user: allUsers) {
                for (Friend friend: myFriends) {
                    if (user.getUsername().equals(friend.getFriendname())){
                        friendUsers.add(user);
                        break;
                    } else if (friendUsers.contains(userDao.findByUsername(friend.getFriendname()))){
                        continue;
                    } else {
                        localUsers.add(user);
                    }
                }
            }
            if (friendUsers.isEmpty()) {
                model.addAttribute("noFriendsFound", "No friends in your immediate area");
                model.addAttribute("localUsers", localUsers);
                return "find";
            }
            model.addAttribute("friendUsers", friendUsers);
            model.addAttribute("localUsers", localUsers);
            return "find";
        }
    }

    @RequestMapping(value = "/find/addFriend", method = RequestMethod.POST)
    public String addFriend(Principal principal,
                            @RequestParam("newFriendName") String newFriendName){
        User me = userDao.findByUsername(principal.getName());
        Friend newFriend = new Friend();
        newFriend.setUser(me);
        newFriend.setFriendname(newFriendName);
        newFriend.setConfirmed(false);
        friendDao.save(newFriend);
        return "redirect:/find";
    }
}