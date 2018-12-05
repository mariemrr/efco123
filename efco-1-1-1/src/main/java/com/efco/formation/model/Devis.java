package com.efco.formation.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Devis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nom;

	private String email;

	private long sous_type_formation;

	private String num_tel;
	
	private long nombre_stagiaire;

	private String sous_type_formation_name;
	
	private int status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at = new Date();


	@PreUpdate
	public void setLastUpdate() {  this.updated_at = new Date(); }

	public Devis()
	{

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getType_formation() {
		return sous_type_formation;
	}

	public void setType_formation(long sous_type_formation) {
		this.sous_type_formation = sous_type_formation;
	}

	public String getNum_tel() {
		return num_tel;
	}

	public void setNum_tel(String num_tel) {
		this.num_tel = num_tel;
	}

	public long getNombre_stagiaire() {
		return nombre_stagiaire;
	}

	public void setNombre_stagiaire(long nombre_stagiaire) {
		this.nombre_stagiaire = nombre_stagiaire;
	}

	public String getType_formation_name() {
		return sous_type_formation_name;
	}

	public void setType_formation_name(String type_formation_name) {
		this.sous_type_formation_name = type_formation_name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Devis(long id, String nom, String email, long type_formation, String num_tel, long nombre_stagiaire,
			String sous_type_formation_name, int status, Date created_at, Date updated_at) {
		super();
		this.id = id;
		this.nom = nom;
		this.email = email;
		this.sous_type_formation = sous_type_formation;
		this.num_tel = num_tel;
		this.nombre_stagiaire = nombre_stagiaire;
		this.sous_type_formation_name = sous_type_formation_name;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + (int) (nombre_stagiaire ^ (nombre_stagiaire >>> 32));
		result = prime * result + ((num_tel == null) ? 0 : num_tel.hashCode());
		result = prime * result + status;
		result = prime * result + (int) (sous_type_formation ^ (sous_type_formation >>> 32));
		result = prime * result + ((sous_type_formation_name == null) ? 0 : sous_type_formation_name.hashCode());
		result = prime * result + ((updated_at == null) ? 0 : updated_at.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Devis other = (Devis) obj;
		if (created_at == null) {
			if (other.created_at != null)
				return false;
		} else if (!created_at.equals(other.created_at))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (nombre_stagiaire != other.nombre_stagiaire)
			return false;
		if (num_tel == null) {
			if (other.num_tel != null)
				return false;
		} else if (!num_tel.equals(other.num_tel))
			return false;
		if (status != other.status)
			return false;
		if (sous_type_formation != other.sous_type_formation)
			return false;
		if (sous_type_formation_name == null) {
			if (other.sous_type_formation_name != null)
				return false;
		} else if (!sous_type_formation_name.equals(other.sous_type_formation_name))
			return false;
		if (updated_at == null) {
			if (other.updated_at != null)
				return false;
		} else if (!updated_at.equals(other.updated_at))
			return false;
		return true;
	}


	
}
