package com.efco.formation.dao.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.efco.formation.dao.SessionFormationDao;
import com.efco.formation.model.SessionFormation;

@Repository
public class SessionFormationDaoImp extends JdbcDaoSupport implements SessionFormationDao {

	@Autowired 
	DataSource dataSource;

	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}

	@Override
	public List<SessionFormation> getAllFormationSession() {
		String sql = "SELECT * FROM session_formation";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

		List<SessionFormation> result = new ArrayList<SessionFormation>();
		for(Map<String, Object> row:rows){
			SessionFormation sessionFormation = new SessionFormation();
			sessionFormation.setId((int)row.get("id"));
			sessionFormation.setTitre((String)row.get("titre"));
			sessionFormation.setDate_Deb((Date)row.get("date_deb"));
			sessionFormation.setDate_fin((Date)row.get("date_fin"));
			sessionFormation.setNbre_stagiaires((int)row.get("nbre_stagiaires"));
			sessionFormation.setHeureDeb((String)row.get("heureDeb"));	
			sessionFormation.setHeureFin((String)row.get("heureFin"));
			result.add(sessionFormation);
		}

		System.out.println("Size : "+result.size());
		return result;
	}

}
