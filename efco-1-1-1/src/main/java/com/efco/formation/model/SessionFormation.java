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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@TableGenerator(name="session_formation")
public class SessionFormation implements Serializable{


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int duree;
	private String titre;
	@Temporal(TemporalType.DATE)
	private Date date_Deb;
	@Temporal(TemporalType.DATE)
	private Date date_fin;
	private int nbre_stagiaires;
	private int status;
	private String heureDeb;
	private String heureFin;
	@Column(columnDefinition="TEXT",nullable = true)
	private String link;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at = new Date();

	@PreUpdate
	public void setLastUpdate() {  this.updated_at = new Date(); }

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="salle_id", nullable=false)
	private Salle sallef;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "formateur_id")
	private User formateur;

	@ManyToMany
	private List<Stagiaire> stagiaires;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="formation_id", nullable=false)
	private Formation formation;

	@OneToMany( mappedBy = "session_formation",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SessionFormationTime> session_formation_time = new ArrayList<>();


	public SessionFormation()
	{
		this.link="#";
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public int getDuree() {
		return duree;
	}


	public void setDuree(int duree) {
		this.duree = duree;
	}


	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}


	public Date getDate_Deb() {
		return date_Deb;
	}


	public void setDate_Deb(Date date_Deb) {
		this.date_Deb = date_Deb;
	}


	public Date getDate_fin() {
		return date_fin;
	}


	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}


	public int getNbre_stagiaires() {
		return nbre_stagiaires;
	}


	public void setNbre_stagiaires(int nbre_stagiaires) {
		this.nbre_stagiaires = nbre_stagiaires;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getHeureDeb() {
		return heureDeb;
	}


	public void setHeureDeb(String heureDeb) {
		this.heureDeb = heureDeb;
	}


	public String getHeureFin() {
		return heureFin;
	}


	public void setHeureFin(String heureFin) {
		this.heureFin = heureFin;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
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


	public Salle getSallef() {
		return sallef;
	}


	public void setSallef(Salle sallef) {
		this.sallef = sallef;
	}


	public User getFormateur() {
		return formateur;
	}


	public void setFormateur(User formateur) {
		this.formateur = formateur;
	}


	public List<Stagiaire> getStagiaires() {
		return stagiaires;
	}


	public void setStagiaires(List<Stagiaire> stagiaires) {
		this.stagiaires = stagiaires;
	}


	public Formation getFormation() {
		return formation;
	}


	public void setFormation(Formation formation) {
		this.formation = formation;
	}


	public List<SessionFormationTime> getSession_formation_time() {
		return session_formation_time;
	}


	public void setSession_formation_time(List<SessionFormationTime> session_formation_time) {
		this.session_formation_time = session_formation_time;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public SessionFormation(long id, int duree, String titre, Date date_Deb, Date date_fin, int nbre_stagiaires,
			int status, String heureDeb, String heureFin, String link, Date created_at, Date updated_at, Salle sallef,
			User formateur, List<Stagiaire> stagiaires, Formation formation,
			List<SessionFormationTime> session_formation_time) {
		super();
		this.id = id;
		this.duree = duree;
		this.titre = titre;
		this.date_Deb = date_Deb;
		this.date_fin = date_fin;
		this.nbre_stagiaires = nbre_stagiaires;
		this.status = status;
		this.heureDeb = heureDeb;
		this.heureFin = heureFin;
		this.link = link;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.sallef = sallef;
		this.formateur = formateur;
		this.stagiaires = stagiaires;
		this.formation = formation;
		this.session_formation_time = session_formation_time;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
		result = prime * result + ((date_Deb == null) ? 0 : date_Deb.hashCode());
		result = prime * result + ((date_fin == null) ? 0 : date_fin.hashCode());
		result = prime * result + duree;
		result = prime * result + ((formateur == null) ? 0 : formateur.hashCode());
		result = prime * result + ((formation == null) ? 0 : formation.hashCode());
		result = prime * result + ((heureDeb == null) ? 0 : heureDeb.hashCode());
		result = prime * result + ((heureFin == null) ? 0 : heureFin.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + nbre_stagiaires;
		result = prime * result + ((sallef == null) ? 0 : sallef.hashCode());
		result = prime * result + ((session_formation_time == null) ? 0 : session_formation_time.hashCode());
		result = prime * result + ((stagiaires == null) ? 0 : stagiaires.hashCode());
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
		SessionFormation other = (SessionFormation) obj;
		if (created_at == null) {
			if (other.created_at != null)
				return false;
		} else if (!created_at.equals(other.created_at))
			return false;
		if (date_Deb == null) {
			if (other.date_Deb != null)
				return false;
		} else if (!date_Deb.equals(other.date_Deb))
			return false;
		if (date_fin == null) {
			if (other.date_fin != null)
				return false;
		} else if (!date_fin.equals(other.date_fin))
			return false;
		if (duree != other.duree)
			return false;
		if (formateur == null) {
			if (other.formateur != null)
				return false;
		} else if (!formateur.equals(other.formateur))
			return false;
		if (formation == null) {
			if (other.formation != null)
				return false;
		} else if (!formation.equals(other.formation))
			return false;
		if (heureDeb == null) {
			if (other.heureDeb != null)
				return false;
		} else if (!heureDeb.equals(other.heureDeb))
			return false;
		if (heureFin == null) {
			if (other.heureFin != null)
				return false;
		} else if (!heureFin.equals(other.heureFin))
			return false;
		if (id != other.id)
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (nbre_stagiaires != other.nbre_stagiaires)
			return false;
		if (sallef == null) {
			if (other.sallef != null)
				return false;
		} else if (!sallef.equals(other.sallef))
			return false;
		if (session_formation_time == null) {
			if (other.session_formation_time != null)
				return false;
		} else if (!session_formation_time.equals(other.session_formation_time))
			return false;
		if (stagiaires == null) {
			if (other.stagiaires != null)
				return false;
		} else if (!stagiaires.equals(other.stagiaires))
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


}
