package com.efco.formation.dao.imp;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.usertype.UserCollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import com.efco.formation.dao.RoleDao;
import com.efco.formation.dao.UserDao;
import com.efco.formation.model.Stagiaire;
import com.efco.formation.model.User;
import com.efco.formation.service.ClientService;
import com.efco.formation.service.RoleService;
import com.efco.formation.service.StagiareService;


@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao{

	@Autowired 
	DataSource dataSource;

	@Autowired
	RoleService roleService;


	@Autowired
	ClientService clientService;

	@Autowired
	StagiareService stagiaireService;

	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}



	@Override
	public int insertUser(User user) {
		String sql = "INSERT INTO Users " + "(nom,prenom,email,adresse,num_tel,status,created_at,updated_at,role_id) VALUES (?,?,?,?,?,?,?,?,?)" ;
		getJdbcTemplate().update(sql, new Object[]{
				user.getNom(),
				user.getPrenom(),
				user.getEmail(),
				user.getAdresse(),
				user.getNum_tel(),
				user.getStatus(),
				user.getCreated_at(),
				user.getUpdated_at(),
				user.getRole().getId()
		});

		String sql2 = "select id from Users where email=?";
		int result= (int)getJdbcTemplate().queryForObject(
				sql2, new Object[] { user.getEmail() }, int.class);
		System.out.println("id user : "+result);
		return result;


	}

	@Override
	public void insertUsers(final List<User> Users) {
		String sql = "INSERT INTO Users " + "(nom,prenom,email,adresse,password,num_tel,status,role_id) VALUES (?,?,?,?,?,?,?,?)";
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				User User = Users.get(i);
				ps.setString(1, User.getNom());
				ps.setString(2, User.getPrenom());
				ps.setString(3, User.getEmail());
				ps.setString(4, User.getAdresse());
				ps.setString(5,User.getPassword());
				ps.setString(6, User.getNum_tel());
				ps.setInt(7, User.getStatus());
				ps.setInt(8, User.getRole().getId());
			}

			public int getBatchSize() {
				return Users.size();
			}
		});

	}
	@Override
	public List<User> getAllUsers(){
		String sql = "SELECT * FROM Users";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

		List<User> result = new ArrayList<User>();
		for(Map<String, Object> row:rows){
			User user = new User();
			user.setId((int)row.get("id"));
			user.setNom((String)row.get("nom"));
			user.setPrenom((String)row.get("prenom"));
			user.setAdresse((String)row.get("adresse"));
			user.setNum_tel((String)row.get("num_tel"));
			user.setStatus((int)row.get("status"));
			user.setRole(roleService.getRoleById((int)row.get("role_id")));
			user.setCreated_at((Date)row.get("created_at"));
			user.setUpdated_at((Date)row.get("updated_at"));
			result.add(user);
		}

		return result;
	}

	@Override
	public User getUserById(int userId) {
		String sql = "SELECT * FROM Users WHERE id = ?";
		try
		{

			return (User)getJdbcTemplate().queryForObject(sql, new Object[]{userId}, new RowMapper<User>(){
				@Override
				public User mapRow(ResultSet rs, int rwNumber) throws SQLException {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setNom(rs.getString("nom"));
					user.setPrenom(rs.getString("prenom"));
					user.setEmail(rs.getString("email"));
					user.setAdresse(rs.getString("adresse"));
					user.setNum_tel(rs.getString("num_tel"));
					user.setStatus(rs.getInt("status"));
					user.setRole(roleService.getRoleById(rs.getInt("role_id")));
					user.setCreated_at(rs.getDate("created_at"));
					user.setUpdated_at(rs.getDate("updated_at"));
					return user;
				}
			});
		}catch(EmptyResultDataAccessException ex)
		{
			return null;
		}
	}

	@Override
	public User getUserByEmailAndPassword(String email, String password) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM Users WHERE email = ? and password = ?";
		try {
			return (User)getJdbcTemplate().queryForObject(sql, new Object[]{email,password}, new RowMapper<User>(){
				@Override
				public User mapRow(ResultSet rs, int rwNumber) throws SQLException {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setNom(rs.getString("nom"));
					user.setPrenom(rs.getString("prenom"));
					user.setEmail(rs.getString("email"));
					user.setAdresse(rs.getString("adresse"));
					user.setNum_tel(rs.getString("num_tel"));
					user.setStatus(rs.getInt("status"));
					user.setRole(roleService.getRoleById(rs.getInt("role_id")));
					user.setCreated_at(rs.getDate("created_at"));
					user.setUpdated_at(rs.getDate("updated_at"));

					return user;
				}
			});
		}catch(EmptyResultDataAccessException ex)
		{
			return null;
		}
		//return null;
	}



	@Override
	public User getUserByEmail(String email) {
		String sql = "SELECT * FROM Users WHERE email = ?";
		User userFound=null;
		try
		{
			userFound=  (User)getJdbcTemplate().queryForObject(sql, new Object[]{email}, new RowMapper<User>(){
				@Override
				public User mapRow(ResultSet rs, int rwNumber) throws SQLException {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setNom(rs.getString("nom"));
					user.setEmail(rs.getString("email"));
					user.setPrenom(rs.getString("prenom"));
					user.setAdresse(rs.getString("adresse"));
					user.setNum_tel(rs.getString("num_tel"));
					user.setStatus(rs.getInt("status"));
					user.setRole(roleService.getRoleById(rs.getInt("role_id")));
					user.setCreated_at(rs.getDate("created_at"));
					user.setUpdated_at(rs.getDate("updated_at"));
					return user;
				}
			});
		}catch(EmptyResultDataAccessException ex)
		{
			return null;
		}
		return userFound;
		//return null;
	}



	@Override
	public List<User> getUsersByRole(int role_id) {
		String sql = "SELECT * FROM Users where role_id=? and status!=-1";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[]{role_id});

		List<User> result = new ArrayList<User>();
		for(Map<String, Object> row:rows){
			User user = new User();
			user.setId((int)row.get("id"));
			user.setNom((String)row.get("nom"));
			user.setEmail((String)row.get("email"));
			user.setPrenom((String)row.get("prenom"));
			user.setAdresse((String)row.get("adresse"));
			user.setNum_tel((String)row.get("num_tel"));
			user.setStatus((int)row.get("status"));
			user.setRole(roleService.getRoleById((int)row.get("role_id")));
			user.setCreated_at((Date)row.get("created_at"));
			user.setUpdated_at((Date)row.get("updated_at"));
			result.add(user);
		}

		return result;
	}



	@Override
	public User acceptUser(int user_id,String password) {
		String sql = "Update users set status=1 , password =' "+password+" 'where id = "+user_id;
		System.out.println("Sql : "+sql);
		try
		{
			if(getJdbcTemplate().update(sql)>0)
			{
				return this.getUserById(user_id);
			}
		}catch(EmptyResultDataAccessException ex)
		{
			return null;
		}
		return null;
	}



	@Override
	public User deletetUser(int user_id) {
		// TODO Auto-generated method stub

		String sql = "Update users set status=-1 where id = "+user_id;
		System.out.println("Sql : "+sql);
		try
		{
			if(getJdbcTemplate().update(sql)>0)
			{
				return this.getUserById(user_id);
			}
		}catch(EmptyResultDataAccessException ex)
		{
			return null;
		}
		return null;
	}



	@Override
	public void deleteStag(Long stag_id) {
		// TODO Auto-generated method stub


		String sql = "delete from stagiaire where id = "+stag_id;
		System.out.println("Sql : "+sql);
		try
		{
			if(getJdbcTemplate().update(sql)>0)
			{
				System.out.println("Done");
			}
		}catch(EmptyResultDataAccessException ex)
		{

		}


	}
}
