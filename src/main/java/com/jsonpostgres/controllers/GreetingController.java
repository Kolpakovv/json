package com.jsonpostgres.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jsonpostgres.entities.Greetings;
import com.jsonpostgres.entities.Person;
import com.jsonpostgres.repositories.PersonRepository;
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

    @RequestMapping(value = "/greeting", method = RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Greetings greeting, Model model){
    model.addAttribute("greeting", greeting);



        Person people = new Person();
        people.setId(greeting.getId());
        people.setEmail(greeting.getEmail());
        people.setpass(greeting.getPass());

        /*try {

            personRepository.findAll();

            logger.info("Record saved.");
        }catch (Throwable error) {System.out.print("xernya");

        }*/
        try {

            List <Person> persons = personRepository.findByEmail(people.getEmail());

            if (containemail(persons,people.getEmail()) == true) {
                if(containspass(persons,people.getpass()) == true){System.out.println("uspex");}
                else {System.out.println("neverny parol");}

            }else {System.out.println("Polzovatel ne naiden");}



            /*for (Person person : persons) {
                System.out.println(people.getEmail());

            }*/
            logger.info("Record saved.");
        }catch (Throwable error) {System.out.print("xernya");

        }

        return "result";

    }

}

