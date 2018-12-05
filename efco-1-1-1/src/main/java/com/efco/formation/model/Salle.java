package com.efco.formation.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity

public class Salle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nom;
	private String adresse;
	private String localisation;
	private int disponibilite;
	@Temporal(TemporalType.DATE)
	private Date date;
	@Temporal(TemporalType.TIME)
	private Date HeureDebut;
	@Temporal(TemporalType.TIME)
	private Date heureFin;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at = new Date();



	@PreUpdate
	public void setLastUpdate() {  this.updated_at = new Date(); }

	@OneToMany( mappedBy = "sallef",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SessionFormation> session_formations = new ArrayList<>();


	public Salle()
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


	public String getLocalisation() {
		return localisation;
	}


	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}


	public int getDisponibilite() {
		return disponibilite;
	}


	public void setDisponibilite(int disponibilite) {
		this.disponibilite = disponibilite;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Date getHeureDebut() {
		return HeureDebut;
	}


	public void setHeureDebut(Date heureDebut) {
		HeureDebut = heureDebut;
	}


	public Date getHeureFin() {
		return heureFin;
	}


	public void setHeureFin(Date heureFin) {
		this.heureFin = heureFin;
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


	public List<SessionFormation> getSession_formations() {
		return session_formations;
	}


	public void setSession_formations(List<SessionFormation> session_formations) {
		this.session_formations = session_formations;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((HeureDebut == null) ? 0 : HeureDebut.hashCode());
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + disponibilite;
		result = prime * result + ((heureFin == null) ? 0 : heureFin.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((localisation == null) ? 0 : localisation.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((session_formations == null) ? 0 : session_formations.hashCode());
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
		Salle other = (Salle) obj;
		if (HeureDebut == null) {
			if (other.HeureDebut != null)
				return false;
		} else if (!HeureDebut.equals(other.HeureDebut))
			return false;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		if (created_at == null) {
			if (other.created_at != null)
				return false;
		} else if (!created_at.equals(other.created_at))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (disponibilite != other.disponibilite)
			return false;
		if (heureFin == null) {
			if (other.heureFin != null)
				return false;
		} else if (!heureFin.equals(other.heureFin))
			return false;
		if (id != other.id)
			return false;
		if (localisation == null) {
			if (other.localisation != null)
				return false;
		} else if (!localisation.equals(other.localisation))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (session_formations == null) {
			if (other.session_formations != null)
				return false;
		} else if (!session_formations.equals(other.session_formations))
			return false;
		if (updated_at == null) {
			if (other.updated_at != null)
				return false;
		} else if (!updated_at.equals(other.updated_at))
			return false;
		return true;
	}


	
}
