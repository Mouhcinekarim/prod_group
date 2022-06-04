package com.gestion_pfe.prod.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import  com.gestion_pfe.prod.model.Departement;
import com.gestion_pfe.prod.model.Etudiant;
import com.gestion_pfe.prod.model.PfeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="groupe")
public class Groupe  implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   private  int GroupId;
   private String email;
   private String Password;
   private String niveau;
   private Integer Anne;
	private Instant created;//
	private boolean isEnabled;//
   @OneToMany(
		    mappedBy="group",
			fetch=FetchType.LAZY,
			cascade=CascadeType.ALL
			
			)
  
  private   List<Etudiant> etudiants;
   @OneToOne()
   
   PfeInfo pfeinfo;
   @ManyToOne()
   @JoinColumn(name="departement_id")
   @JsonIgnore
  private Departement departement;
  
   
   
   
   public void AddEtudiant(List<Etudiant> etudiant) {
	   etudiant.forEach((e)->{
//		   this.etudiants.add(e);
		   e.setGroup(this);
		   
	   });
   }
}
