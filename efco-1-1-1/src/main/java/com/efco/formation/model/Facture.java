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
public class Facture implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nom;
	private double totale;
	private double montant_recu;
	private int methode_paiement; //1-> cheque,2->espéces
	private int status;
	private int nbr_heure;
	private Long formation_id;
	private int nbr_stagiaires;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at = new Date();




	@Override
	public String toString() {
		return "Facture [id=" + id + ", nom=" + nom + ", totale=" + totale + ", montant_recu=" + montant_recu
				+ ", methode_paiement=" + methode_paiement + ", status=" + status + ", nbr_heure=" + nbr_heure
				+ ", formation_id=" + formation_id + ", nbr_stagiaires=" + nbr_stagiaires + ", created_at=" + created_at
				+ ", updated_at=" + updated_at + "]";
	}

	@PreUpdate
	public void setLastUpdate() {  this.updated_at = new Date(); }

	public Facture()
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

	public double getTotale() {
		return totale;
	}

	public void setTotale(double totale) {
		this.totale = totale;
	}

	public double getMontant_recu() {
		return montant_recu;
	}

	public void setMontant_recu(double montant_recu) {
		this.montant_recu = montant_recu;
	}

	public int getMethode_paiement() {
		return methode_paiement;
	}

	public void setMethode_paiement(int methode_paiement) {
		this.methode_paiement = methode_paiement;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNbr_heure() {
		return nbr_heure;
	}

	public void setNbr_heure(int nbr_heure) {
		this.nbr_heure = nbr_heure;
	}

	public long getFormation_id() {
		return formation_id;
	}

	public void setFormation_id(long formation_id) {
		this.formation_id = formation_id;
	}

	public int getNbr_stagiaires() {
		return nbr_stagiaires;
	}

	public void setNbr_stagiaires(int nbr_stagiaires) {
		this.nbr_stagiaires = nbr_stagiaires;
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

	public Facture(long id, String nom, double totale, double montant_recu, int methode_paiement, int status,
			int nbr_heure, int formation_id, int nbr_stagiaires, Date created_at, Date updated_at) {
		super();
		this.id = id;
		this.nom = nom;
		this.totale = totale;
		this.montant_recu = montant_recu;
		this.methode_paiement = methode_paiement;
		this.status = status;
		this.nbr_heure = nbr_heure;
		this.formation_id = (long) formation_id;
		this.nbr_stagiaires = nbr_stagiaires;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
		result = (int) (prime * result + formation_id);
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + methode_paiement;
		long temp;
		temp = Double.doubleToLongBits(montant_recu);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + nbr_heure;
		result = prime * result + nbr_stagiaires;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + status;
		temp = Double.doubleToLongBits(totale);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Facture other = (Facture) obj;
		if (created_at == null) {
			if (other.created_at != null)
				return false;
		} else if (!created_at.equals(other.created_at))
			return false;
		if (formation_id != other.formation_id)
			return false;
		if (id != other.id)
			return false;
		if (methode_paiement != other.methode_paiement)
			return false;
		if (Double.doubleToLongBits(montant_recu) != Double.doubleToLongBits(other.montant_recu))
			return false;
		if (nbr_heure != other.nbr_heure)
			return false;
		if (nbr_stagiaires != other.nbr_stagiaires)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (status != other.status)
			return false;
		if (Double.doubleToLongBits(totale) != Double.doubleToLongBits(other.totale))
			return false;
		if (updated_at == null) {
			if (other.updated_at != null)
				return false;
		} else if (!updated_at.equals(other.updated_at))
			return false;
		return true;
	}



}
