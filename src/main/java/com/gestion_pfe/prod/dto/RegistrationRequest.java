package com.gestion_pfe.prod.dto;


import com.gestion_pfe.prod.model.Etudiant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
	 private String Email;
	   private String Password;
	   private String niveau;
	   private Integer  depatementId;
	   private Etudiant[] etudiants;
}
