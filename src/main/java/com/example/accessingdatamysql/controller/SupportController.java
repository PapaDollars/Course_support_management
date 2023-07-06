package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entite.Cours;
import com.example.accessingdatamysql.entite.Support;
import com.example.accessingdatamysql.repository.CoursRepository;
import com.example.accessingdatamysql.repository.SupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Controller // This means that this class is a Controller
@RequestMapping(path="/")
public class SupportController {
    @Autowired
    private SupportRepository supportRepository;
    @Autowired
    private CoursRepository coursRepository;

    @RequestMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file , Model model, @RequestParam("titre")String titre, @RequestParam("auteur")String auteur, @RequestParam("cours")String cours) {
        if (file.isEmpty()) {
            model.addAttribute("message","fichier inexistant");
        }
        else {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uploadDir = "static/supports"; // Dossier de destination des fichiers téléchargés
            Support support= new Support();
            try {
                String projectDir = System.getProperty("user.dir");
                String uploadPath = Paths.get(projectDir, "src", "main", "resources", uploadDir).toString();
                File directory = new File(uploadPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                Path destinationPath = Path.of(uploadPath, fileName);
                Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
//                String fileType = Files.probeContentType(destinationPath);
                model.addAttribute("message", "File uploaded successfully.");
                if(supportRepository.findByTitreAndCours(titre,cours)==null) {
                    support.setAuteur(auteur);
                    support.setTitre(titre);
                    support.setType("file");
                    support.setCours(cours);
                    support.setFicher(fileName);
                    support.setSize(file.getSize());
                    support.setContenu(uploadPath + "\\" + fileName);
                    supportRepository.save(support);
//                    Optional<Statistique> optionalStatistique = statistiqueRepository.findById(cours);
//                    Statistique statistique = new Statistique();
//                    if (optio nalStatistique.isPresent()) {
//
//                        statistique.setCode(cours);
//                        statistique.setIntitule(coursRepository.findById(cours).get().getIntitule());
//                        statistique.setNombre_fichier(optionalStatistique.get().getNombre_fichier() + 1);
//                        statistique.setLien(optionalStatistique.get().getLien());
//                        statistique.setVolume(optionalStatistique.get().getVolume() + file.getSize());
//
//                    }
//                    else{
//
//                        statistique.setCode(cours);
//                        statistique.setIntitule(coursRepository.findById(cours).get().getIntitule());
//                        statistique.setNombre_fichier(1);
//                        statistique.setLien(0);
//                        statistique.setVolume(file.getSize());
//                    }
//                    statistiqueRepository.save(statistique);
                }
                else{
                    model.addAttribute("error","Veillez changez un autre titre!!!");
                }

                Optional<Cours> optionalCours = coursRepository.findById(cours);
                Cours cours1 =optionalCours.get();
                model.addAttribute("cours",cours1);
                List<Support> supportList= supportRepository.findByCours(cours);
                model.addAttribute("supportList",supportList);
                float l = file.getSize() / (1024 * 1024);
                model.addAttribute("size",l);
            } catch (IOException e) {

            }
        }
        return "examples/supportList";
    }
}

