package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.PatientDao;
import model.entities.Patient;

public class PatientDaoJDBC implements PatientDao{
	
	private Connection conn;
	
	public PatientDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Patient obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO paciente "
										 + "(Cpf, PNome, Convenio, Sexo) "
										 + "VALUES "
										 + "(?, ?, ?, ?)");
			st.setString(1, obj.getCpf());
			st.setString(2, obj.getName());
			st.setString(3, obj.getConvenio());
			st.setString(4, obj.getSex());
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
	public void update(Patient obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE paciente " 
										+ "SET PNome = ?, Convenio = ?, Sexo = ? "
										+ "WHERE Cpf = ?");
			st.setString(1, obj.getName());
			st.setString(2, obj.getConvenio());
			st.setString(3, obj.getSex());
			st.setString(4, obj.getCpf());
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
	public void deleteByCpf(Patient obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM paciente WHERE Cpf = ?");
			st.setString(1, obj.getCpf());
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
	public Patient findByCpf(String cpf) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT p.* "
									 	+ "FROM paciente as p "
									 	+ "WHERE c.Cpf = ?");
			st.setString(1, cpf);
			rs = st.executeQuery();
			if(rs.next()) {
				Patient obj = instantiatePatient(rs);
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
	public List<Patient> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM paciente ORDER BY Name");
			rs = st.executeQuery();

			List<Patient> list = new ArrayList<>();

			while (rs.next()) {
				Patient obj = new Patient();
				obj = instantiatePatient(rs);
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
	
	private Patient instantiatePatient(ResultSet rs) throws SQLException{
		Patient obj = new Patient();
		obj.setCpf(rs.getString("Cpf"));
		obj.setName(rs.getString("Nome"));
		obj.setConvenio(rs.getString("Convenio"));
		obj.setSex(rs.getString("Sexo"));
		return obj;
	}


}
