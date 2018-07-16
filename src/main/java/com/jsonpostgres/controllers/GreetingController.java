package com.jsonpostgres.controllers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jsonpostgres.entities.Greetings;
import com.jsonpostgres.entities.Person;
import com.jsonpostgres.repositories.PersonRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Controller
public class GreetingController {

    public static boolean containspass(List<Person> c, String pass) {
        for(Person o : c) {
            if(o != null && o.getpass().equals(pass)) {
                return true;
            }
        }
        return false;
    }
    public static boolean containemail(List<Person> c, String email) {
        for(Person o : c) {
            if(o != null && o.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    public static String md5Apache(String st) {
        String md5Hex = DigestUtils.md5Hex(st);
        return md5Hex;
    }
        private PersonRepository personRepository;
    @Autowired
    public GreetingController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    private final static Logger logger = LoggerFactory.getLogger(PersonController.class);
    @GetMapping("/greeting")
    @RequestMapping(value="/greeting", method = RequestMethod.GET)
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greetings());
        return "greeting";
    }
    @GetMapping("/authorization")
    @RequestMapping(value="/authorization", method = RequestMethod.GET)
    public String authorizationForm(Model model) {
        model.addAttribute("authorization", new Greetings());
        return "authorization";
    }
    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    public String AutentificationSubmit(@ModelAttribute Greetings autent, Model model){
        model.addAttribute("authorization", autent);
        Person people = new Person();
        people.setId(autent.getId());
        people.setEmail(autent.getEmail().toLowerCase());
        people.setpass(md5Apache(autent.getPass()));
        try {
            List <Person> persons = personRepository.findByEmail(people.getEmail());
            if (containemail(persons,people.getEmail()) == true) {
                if(containspass(persons,people.getpass()) == true){return "autresult";}
                else {return "autheror";}
            }else {return "autheror";}
        }catch (Throwable error) {System.out.print("xernya");
        }
        return "autresult";
    }
    @RequestMapping(value = "/greeting", method = RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Greetings greeting, Model model){
    model.addAttribute("greeting", greeting);
        Person people = new Person();
        people.setId(greeting.getId());
        people.setEmail(greeting.getEmail().toLowerCase());
        people.setpass(md5Apache(greeting.getPass()));
        try {
            List <Person> persons = personRepository.findByEmail(people.getEmail());
            if (containemail(persons,people.getEmail()) == true) {System.out.print("Polzovatel c takim Email uzhe sushestvuet");
            }else {personRepository.save(people);
            logger.info("Record saved.");
               // System.out.print(md5Apache(people.getpass()));
            }
        }catch (Throwable error) {System.out.print("xernya");
        }
        return "result";
    }
}

