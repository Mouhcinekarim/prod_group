package com.gestion_pfe.prod.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion_pfe.prod.model.Departement;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Integer>{

}
