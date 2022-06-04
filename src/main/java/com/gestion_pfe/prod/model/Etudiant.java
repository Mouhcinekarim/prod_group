package com.gestion_pfe.prod.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Etudiant implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
    private Integer EtudiantId;
    private String nom;
    private String prenom;
    private String Appogee;
    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name="group_id")

    private Groupe group;
}
