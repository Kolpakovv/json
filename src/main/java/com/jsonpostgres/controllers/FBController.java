package com.jsonpostgres.controllers;
import com.jsonpostgres.entities.FB;
import com.jsonpostgres.entities.Greetings;
import com.jsonpostgres.entities.Person;
import com.jsonpostgres.repositories.FbRepository;
import com.jsonpostgres.repositories.PersonRepository;
import org.apache.commons.codec.digest.DigestUtils;
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
import org.slf4j.Logger;




@Controller
public class FBController {

    public static String md5Apache(String st) {
        String md5Hex = DigestUtils.md5Hex(st);
        return md5Hex;}

    private static boolean containemail(List<FB> c, String email) {
        for(FB o : c) {
            if(o != null && o.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Autowired
    public FBController(PersonRepository personRepository, FbRepository fbRepository) {
        this.personRepository = personRepository;
        this.fbRepository = fbRepository;
    }
    private final static Logger logger = LoggerFactory.getLogger(FBController.class);
    private PersonRepository personRepository;
    private FbRepository fbRepository;
    @GetMapping("/FBtest")
    @RequestMapping(value="/FBtest", method = RequestMethod.GET)
    public String FaceForm() {

        return "FBtest";}

    @GetMapping("/facebookConnect")
    @RequestMapping(value="/facebookConnect", method = RequestMethod.GET)
    public String facebookConnect(Model model, HttpServletRequest request) {
        model.addAttribute("facebookConnect", new Greetings());
        Cookie[] cookies = request.getCookies();
        int arrlenght = cookies.length;
        System.out.println(arrlenght);
        String fbemail="";
        String fbid="";

        for(Cookie i :cookies){
            System.out.println(i.getName());
            if(i.getName().equals("fbemail")){fbemail = i.getValue();}
            if (i.getName().equals("fbid")){fbid = i.getValue();}}

        if(fbemail.equals("")&fbid.equals("")){return "/VkError";}
        Person people = new Person();
        FB men = new FB();

        people.setEmail(fbemail.toLowerCase());
        men.setEmail(fbemail.toLowerCase());


        try {
            List<FB> vks = fbRepository.findByEmail(men.getEmail());
            List<Person> prs = personRepository.findByEmail(people.getEmail());


            if (FBController.containemail(vks,men.getEmail())) {return ("/facebookConnect");
            }else {
                if(GreetingController.containemail(prs,people.getEmail()))
                {   for(Person cont : prs){ if(cont.getEmail().equals(fbemail)){

                    men.setfbid(fbid);

                    men.setId(cont.getId());

                    men.setPerson(cont);
                    cont.setFb(men);
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
                    stmt = c.prepareStatement("INSERT INTO json.fb VALUES(?,?,?);");
                    ((PreparedStatement) stmt).setLong(1,  men.getId());
                    ((PreparedStatement) stmt).setString(2, men.getfbid());
                    ((PreparedStatement) stmt).setString(3,men.getEmail());
                    ((PreparedStatement) stmt).executeQuery();
                    return ("facebookConnect");


                }System.out.println("oshibka");


                }

                }else {
                    men.setPerson(people);
                    people.setFb(men);
                    people.setId(people.getId());

                    men.setfbid(fbid);

                    personRepository.save(people);
                    fbRepository.save(men);
                    logger.info("Record saved.");}




            }
        }catch (Throwable error) {System.out.print("error");
        }

        return "facebookConnect";}


    @RequestMapping(value = "/facebookConnect", method = RequestMethod.POST)
    public String facebookConnect(@ModelAttribute Greetings fb, Model model, HttpServletRequest request){
        model.addAttribute("facebookConnect", fb);
        Cookie[] cookies = request.getCookies();
        String email="";
        for(Cookie i :cookies){
            if(i.getName().equals("fbemail")){email = i.getValue();}
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
                ((PreparedStatement) stmt).setString(1,  md5Apache(fb.getPass()));
                ((PreparedStatement) stmt).setLong(2, cont.getId());
                System.out.println(stmt);
                ((PreparedStatement) stmt).executeQuery();




                return ("facebookConnect");


            }System.out.println("oshibka");


            }

            }





        }catch (Throwable error) {}

        return "facebookConnect";}



}