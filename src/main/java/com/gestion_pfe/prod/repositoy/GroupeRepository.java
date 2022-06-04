package com.gestion_pfe.prod.repositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion_pfe.prod.model.Groupe;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Integer>{
	Optional<Groupe> findByEmail(String email);
}
