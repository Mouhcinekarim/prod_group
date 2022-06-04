package com.gestion_pfe.prod.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="departement")



public class Departement implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="departement_id")
	private Integer departementId;
	
	@Column(name="nom_departement")
	private String nomDepartement;
	

	@OneToMany(
		    mappedBy="departement",
			fetch=FetchType.LAZY,
			cascade=CascadeType.ALL
			
			)
	@JsonIgnore
	List<Prof> profs = new ArrayList<>();
	@OneToMany(
		    mappedBy="departement",
			fetch=FetchType.LAZY,
			cascade=CascadeType.ALL
			
			)
	List<Groupe> groups=new ArrayList<>();
	// helpers methods
	public void addProf(Prof prof) {
		profs.add(prof);
		prof.setDepartement(this);
		
	}
	public void addGroup(Groupe groupe) {
		this.groups.add(groupe);
       groupe.setDepartement(this);
	}
	public void removeProf(Prof prof) {
		profs.remove(prof);
		prof.setDepartement(null);
	}	
}

