package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entite.Cours;
import org.springframework.data.repository.CrudRepository;

public interface CoursRepository extends CrudRepository<Cours, String> {

}