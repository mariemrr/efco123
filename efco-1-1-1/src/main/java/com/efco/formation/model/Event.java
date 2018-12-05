package com.efco.formation.model;

public class Event {

	private long id;

	private String title;

	private String start;

	private String end;

	private String description;

	private String color;

	private int free_stagiare_number;

	private String salle;
	
	private String adresse;
	
	private String localisation;

	private int expired;
	
	private String link;
	
	private String duree;

	private String formation_date_debut;
	
	private String formation_date_fin;

	public Event()
	{

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getFree_stagiare_number() {
		return free_stagiare_number;
	}

	public void setFree_stagiare_number(int free_stagiare_number) {
		this.free_stagiare_number = free_stagiare_number;
	}

	public String getSalle() {
		return salle;
	}

	public void setSalle(String salle) {
		this.salle = salle;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getLocalisation() {
		return localisation;
	}

	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}

	public int getExpired() {
		return expired;
	}

	public void setExpired(int expired) {
		this.expired = expired;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDuree() {
		return duree;
	}

	public void setDuree(String duree) {
		this.duree = duree;
	}

	public String getFormation_date_debut() {
		return formation_date_debut;
	}

	public void setFormation_date_debut(String formation_date_debut) {
		this.formation_date_debut = formation_date_debut;
	}

	public String getFormation_date_fin() {
		return formation_date_fin;
	}

	public void setFormation_date_fin(String formation_date_fin) {
		this.formation_date_fin = formation_date_fin;
	}
	




	

}