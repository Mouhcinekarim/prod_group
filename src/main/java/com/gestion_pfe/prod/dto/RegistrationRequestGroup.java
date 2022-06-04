package com.gestion_pfe.prod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestGroup {
	
	private String email;
	private String password;
	private String Niveau; // normalement foreign key
	private String departement; // normalement foreign key
	private int nombre_etudiant;
}
