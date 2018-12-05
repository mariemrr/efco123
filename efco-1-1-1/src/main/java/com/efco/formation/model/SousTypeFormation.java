package com.efco.formation.model;

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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class SousTypeFormation implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String titre;
	private String image;
	@Column(columnDefinition = "TEXT")
	private String description;
	private int status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "type_formation_id", nullable = false)
	
	private TypeFormation typeFormaton;
	
	

	
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
	public TypeFormation getTypeFormaton() {
		return typeFormaton;
	}
	public void setTypeFormaton(TypeFormation typeFormaton) {
		this.typeFormaton = typeFormaton;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + status;
		result = prime * result + ((titre == null) ? 0 : titre.hashCode());
		result = prime * result + ((typeFormaton == null) ? 0 : typeFormaton.hashCode());
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
		SousTypeFormation other = (SousTypeFormation) obj;
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
		if (status != other.status)
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		if (typeFormaton == null) {
			if (other.typeFormaton != null)
				return false;
		} else if (!typeFormaton.equals(other.typeFormaton))
			return false;
		return true;
	}

	public SousTypeFormation(long id, String titre, String image, String description, int status,
			TypeFormation typeFormaton) {
		super();
		this.id = id;
		this.titre = titre;
		this.image = image;
		this.description = description;
		this.status = status;
		this.typeFormaton = typeFormaton;
	}

	public SousTypeFormation()
	{

	}
}
