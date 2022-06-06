package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DoctorDao;
import model.entities.Doctor;

public class DoctorDaoJDBC implements DoctorDao {
	
	private Connection conn;

	public DoctorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Doctor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO medico "
										 + "(crm, nome, especializacao) "
										 + "VALUES "
										 + "(?, ?, ?)");
			st.setString(1, obj.getCrm());
			st.setString(2, obj.getName());
			st.setString(3, obj.getSpecialization());
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Doctor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE medico " 
										+ "SET nome = ?, especializacao = ? "
										+ "WHERE crm = ?");
			st.setString(1, obj.getName());
			st.setString(2, obj.getSpecialization());
			st.setString(3, obj.getCrm());
			st.executeUpdate();
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteByCrm(Doctor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM medico WHERE crm = ?");
			st.setString(1, obj.getCrm());
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Doctor findByCrm(String crm) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT crm, nome, especializacao "
									 	+ "FROM medico "
									 	+ "WHERE crm = ?");
			st.setString(1, crm);
			rs = st.executeQuery();
			if(rs.next()) {
				Doctor obj = instantiateDoctor(rs);
				return obj;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Doctor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT medico.* "
					+ "FROM medico "
					+ "ORDER BY medico.nome");
			rs = st.executeQuery();

			List<Doctor> list = new ArrayList<>();

			while (rs.next()) {
				Doctor obj = instantiateDoctor(rs);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Doctor instantiateDoctor(ResultSet rs) throws SQLException{
		Doctor obj = new Doctor();
		obj.setCrm(rs.getString("crm"));
		obj.setName(rs.getString("nome"));
		obj.setSpecialization(rs.getString("especializacao"));
		
		return obj;
	}

}
