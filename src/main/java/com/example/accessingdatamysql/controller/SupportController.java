package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entite.Cours;
import com.example.accessingdatamysql.entite.Support;
import com.example.accessingdatamysql.repository.CoursRepository;
import com.example.accessingdatamysql.repository.SupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller // This means that this class is a Controller
@RequestMapping(path="/")
public class SupportController {
    @Autowired
    private SupportRepository supportRepository;
    @RequestMapping(path="/addSupport", method= RequestMethod.POST)
    public String addNewCours (@ModelAttribute Support support, Model model) {
        Optional<Support> optionalCours = supportRepository.findById(support.getTitre());
        if (optionalCours.isPresent()) {
            List<Support> supportList = (List<Support>) supportRepository.findAll();
            model.addAttribute("supportList", supportList);
            model.addAttribute("errorMessage", "Le nom du support déjà ajouté!!!."); // Ajouter un message d'erreur au modèle
            return "examples/dashboard";
        } else {
            supportRepository.save(support);
            List<Support> supportList = (List<Support>) supportRepository.findAll();
            model.addAttribute("supportList", supportList);
            return "examples/dashboard"; // Renvoyer la vue pour afficher un message de cours non trouvé
        }
    }
}
