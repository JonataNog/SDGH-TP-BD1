package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.DoctorDao;
import model.entities.Clinic;
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
										 + "(crm, nome, especializacao, cnpj_clinica) "
										 + "VALUES "
										 + "(?, ?, ?, ?)");
			st.setString(1, obj.getCrm());
			st.setString(2, obj.getName());
			st.setString(3, obj.getSpecialization());
			st.setString(4, obj.getClinic().getCnpj());
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
										+ "SET nome = ?, especializacao = ?, cnpj_clinica = ? "
										+ "WHERE crm = ?");
			st.setString(1, obj.getName());
			st.setString(2, obj.getSpecialization());
			st.setString(3, obj.getClinic().getCnpj());
			st.setString(4, obj.getCrm());
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
			st = conn.prepareStatement("SELECT m.crm, m.nome, m.especializacao, c.nome "
									 	+ "FROM medico as m, clinica as c "
									 	+ "WHERE m.crm = ?");
			st.setString(1, crm);
			rs = st.executeQuery();
			if(rs.next()) {
				Clinic clinic = instantiateClinic(rs);
				Doctor obj = instantiateDoctor(rs, clinic);
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
				"SELECT medico.crm, medico.nome, medico.especializacao, clinica.cnpj as 'Nome da Clinica' "
					+ "FROM medico INNER JOIN clinica "
					+ "ON medico.cnpj = clinica.cnpj "
					+ "ORDER BY medico.nome");
			rs = st.executeQuery();

			List<Doctor> list = new ArrayList<>();
			Map<String, Clinic> map = new HashMap<>();

			while (rs.next()) {
				Clinic clinic = map.get(rs.getString("Nome da Clinica"));
				
				if(clinic == null) {
					clinic = instantiateClinic(rs);
					map.put(rs.getString("Nome da Clinica"), clinic);
				}
				
				Doctor obj = instantiateDoctor(rs, clinic);
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
	
	private Doctor instantiateDoctor(ResultSet rs, Clinic clinic) throws SQLException{
		Doctor obj = new Doctor();
		obj.setCrm(rs.getString("crm"));
		obj.setName(rs.getString("nome"));
		obj.setName(rs.getString("especializacao"));
		obj.setClinic(clinic);
		return obj;
	}
	
	private Clinic instantiateClinic(ResultSet rs)throws SQLException {
		Clinic obj = new Clinic();
		obj.setCnpj(rs.getString("cnpj"));
		obj.setName(rs.getString("nome"));
		obj.setLocal(rs.getString("local"));
		return obj;
	}

}
