package com.efco.formation.dao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.efco.formation.dao.ClientDao;
import com.efco.formation.model.Client;
import com.efco.formation.model.User;
import com.efco.formation.service.ClientService;
import com.efco.formation.service.UserService;


@Repository
public class ClientDaoImpl extends JdbcDaoSupport implements ClientDao{

	@Autowired 
	DataSource dataSource;

	@Autowired
	UserService userService;


	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}



	@Override
	public void insertClient(Client client) {
		String sql = "INSERT INTO client " + "(assujetti_tva,code_postal,effectif,formejuridique,maison_mere,numero_compte_banc,pays,rcs_rm,siren,siret,type_tier,ville,status,created_at,updated_at,user_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
		System.out.println("Hi : "+client.getUser().getId());
		getJdbcTemplate().update(sql, new Object[]{
				client.isAssujetti_TVA(),
				client.getCodePostal(),
				client.getEffectif(),
				client.getFormejuridique(),
				client.getMaisonMere(),
				client.getNumeroCompteBanc(),
				client.getPays(),
				client.getRcs_rm(),
				client.getSiren(),
				client.getSiret(),
				client.getType_tier(),
				client.getVille(),
				client.getStatus(),
				client.getCreated_at(),
				client.getUpdated_at(),
				client.getUser().getId()

		});

	}

	@Override
	public Client getClientByUserId(int client_id) {
		String sql = "SELECT * FROM Client WHERE user_id = ?";
		try
		{

			return (Client)getJdbcTemplate().queryForObject(sql, new Object[]{client_id}, new RowMapper<Client>(){
				@Override
				public Client mapRow(ResultSet rs, int rwNumber) throws SQLException {
					Client client = new Client();
					client.setId(rs.getInt("id"));
					client.setAssujetti_TVA(rs.getBoolean("assujetti_tva"));
					client.setCodePostal(rs.getInt("code_postal"));
					client.setEffectif(rs.getInt("effectif"));
					client.setFormejuridique(rs.getString("formejuridique"));
					client.setMaisonMere(rs.getString("maison_mere"));
					client.setNumeroCompteBanc(rs.getInt("numero_compte_banc"));
					client.setPays(rs.getString("pays"));
					client.setRcs_rm(rs.getInt("rcs_rm"));
					client.setSiren(rs.getInt("siren"));
					client.setSiret(rs.getInt("siret"));
					client.setType_tier(rs.getString("type_tier"));
					client.setVille(rs.getString("ville"));
					client.setStatus(rs.getInt("status"));
					client.setCreated_at(rs.getDate("created_at"));
					client.setUpdated_at(rs.getDate("updated_at"));
					client.setUser(userService.getUserById(rs.getInt("user_id")));

					return client;
				}
			});
		}catch(EmptyResultDataAccessException ex)
		{
			return null;
		}
	}
}
