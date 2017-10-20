package com.logan.petfinder.controllers;
import com.logan.petfinder.Dao.PetDao;
import com.logan.petfinder.Dao.UserDao;
import com.logan.petfinder.models.Pet;
import com.logan.petfinder.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class PetController {
    @Autowired
    UserDao userDao;
    @Autowired
    PetDao petDao;

    @RequestMapping(value = "/pets/{userId}" )
    public String home( Principal principal,
                        @PathVariable("userId") long userId,
                        Model model){
        User me = userDao.findByUsername(principal.getName());
        model.addAttribute("userPet", petDao.findAllByUser(me));
        model.addAttribute("pet", new Pet());
        model.addAttribute("user", me);
        return "pets";
    }
    @RequestMapping(value = "/pets/{userId}", method = RequestMethod.POST)
    public String add(@ModelAttribute Pet newPet,
                      Principal principal,
                      @PathVariable("userId") long userId,
                      @RequestParam("petOwner") long id){
        User me = userDao.findByUsername(principal.getName());
        newPet.setUser(userDao.findOne(id));
        petDao.save(newPet);
        return "redirect:/pets/" + me.getId();
    }
    @RequestMapping(value = "/pets/delete/{userId}", method = RequestMethod.POST)
    public String delete(
                         @PathVariable("userId") Long id,
                         @RequestParam("petId") Integer petId,
                         Principal principal,
                      Model model){
        User me = userDao.findByUsername(principal.getName());
        Pet pet = petDao.findById(petId);
        petDao.delete(pet);
        System.out.println(id);
        return "redirect:/pets/" + me.getId();
    }
    @RequestMapping(value = "/pet/details/{userId}/{petId}")
    public String review(Model model,
                         Principal principal,
                         @PathVariable("userId") long userId,
                         @PathVariable("petId") long petId){
        User me = userDao.findByUsername(principal.getName());
        Pet findPet = petDao.findOne(petId);
        model.addAttribute("user", me);
        model.addAttribute("userPet", petDao.findOne(petId));
        model.addAttribute("pet", findPet);
        return "petDetails";
    }
    @RequestMapping(value = "/pet/details/update/{id}", method = RequestMethod.POST)
    public String update(@ModelAttribute Pet updatePet,
                         Principal principal) {
        User me = userDao.findByUsername(principal.getName());
        updatePet.setUser(userDao.findOne(me.getId()));
        petDao.save(updatePet);
        return "redirect:/pets/"+ me.getId();
    }
}
