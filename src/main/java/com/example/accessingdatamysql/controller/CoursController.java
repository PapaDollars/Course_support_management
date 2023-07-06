package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entite.Cours;
import com.example.accessingdatamysql.entite.User;
import com.example.accessingdatamysql.repository.CoursRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller // This means that this class is a Controller
@RequestMapping(path="/")
public class CoursController {
    @Autowired
    private CoursRepository coursRepository;

    @RequestMapping(path="/addCours", method=RequestMethod.POST)
    public String addNewCours (@ModelAttribute Cours cours, Model model) {
        Optional<Cours> optionalCours = coursRepository.findById(cours.getCode());
        if (optionalCours.isPresent()) {
            List<Cours> coursList = (List<Cours>) coursRepository.findAll();
            model.addAttribute("coursList", coursList);
            model.addAttribute("errorMessage", "Le cours déjà ajouté!!!."); // Ajouter un message d'erreur au modèle
            return "examples/dashboard";
        } else {
            coursRepository.save(cours);
            List<Cours> coursList = (List<Cours>) coursRepository.findAll();
            model.addAttribute("coursList", coursList);
            return "examples/dashboard"; // Renvoyer la vue pour afficher un message de cours non trouvé
        }
    }


    @RequestMapping(path="/dashboard")
    public String getAllCours(Model model) {
        List<Cours> coursList = (List<Cours>) coursRepository.findAll();
        model.addAttribute("coursList", coursList);
        return "examples/dashboard"; // Nom de la vue à afficher, par exemple "cours-list.html"
    }
    @GetMapping("/deleteCours/{id}")
    public String deleteCours(@PathVariable("id") String code,Model model) {
        coursRepository.deleteById(code);
        List<Cours> coursList = (List<Cours>) coursRepository.findAll();
        model.addAttribute("coursList", coursList);
        return "examples/dashboard"; // Redirige vers la page qui affiche la liste des cours après la suppression
    }
    @RequestMapping(path="/modifierCours", method=RequestMethod.POST)
    public String updateCours (@ModelAttribute Cours cours, Model model) {
        coursRepository.save(cours);
        List<Cours> coursList = (List<Cours>) coursRepository.findAll();
        model.addAttribute("coursList", coursList);
        return "examples/dashboard";
    }
    @RequestMapping(path = "/search")
    public String modifierCours(@RequestParam String search,Model model) {
        Optional<Cours> optionalCours = coursRepository.findById(search);
        if (optionalCours.isPresent()) {
            Cours cours = optionalCours.get();
            List<Cours> coursList=new ArrayList<>();
            coursList.add(cours);
            model.addAttribute("coursList", coursList);
            return "examples/dashboard"; // Renvoyer la vue pour afficher les détails du cours
        } else {
            model.addAttribute("errorMessage", "Le cours n'existe pas!!!.");
            return "examples/dashboard"; // Renvoyer la vue pour afficher un message de cours non trouvé
        }
    }

    @GetMapping("/consulter/{id}")
    public String consulter(@PathVariable("id") String code,Model model) {
        Optional<Cours> optionalCours = coursRepository.findById(code);
        Cours cours = optionalCours.get();
        model.addAttribute("cours", cours);
        return "examples/supportList"; // Redirige vers la page qui affiche la liste des cours après la suppression
    }



//    @GetMapping("/lecture/{id}")
//    public String lecture(@PathVariable("id") String code,Model model) {
//        Optional<Cours> optionalCours = coursRepository.findById(code);
//        Cours cours = optionalCours.get();
//        model.addAttribute("cours", cours);
//        return "examples/lecture"; // Redirige vers la page qui affiche la liste des cours après la suppression
//    }
//    @GetMapping("/deleteLecture/{id}")
//    public String deleteLecture(@PathVariable("id") String code,Model model) {
//        coursRepository.deleteById(code);
//        List<Cours> coursList = (List<Cours>) coursRepository.findAll();
//        model.addAttribute("coursList", coursList);
//        return "examples/dashboard"; // Redirige vers la page qui affiche la liste des cours après la suppression
//    }

}
