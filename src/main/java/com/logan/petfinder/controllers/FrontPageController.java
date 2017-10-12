package com.logan.petfinder.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontPageController {
    @RequestMapping(value = "/" )
    public String frontPage(){
        return "frontPage";
    }


}
