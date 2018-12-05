package com.efco.formation.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@TableGenerator(name="client")
@DiscriminatorValue(value="Client")

public class Client  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = true)
	private long siren;
	@Column(nullable = true)
	private long siret;
	@Column(nullable = true)
	private long rcs_rm;
	@Column(nullable = true)
	private boolean assujetti_TVA;
	@Column(nullable = true)
	private long effectif;
	@Column(nullable = true)
	private String type_tier;
	@Column(nullable = true)
	private String formejuridique;
	@Column(nullable = true)
	private long numeroCompteBanc;
	@Column(nullable = true)
	private String maisonMere;
	@Column(nullable = true)
	private String pays;
	@Column(nullable = true)
	private String ville;
	@Column(nullable = true)
	private long codePostal;
	private int status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at = new Date();

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;


	@OneToMany(mappedBy="client",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Stagiaire> list_stagiaires = new ArrayList<>();


	@PreUpdate
	public void setLastUpdate() {  this.updated_at = new Date(); }


	public Client()
	{

	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getSiren() {
		return siren;
	}


	public void setSiren(long siren) {
		this.siren = siren;
	}


	public long getSiret() {
		return siret;
	}


	public void setSiret(long siret) {
		this.siret = siret;
	}


	public long getRcs_rm() {
		return rcs_rm;
	}


	public void setRcs_rm(long rcs_rm) {
		this.rcs_rm = rcs_rm;
	}


	public boolean isAssujetti_TVA() {
		return assujetti_TVA;
	}


	public void setAssujetti_TVA(boolean assujetti_TVA) {
		this.assujetti_TVA = assujetti_TVA;
	}


	public long getEffectif() {
		return effectif;
	}


	public void setEffectif(long effectif) {
		this.effectif = effectif;
	}


	public String getType_tier() {
		return type_tier;
	}


	public void setType_tier(String type_tier) {
		this.type_tier = type_tier;
	}


	public String getFormejuridique() {
		return formejuridique;
	}


	public void setFormejuridique(String formejuridique) {
		this.formejuridique = formejuridique;
	}


	public long getNumeroCompteBanc() {
		return numeroCompteBanc;
	}


	public void setNumeroCompteBanc(long numeroCompteBanc) {
		this.numeroCompteBanc = numeroCompteBanc;
	}


	public String getMaisonMere() {
		return maisonMere;
	}


	public void setMaisonMere(String maisonMere) {
		this.maisonMere = maisonMere;
	}


	public String getPays() {
		return pays;
	}


	public void setPays(String pays) {
		this.pays = pays;
	}


	public String getVille() {
		return ville;
	}


	public void setVille(String ville) {
		this.ville = ville;
	}


	public long getCodePostal() {
		return codePostal;
	}


	public void setCodePostal(long codePostal) {
		this.codePostal = codePostal;
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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List<Stagiaire> getList_stagiaires() {
		return list_stagiaires;
	}


	public void setList_stagiaires(List<Stagiaire> list_stagiaires) {
		this.list_stagiaires = list_stagiaires;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Client(long id, long siren, long siret, long rcs_rm, boolean assujetti_TVA, long effectif, String type_tier,
			String formejuridique, long numeroCompteBanc, String maisonMere, String pays, String ville, long codePostal,
			int status, Date created_at, Date updated_at, User user, List<Stagiaire> list_stagiaires) {
		super();
		this.id = id;
		this.siren = siren;
		this.siret = siret;
		this.rcs_rm = rcs_rm;
		this.assujetti_TVA = assujetti_TVA;
		this.effectif = effectif;
		this.type_tier = type_tier;
		this.formejuridique = formejuridique;
		this.numeroCompteBanc = numeroCompteBanc;
		this.maisonMere = maisonMere;
		this.pays = pays;
		this.ville = ville;
		this.codePostal = codePostal;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.user = user;
		this.list_stagiaires = list_stagiaires;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (assujetti_TVA ? 1231 : 1237);
		result = prime * result + (int) (codePostal ^ (codePostal >>> 32));
		result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
		result = prime * result + (int) (effectif ^ (effectif >>> 32));
		result = prime * result + ((formejuridique == null) ? 0 : formejuridique.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((list_stagiaires == null) ? 0 : list_stagiaires.hashCode());
		result = prime * result + ((maisonMere == null) ? 0 : maisonMere.hashCode());
		result = prime * result + (int) (numeroCompteBanc ^ (numeroCompteBanc >>> 32));
		result = prime * result + ((pays == null) ? 0 : pays.hashCode());
		result = prime * result + (int) (rcs_rm ^ (rcs_rm >>> 32));
		result = prime * result + (int) (siren ^ (siren >>> 32));
		result = prime * result + (int) (siret ^ (siret >>> 32));
		result = prime * result + status;
		result = prime * result + ((type_tier == null) ? 0 : type_tier.hashCode());
		result = prime * result + ((updated_at == null) ? 0 : updated_at.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((ville == null) ? 0 : ville.hashCode());
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
		Client other = (Client) obj;
		if (assujetti_TVA != other.assujetti_TVA)
			return false;
		if (codePostal != other.codePostal)
			return false;
		if (created_at == null) {
			if (other.created_at != null)
				return false;
		} else if (!created_at.equals(other.created_at))
			return false;
		if (effectif != other.effectif)
			return false;
		if (formejuridique == null) {
			if (other.formejuridique != null)
				return false;
		} else if (!formejuridique.equals(other.formejuridique))
			return false;
		if (id != other.id)
			return false;
		if (list_stagiaires == null) {
			if (other.list_stagiaires != null)
				return false;
		} else if (!list_stagiaires.equals(other.list_stagiaires))
			return false;
		if (maisonMere == null) {
			if (other.maisonMere != null)
				return false;
		} else if (!maisonMere.equals(other.maisonMere))
			return false;
		if (numeroCompteBanc != other.numeroCompteBanc)
			return false;
		if (pays == null) {
			if (other.pays != null)
				return false;
		} else if (!pays.equals(other.pays))
			return false;
		if (rcs_rm != other.rcs_rm)
			return false;
		if (siren != other.siren)
			return false;
		if (siret != other.siret)
			return false;
		if (status != other.status)
			return false;
		if (type_tier == null) {
			if (other.type_tier != null)
				return false;
		} else if (!type_tier.equals(other.type_tier))
			return false;
		if (updated_at == null) {
			if (other.updated_at != null)
				return false;
		} else if (!updated_at.equals(other.updated_at))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (ville == null) {
			if (other.ville != null)
				return false;
		} else if (!ville.equals(other.ville))
			return false;
		return true;
	}


}
