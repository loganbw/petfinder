package com.logan.petfinder.controllers;

import com.logan.petfinder.Dao.FriendDao;
import com.logan.petfinder.Dao.UserDao;
import com.logan.petfinder.models.Friend;
import com.logan.petfinder.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NetController {

    @Autowired
    UserDao userDao;
    @Autowired
    FriendDao friendDao;


    @RequestMapping(value = "/net/{userId}"  )
    public String home( Principal principal,
                        @PathVariable("userId") long userId,
                        Model model){
        User me = userDao.findByUsername(principal.getName());
        model.addAttribute("user", me);
        if (userId == me.getId()) {
            model.addAttribute("confirmed", myFriends(me));
            model.addAttribute("pending", pendingRequests(me.getUsername()));
            model.addAttribute("user", me);
            return "net";
        } else {
            model.addAttribute("user", me);
            return "profile";
        }
    }
    @RequestMapping(value = "/net/addFriend", method = RequestMethod.POST)
    public String addFriend(Principal principal,
                            Model model,
                            @RequestParam("newFriendName")String newFriendName){
        User me = userDao.findByUsername(principal.getName());
        if (userDao.findByUsername(newFriendName) == null){
            model.addAttribute("friendError", "Cannot find user");
            model.addAttribute("user", me);
            model.addAttribute("confirmed", myFriends(me));
            model.addAttribute("pending", pendingRequests(me.getUsername()));
            return "myProfile";
        }
        for (int i = 0; i < me.getFriends().size(); i++) {
            if(me.getFriends().get(i).getFriendname().equals(newFriendName)){
                model.addAttribute("friendError", "Friend already added");
                model.addAttribute("user", me);
                model.addAttribute("confirmed", myFriends(me));
                model.addAttribute("pending", pendingRequests(me.getUsername()));
                return "myProfile";
            }
        }
        Friend newFriend = new Friend();
        newFriend.setUser(me);
        newFriend.setFriendname(newFriendName);
        newFriend.setConfirmed(false);
        friendDao.save(newFriend);
        return "redirect:/net/" + me.getId();
    }
    @RequestMapping(value = "/net/accept", method = RequestMethod.POST)
    public String acceptRequest(Principal principal,
                                @RequestParam("friendId") Integer friendId){
        User me = userDao.findByUsername(principal.getName());
        User pendingFriend = userDao.findById(friendId);
        Friend pendingRequest = friendDao.findByUserAndFriendname(pendingFriend, me.getUsername());
        Friend acceptedRequest = new Friend();
        acceptedRequest.setUser(me);
        acceptedRequest.setFriendname(pendingFriend.getUsername());
        acceptedRequest.setConfirmed(true);
        pendingRequest.setConfirmed(true);
        friendDao.save(acceptedRequest);
        friendDao.save(pendingRequest);
        return "redirect:/net/" + me.getId();
    }

    @RequestMapping(value = "/net/ignore", method = RequestMethod.POST)
    public String ignoreRequest(Principal principal,
                                @RequestParam("friendId") Integer friendId){
        User me = userDao.findByUsername(principal.getName());
        User pendingFriend = userDao.findById(friendId);
        Friend pendingRequest = friendDao.findByUserAndFriendname(pendingFriend, me.getUsername());
        friendDao.delete(pendingRequest);
        return "redirect:/net/" + me.getId();
    }
    private List<User> myFriends(User user){
        List<Friend> allFriends = user.getFriends();
        List<User> confirmedFriends = new ArrayList<>();
        for (Friend friend: allFriends) {
            if (friend.getConfirmed()){
                User confirmedFriend = userDao.findByUsername(friend.getFriendname());
                confirmedFriends.add(confirmedFriend);
            }
        }
        return confirmedFriends;
    }
    private List<User> pendingRequests(String myUsername){
        List<Friend> pendingList = friendDao.findAllByFriendnameAndConfirmedFalse(myUsername);
        List<User> pendingFriends = new ArrayList<>();
        for (Friend friend : pendingList){
            User user = friend.getUser();
            pendingFriends.add(user);
        }
        return pendingFriends;
    }
}
