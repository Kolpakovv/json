package com.jsonpostgres.controllers;

import com.jsonpostgres.entities.Greetings;
import com.jsonpostgres.entities.Person;
import com.jsonpostgres.entities.Vk;
import com.jsonpostgres.repositories.PersonRepository;
import com.jsonpostgres.repositories.VkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.util.List;



@Controller
public class VkController {
    private static boolean containemail(List<Vk> c, String email) {
        for(Vk o : c) {
            if(o != null && o.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    @Autowired
    public VkController(PersonRepository personRepository, VkRepository vkRepository) {
        this.personRepository = personRepository;
        this.vkRepository = vkRepository;
    }
    private final static Logger logger = LoggerFactory.getLogger(VkController.class);
    private PersonRepository personRepository;
    private VkRepository vkRepository;

    @GetMapping("/VkReg")
    @RequestMapping(value="/VkReg", method = RequestMethod.GET)
    public String VkForm() {

        return "VkReg";}




    @GetMapping("/VkRegResult")
    @RequestMapping(value="/VkRegResult", method = RequestMethod.GET)
    public String VkAuthForm(Model model, HttpServletRequest request) {
        model.addAttribute("VkRegResult", new Greetings());
        Cookie[] cookies = request.getCookies();
        int arrlenght = cookies.length;
        System.out.println(arrlenght);
        String email="";
        String Vkid="";

        for(Cookie i :cookies){
           System.out.println(i.getName());
            if(i.getName().equals("email")){email = i.getValue();}
            if (i.getName().equals("id")){Vkid = i.getValue();}


       }

        if(email.equals("")&Vkid.equals("")){return "/VkError";}

        Person people = new Person();
        Vk men = new Vk();
        men.setPerson(people);
        people.setVk(men);
        people.setId(people.getId());
        people.setEmail(email.toLowerCase());


        men.setEmail(email.toLowerCase());
        men.setvkid(Vkid);


        try {
            List<Vk> vks = vkRepository.findByEmail(men.getEmail());

            if (VkController.containemail(vks,men.getEmail())) {return ("/VkRegResult");
            }else {
                personRepository.save(people);
                vkRepository.save(men);
                logger.info("Record saved.");

            }
        }catch (Throwable error) {System.out.print("error");
        }

        return "VkRegResult";}


}
