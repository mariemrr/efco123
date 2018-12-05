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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "users") 
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nom;

	private String prenom;

	private String email;

	private String adresse;

	private String gender;
	
	private String password;

	private String num_tel;

	private int status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at = new Date();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@OneToOne(fetch = FetchType.LAZY,
			cascade =  CascadeType.ALL,
			mappedBy = "user")
	private Client client;

	@OneToOne(fetch = FetchType.LAZY,
			cascade =  CascadeType.ALL,
			mappedBy = "user")
	private Stagiaire stagiaire;
	
	
	@OneToMany( mappedBy = "formateur",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SessionFormation> session_formation = new ArrayList<>();


	public User()
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


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getNum_tel() {
		return num_tel;
	}


	public void setNum_tel(String num_tel) {
		this.num_tel = num_tel;
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


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public Client getClient() {
		return client;
	}


	public void setClient(Client client) {
		this.client = client;
	}


	public Stagiaire getStagiaire() {
		return stagiaire;
	}


	public void setStagiaire(Stagiaire stagiaire) {
		this.stagiaire = stagiaire;
	}


	public List<SessionFormation> getSession_formation() {
		return session_formation;
	}


	public void setSession_formation(List<SessionFormation> session_formation) {
		this.session_formation = session_formation;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public User(long id, String nom, String prenom, String email, String adresse, String gender, String password,
			String num_tel, int status, Date created_at, Date updated_at, Role role, Client client, Stagiaire stagiaire,
			List<SessionFormation> session_formation) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.adresse = adresse;
		this.gender = gender;
		this.password = password;
		this.num_tel = num_tel;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.role = role;
		this.client = client;
		this.stagiaire = stagiaire;
		this.session_formation = session_formation;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((num_tel == null) ? 0 : num_tel.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((session_formation == null) ? 0 : session_formation.hashCode());
		result = prime * result + ((stagiaire == null) ? 0 : stagiaire.hashCode());
		result = prime * result + status;
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
		User other = (User) obj;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
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
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id != other.id)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (num_tel == null) {
			if (other.num_tel != null)
				return false;
		} else if (!num_tel.equals(other.num_tel))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (session_formation == null) {
			if (other.session_formation != null)
				return false;
		} else if (!session_formation.equals(other.session_formation))
			return false;
		if (stagiaire == null) {
			if (other.stagiaire != null)
				return false;
		} else if (!stagiaire.equals(other.stagiaire))
			return false;
		if (status != other.status)
			return false;
		if (updated_at == null) {
			if (other.updated_at != null)
				return false;
		} else if (!updated_at.equals(other.updated_at))
			return false;
		return true;
	}

}
