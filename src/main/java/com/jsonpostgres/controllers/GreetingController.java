package com.jsonpostgres.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jsonpostgres.entities.Greetings;
import com.jsonpostgres.entities.Person;
import com.jsonpostgres.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GreetingController {

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

        try {

            personRepository.save(people);

            logger.info("Record saved.");
        }catch (Throwable error) {System.out.print("xernya");

        }
        return "result";

    }

}

