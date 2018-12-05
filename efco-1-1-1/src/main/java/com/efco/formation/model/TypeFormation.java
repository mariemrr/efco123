package com.efco.formation.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
public class TypeFormation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titre;
	@Column(columnDefinition = "TEXT")
	private String description;
	private int status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at = new Date();

	@OneToMany(fetch = FetchType.LAZY,
			cascade =  CascadeType.ALL,
			mappedBy = "typeFormaton")
	private List<SousTypeFormation> sous_type_formations;

	@PreUpdate
	public void setLastUpdate() {  this.updated_at = new Date(); }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sous_type_formations == null) ? 0 : sous_type_formations.hashCode());
		result = prime * result + status;
		result = prime * result + ((titre == null) ? 0 : titre.hashCode());
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
		TypeFormation other = (TypeFormation) obj;
		if (created_at == null) {
			if (other.created_at != null)
				return false;
		} else if (!created_at.equals(other.created_at))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sous_type_formations == null) {
			if (other.sous_type_formations != null)
				return false;
		} else if (!sous_type_formations.equals(other.sous_type_formations))
			return false;
		if (status != other.status)
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		if (updated_at == null) {
			if (other.updated_at != null)
				return false;
		} else if (!updated_at.equals(other.updated_at))
			return false;
		return true;
	}



	public TypeFormation(Long id, String titre, String description, int status, Date created_at, Date updated_at,
			List<SousTypeFormation> sous_type_formations) {
		super();
		this.id = id;
		this.titre = titre;
		this.description = description;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.sous_type_formations = sous_type_formations;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTitre() {
		return titre;
	}



	public void setTitre(String titre) {
		this.titre = titre;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
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



	public List<SousTypeFormation> getSous_type_formations() {
		return sous_type_formations;
	}



	public void setSous_type_formations(List<SousTypeFormation> sous_type_formations) {
		this.sous_type_formations = sous_type_formations;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public TypeFormation()
	{

	}

}
