package com.gestion_pfe.prod.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="pfe_info")
@ToString
public class PfeInfo implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pfe_info_id")
	private Integer PfeInfoId;
	
	private String titre;	
	
	private String niveau;	
	
	private String description;
	
	private int anne;
	
	private boolean conferm=false;
	
	
	private boolean isStage;
	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name="prof_id")
	private Prof prof;
	
	
	@OneToOne(cascade = CascadeType.ALL,
			fetch=FetchType.EAGER
			)
	PfeFichier pfefichier;
	
	
	@OneToOne()
	@JoinColumn(name="groupe_id")
    Groupe groupe;
	//hepers Method
	void SetFichier(PfeFichier fichier){
		this.pfefichier=fichier;
		
		
	}
}




















