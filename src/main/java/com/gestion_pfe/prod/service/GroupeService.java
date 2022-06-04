package com.gestion_pfe.prod.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion_pfe.prod.model.Groupe;
import com.gestion_pfe.prod.repositoy.GroupeRepository;

@Service
public class GroupeService {
	@Autowired
	private GroupeRepository groupeRepository;
	
	public Optional<Groupe> findProf(String email) {
		return groupeRepository.findByEmail(email);
	}
}
