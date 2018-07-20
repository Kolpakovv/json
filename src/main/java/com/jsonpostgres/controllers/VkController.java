package com.jsonpostgres.controllers;

import com.jsonpostgres.entities.Greetings;
import com.jsonpostgres.entities.Person;
import com.jsonpostgres.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.net.*;
import java.util.Enumeration;
import java.util.List;

import static com.jsonpostgres.controllers.GreetingController.containemail;
import static com.jsonpostgres.controllers.GreetingController.md5Apache;
@Controller
public class VkController {
    @Autowired
    public VkController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    private final static Logger logger = LoggerFactory.getLogger(VkController.class);
    private PersonRepository personRepository;
    @GetMapping("/VkReg")
    @RequestMapping(value="/VkReg", method = RequestMethod.GET)
    public String VkForm(Model model, HttpServletRequest request) {
        model.addAttribute("VkReg", new Greetings());
        return "VkReg";}

    @RequestMapping(value = "/VkReg", method = RequestMethod.POST)
    public String VkReg(@ModelAttribute Greetings vkreg, Model model ,HttpServletRequest request ){
        model.addAttribute("VkReg", vkreg );


        Person people = new Person();
        people.setId(vkreg.getId());
        people.setEmail(vkreg.getEmail().toLowerCase());
        people.setpass(md5Apache(vkreg.getPass()));
        try {
            List<Person> persons = personRepository.findByEmail(people.getEmail());

            if (containemail(persons,people.getEmail()) == true) {return ("/VkRegResult");
            }else {personRepository.save(people);
                logger.info("Record saved.");

            }
        }catch (Throwable error) {System.out.print("error");
        }

        return "VkRegResult";
    }

}
