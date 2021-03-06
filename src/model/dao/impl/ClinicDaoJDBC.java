package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ClinicDao;
import model.entities.Clinic;

public class ClinicDaoJDBC implements ClinicDao {
	
	private Connection conn;
	
	public ClinicDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Clinic obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO clinica "
										 + "(cnpj, nome, local) "
										 + "VALUES "
										 + "(?, ?, ?)");
			st.setString(1, obj.getCnpj());
			st.setString(2, obj.getName());
			st.setString(3, obj.getLocal());
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
	public void update(Clinic obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE clinica " 
										+ "SET nome = ?, local = ? "
										+ "WHERE cnpj = ?");
			st.setString(1, obj.getName());
			st.setString(2, obj.getLocal());
			st.setString(3, obj.getCnpj());
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
	public void deleteByCnpj(Clinic obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM clinica WHERE cnpj = ?");
			st.setString(1, obj.getCnpj());
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
	public Clinic findByCnpj(String cnpj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT c.* "
									 	+ "FROM clinica as c "
									 	+ "WHERE c.cnpj = ?");
			st.setString(1, cnpj);
			rs = st.executeQuery();
			if(rs.next()) {
				Clinic obj = instantiateClinic(rs);
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
	public List<Clinic> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM clinica ORDER BY nome");
			rs = st.executeQuery();

			List<Clinic> list = new ArrayList<>();

			while (rs.next()) {
				Clinic obj = new Clinic();
				obj = instantiateClinic(rs);
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
	
	private Clinic instantiateClinic(ResultSet rs)throws SQLException {
		Clinic obj = new Clinic();
		obj.setCnpj(rs.getString("cnpj"));
		obj.setName(rs.getString("nome"));
		obj.setLocal(rs.getString("local"));
		return obj;
	}
}
