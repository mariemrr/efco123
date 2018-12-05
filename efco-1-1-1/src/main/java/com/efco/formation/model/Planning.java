package com.efco.formation.model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name="planning")
public class Planning implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private User formateur;
	@ManyToMany
	private List<SessionFormation> listeFormations;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formateur == null) ? 0 : formateur.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((listeFormations == null) ? 0 : listeFormations.hashCode());
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
		Planning other = (Planning) obj;
		if (formateur == null) {
			if (other.formateur != null)
				return false;
		} else if (!formateur.equals(other.formateur))
			return false;
		if (id != other.id)
			return false;
		if (listeFormations == null) {
			if (other.listeFormations != null)
				return false;
		} else if (!listeFormations.equals(other.listeFormations))
			return false;
		return true;
	}
	public Planning(long id, User formateur, List<SessionFormation> listeFormations) {
		super();
		this.id = id;
		this.formateur = formateur;
		this.listeFormations = listeFormations;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getFormateur() {
		return formateur;
	}
	public void setFormateur(User formateur) {
		this.formateur = formateur;
	}
	public List<SessionFormation> getListeFormations() {
		return listeFormations;
	}
	public void setListeFormations(List<SessionFormation> listeFormations) {
		this.listeFormations = listeFormations;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Planning()
	{

	}

}
