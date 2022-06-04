package com.gestion_pfe.prod.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="prof")
public class Prof implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="prof_id")
	private Integer profId;
	
	private String nom;
	
	private String prenom;
	
	private String email;
	
	private String password;
	
	
	
private Instant created;
	
    private boolean enabled;	
//    private boolean enabled;
	//@JsonIgnore
//	 @JsonBackReference
	@ManyToOne(
			
			cascade=CascadeType.ALL
			
			)
	@JoinColumn(name="departement_id",referencedColumnName="departement_id")
	@JsonIgnore
	private Departement departement;
//	@JoinColumn(name="departement_id",referencedColumnName="departement_id")
//	@JsonIgnoreProperties(value = "product", allowSetters = true)
	//private Departement departement;
	
	@OneToMany(
			mappedBy = "prof",
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
			)
	
	List<PfeInfo> pfesInfo = new ArrayList<>();
	
	// helpers methods
	public void addPfe(PfeInfo pfeInfo) {
		pfesInfo.add(pfeInfo);
		pfeInfo.setProf(this);
	}
	
	public void removePfeInfo(PfeInfo pfeInfo) {
		pfesInfo.remove(pfeInfo);
		pfeInfo.setProf(null);
	}
	
}










