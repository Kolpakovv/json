package com.jsonpostgres.controllers;

import com.jsonpostgres.entities.Info;
import com.jsonpostgres.repositories.InfoRepository;
import com.jsonpostgres.repositories.PersonRepository;
import com.jsonpostgres.repositories.VkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.List;


@Controller
public class InfoController {

    @Autowired
    public InfoController(PersonRepository personRepository, VkRepository vkRepository, InfoRepository infoRepository) {
        this.personRepository = personRepository;
        this.vkRepository = vkRepository;
        this.infoRepository = infoRepository;
    }

    private PersonRepository personRepository;
    private VkRepository vkRepository;
    private InfoRepository infoRepository;


    @GetMapping("/SetInfo")
    @RequestMapping(value="/SetInfo", method = RequestMethod.GET)
    public String SetInfo(Model model, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        int arrlenght = cookies.length;
        String email="";

        for(Cookie i :cookies){
            if(i.getName().equals("email")){email = i.getValue();}
        }

        List<Info> inf = infoRepository.findByEmail(email.toLowerCase());
        Info innf = inf.get(0);
        model.addAttribute("Inform",innf);
        System.out.print("emaillllll: ");
        System.out.println(innf.getEmail());

        return "SetInfo";}



    @RequestMapping(value="/SetInfo", method = RequestMethod.POST)
    public String ChangeInfo (@ModelAttribute Info info, Model model, HttpServletRequest request) {
        model.addAttribute("Indorm", info);
        System.out.println(info.getEmail());
        System.out.println(info.getAge());
        System.out.println(info.getCity());

        Connection c = null;
        Statement stmt = null;
        try {
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/postgres",
                                "spring", "spring");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("Opened database successfully");
            stmt = c.prepareStatement("UPDATE json.info SET firstname = (?), secondname = (?) ,email = (?), age = (?), country = (?), city = (?) WHERE id = (?);");
            ((PreparedStatement) stmt).setString(1, info.getFirstname());
            ((PreparedStatement) stmt).setString(2, info.getSecondname());
            ((PreparedStatement) stmt).setString(3, info.getEmail());
            ((PreparedStatement) stmt).setInt(4, info.getAge());
            ((PreparedStatement) stmt).setString(5, info.getCountry());
            ((PreparedStatement) stmt).setString(6, info.getCity());
            ((PreparedStatement) stmt).setLong(7, info.getId());
            System.out.println(stmt);
            ((PreparedStatement) stmt).executeQuery();

        }catch (Throwable error) {}
        return ("InfoSetSuccess");
    }













}
