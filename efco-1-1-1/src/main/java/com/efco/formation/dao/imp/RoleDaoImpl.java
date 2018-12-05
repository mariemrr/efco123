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

import com.efco.formation.dao.RoleDao;

import com.efco.formation.model.Role;


@Repository
public class RoleDaoImpl extends JdbcDaoSupport implements RoleDao{

	@Autowired 
	DataSource dataSource;

	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}

	@Override
	public Role getRoleById(int id) {

		String sql = "SELECT * FROM roles WHERE id = ?";
		try
		{
			return (Role)getJdbcTemplate().queryForObject(sql, new Object[]{id}, new RowMapper<Role>(){
				@Override
				public Role mapRow(ResultSet rs, int rwNumber) throws SQLException {
					Role role = new Role();
					role.setId(rs.getInt("id"));
					role.setNom(rs.getString("nom"));
					return role;
				}
			});
		}catch(EmptyResultDataAccessException ex)
		{
			return null;
		}

	}
}
