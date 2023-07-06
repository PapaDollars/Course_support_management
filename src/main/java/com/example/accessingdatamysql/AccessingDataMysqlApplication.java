package com.example.accessingdatamysql;

import com.example.accessingdatamysql.entite.Cours;
import com.example.accessingdatamysql.repository.CoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@SpringBootApplication
@Controller
public class AccessingDataMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataMysqlApplication.class, args);
    }
    @Autowired
    private CoursRepository coursRepository;
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String welcome(){
        return "welcome";
    }
    @RequestMapping(value = {"/dashboard"}, method = RequestMethod.GET)
    public String dashboard(Model model){
        List<Cours> coursList = (List<Cours>) coursRepository.findAll();
        model.addAttribute("coursList", coursList);
        return "examples/dashboard";

    }

    @RequestMapping(value = {"/statistique"}, method = RequestMethod.GET)
    public String statistique (){
        return "examples/statistique";
    }
    @RequestMapping(value = {"/user"}, method = RequestMethod.GET)
    public String user(){
        return "examples/user";
    }
    @RequestMapping(value = {"/notifications"}, method = RequestMethod.GET)
    public String notifications(){
        return "examples/notifications";
    }
    @RequestMapping(value = {"/support"}, method = RequestMethod.GET)
    public String support(){
        return "examples/supportList";
    }

}