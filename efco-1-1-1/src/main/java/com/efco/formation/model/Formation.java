package com.efco.formation.model;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
public class Formation implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String titre;
	private String image;
	@Column(columnDefinition = "TEXT")
	private String description;
	private int status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at = new Date();


	@PreUpdate
	public void setLastUpdate() {  this.updated_at = new Date(); }

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sous_type_formation_id",nullable = false)
	private SousTypeFormation sous_type;


	@OneToMany(mappedBy="formation",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SessionFormation> list_session_formation = new ArrayList<>();

	public Formation()
	{

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public SousTypeFormation getSous_type() {
		return sous_type;
	}

	public void setSous_type(SousTypeFormation sous_type) {
		this.sous_type = sous_type;
	}

	public List<SessionFormation> getList_session_formation() {
		return list_session_formation;
	}

	public void setList_session_formation(List<SessionFormation> list_session_formation) {
		this.list_session_formation = list_session_formation;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((list_session_formation == null) ? 0 : list_session_formation.hashCode());
		result = prime * result + ((sous_type == null) ? 0 : sous_type.hashCode());
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
		Formation other = (Formation) obj;
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
		if (id != other.id)
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (list_session_formation == null) {
			if (other.list_session_formation != null)
				return false;
		} else if (!list_session_formation.equals(other.list_session_formation))
			return false;
		if (sous_type == null) {
			if (other.sous_type != null)
				return false;
		} else if (!sous_type.equals(other.sous_type))
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

	public Formation(long id, String titre, String image, String description, int status, Date created_at,
			Date updated_at, SousTypeFormation sous_type, List<SessionFormation> list_session_formation) {
		super();
		this.id = id;
		this.titre = titre;
		this.image = image;
		this.description = description;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.sous_type = sous_type;
		this.list_session_formation = list_session_formation;
	}
	
	
}
