package com.efco.formation.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class SessionFormationTime implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_deb = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_fin = new Date();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "session_formation_id")
	private SessionFormation session_formation;

	public SessionFormationTime()
	{
			
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate_deb() {
		return date_deb;
	}

	public void setDate_deb(Date date_deb) {
		this.date_deb = date_deb;
	}

	public Date getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}

	public SessionFormation getSession_formation() {
		return session_formation;
	}

	public void setSession_formation(SessionFormation session_formation) {
		this.session_formation = session_formation;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SessionFormationTime(long id, Date date_deb, Date date_fin, SessionFormation session_formation) {
		super();
		this.id = id;
		this.date_deb = date_deb;
		this.date_fin = date_fin;
		this.session_formation = session_formation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date_deb == null) ? 0 : date_deb.hashCode());
		result = prime * result + ((date_fin == null) ? 0 : date_fin.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((session_formation == null) ? 0 : session_formation.hashCode());
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
		SessionFormationTime other = (SessionFormationTime) obj;
		if (date_deb == null) {
			if (other.date_deb != null)
				return false;
		} else if (!date_deb.equals(other.date_deb))
			return false;
		if (date_fin == null) {
			if (other.date_fin != null)
				return false;
		} else if (!date_fin.equals(other.date_fin))
			return false;
		if (id != other.id)
			return false;
		if (session_formation == null) {
			if (other.session_formation != null)
				return false;
		} else if (!session_formation.equals(other.session_formation))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SessionFormationTime [id=" + id + ", date_deb=" + date_deb + ", date_fin=" + date_fin
				+ ", session_formation=" + session_formation + "]";
	}



}
