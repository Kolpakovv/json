package com.jsonpostgres.controllers;
import com.jsonpostgres.entities.Greetings;
import com.jsonpostgres.entities.Person;
import com.jsonpostgres.repositories.PersonRepository;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Logger;

import static com.jsonpostgres.controllers.GreetingController.containemail;
import static com.jsonpostgres.controllers.GreetingController.md5Apache;

@Controller
@RequestMapping("/facebookConnect")
public class FBController {
    @GetMapping("/facebookConnect")
    @RequestMapping(value="/facebookConnect", method = RequestMethod.GET)
    public String FaceForm(Model model, HttpServletRequest request) {
        model.addAttribute("facebookConnect", new Greetings());
        return "facebookConnect";}

    @RequestMapping(value = "/facebookConnect", method = RequestMethod.POST)
    public String FaceReg(@ModelAttribute Greetings facereg, Model model ,HttpServletRequest request ){
        model.addAttribute("facebookConnect", facereg );
        return "facebookConnect";

    }

}