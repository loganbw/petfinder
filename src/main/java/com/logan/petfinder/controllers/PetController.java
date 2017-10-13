package com.logan.petfinder.controllers;

import com.logan.petfinder.Dao.PetDao;
import com.logan.petfinder.Dao.UserDao;
import com.logan.petfinder.models.Pet;
import com.logan.petfinder.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class PetController {
    @Autowired
    UserDao userDao;
    @Autowired
    PetDao petDao;

    @RequestMapping(value = "/pets" )
    public String home( Principal principal,
                        Model model){
        User me = userDao.findByUsername(principal.getName());
        model.addAttribute("pet", new Pet());
        model.addAttribute("user", me);
        return "pets";
    }
    @RequestMapping(value = "/pets", method = RequestMethod.POST)
    public String add(@ModelAttribute Pet newPet,
                      @RequestParam("petOwner") User user){
        Pet pet = new Pet();
        pet.setUser(user);
        petDao.save(newPet);
        return "redirect:/pets";
    }
}
