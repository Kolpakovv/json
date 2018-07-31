package com.jsonpostgres.controllers;

import com.jsonpostgres.entities.Greetings;
import com.jsonpostgres.entities.Person;
import com.jsonpostgres.entities.Vk;
import com.jsonpostgres.repositories.PersonRepository;
import com.jsonpostgres.repositories.VkRepository;
import org.apache.commons.codec.digest.DigestUtils;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;



@Controller
public class VkController {

    public static String md5Apache(String st) {
        String md5Hex = DigestUtils.md5Hex(st);
        return md5Hex;}

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
        people.setEmail(email.toLowerCase());
        men.setEmail(email.toLowerCase());


        try {
            List<Vk> vks = vkRepository.findByEmail(men.getEmail());
            List<Person> prs = personRepository.findByEmail(people.getEmail());


            if (VkController.containemail(vks,men.getEmail())) {return ("/VkRegResult");
            }else {
                if(GreetingController.containemail(prs,people.getEmail()))
                {   for(Person cont : prs){ if(cont.getEmail().equals(email)){

                                                                        men.setvkid(Vkid);
                                                                        men.setId(cont.getId());

                                                                        men.setPerson(cont);
                                                                        cont.setVk(men);

                    Connection c = null;
                    Statement stmt = null;
                    try {
                        Class.forName("org.postgresql.Driver");
                        c = DriverManager
                                .getConnection("jdbc:postgresql://localhost:5432/postgres",
                                        "spring", "spring");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println(e.getClass().getName()+": "+e.getMessage());
                        System.exit(0);
                    }
                    System.out.println("Opened database successfully");
                    stmt = c.prepareStatement("INSERT INTO json.vk VALUES(?,?,?);");
                    ((PreparedStatement) stmt).setLong(1,  men.getId());
                    ((PreparedStatement) stmt).setString(2, men.getvkid());
                    ((PreparedStatement) stmt).setString(3,men.getEmail());
                    ((PreparedStatement) stmt).executeQuery();




                                                                        return ("VkRegResult");


                                                                 }System.out.println("oshibka");


                                        }

                 }else {
                    men.setPerson(people);
                    people.setVk(men);
                    people.setId(people.getId());

                    men.setvkid(Vkid);

                    personRepository.save(people);
                vkRepository.save(men);
                logger.info("Record saved.");}




            }
        }catch (Throwable error) {System.out.print("error"); }

        return "VkRegResult";}


    @RequestMapping(value = "/VkRegResult", method = RequestMethod.POST)
    public String VkRegResult(@ModelAttribute Greetings vkres, Model model, HttpServletRequest request){
        model.addAttribute("VkRegResult", vkres);
        Cookie[] cookies = request.getCookies();
        String email="";
        for(Cookie i :cookies){
            if(i.getName().equals("email")){email = i.getValue();}
        }

        Person people = new Person();
        people.setEmail(email);
        try {
            List<Person> prs = personRepository.findByEmail(people.getEmail());



                if(GreetingController.containemail(prs,people.getEmail()))
                {   for(Person cont : prs){ if(cont.getEmail().equals(email)){
                        System.out.println(cont.getId());

                    Connection c = null;
                    Statement stmt = null;
                    try {
                        Class.forName("org.postgresql.Driver");
                        c = DriverManager
                                .getConnection("jdbc:postgresql://localhost:5432/postgres",
                                        "spring", "spring");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println(e.getClass().getName()+": "+e.getMessage());
                        System.exit(0);
                    }
                    System.out.println("Opened database successfully");
                    stmt = c.prepareStatement("UPDATE json.person SET pass = (?) WHERE id = (?);");
                    ((PreparedStatement) stmt).setString(1,  md5Apache(vkres.getPass()));
                    ((PreparedStatement) stmt).setLong(2, cont.getId());
                    System.out.println(stmt);
                    ((PreparedStatement) stmt).executeQuery();




                    return ("VkRegResult");


                }System.out.println("oshibka");


                }

                }





        }catch (Throwable error) {}

        return "VkRegResult";}





    }



