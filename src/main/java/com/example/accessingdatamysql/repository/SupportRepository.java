package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entite.Support;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupportRepository extends CrudRepository<Support, Integer> {
    List<Support> findByCours(String cours);

    Support findByTitreAndCours(String titre,String cours);
    @Transactional
    void  deleteByCours(String cours);
}