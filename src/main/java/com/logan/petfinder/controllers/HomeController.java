package com.logan.petfinder.controllers;
import com.logan.petfinder.Dao.UserDao;
import com.logan.petfinder.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class HomeController {
    public static final String ACCOUNT_SID = "ACd246cda9a3dbb08a05956c6f37db14db";
    public static final String AUTH_TOKEN = "12a6a66423fe3d65c2fc5c5d86bd6e34";
    public static final String TWILIO_NUMBER = "+18643831532";

    @Autowired
    UserDao userDao;


    @RequestMapping(value = "/home" )
    public String home( Principal principal,
                       Model model){
        User me = userDao.findByUsername(principal.getName());
        model.addAttribute("user", me);
        return "home";
    }
    @RequestMapping(value = "/lost")
    public String lost() {
        sendSMS();
        return "redirect:/home";
    }
    @RequestMapping(value = "/found")
    public String found() {
        sendFound();
        return "redirect:/home";
    }
            public void sendSMS() {
        try {
            TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

            // messages
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Body", "Your pet is lost!"));
            params.add(new BasicNameValuePair("To", "18646219361")); //Add real number here
            params.add(new BasicNameValuePair("From", TWILIO_NUMBER));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
            System.out.println(message.getSid());
        }
        catch (TwilioRestException e) {
            System.out.println(e.getErrorMessage());
        }
    }
    public void sendFound() {
        try {
            AtomicReference<TwilioRestClient> client = new AtomicReference<>(new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN));

            // messages
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Body", "Your pet is found!"));
            params.add(new BasicNameValuePair("To", "18646219361")); //Add real number here
            params.add(new BasicNameValuePair("From", TWILIO_NUMBER));

            MessageFactory messageFactory = client.get().getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
            System.out.println(message.getSid());
        }
        catch (TwilioRestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

}
