package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import entities.Clinic;
import model.dao.ClinicDao;

public class ClinicDaoJDBC implements ClinicDao {
	
	private Connection conn;
	
	public ClinicDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Clinic obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO clinic "
										 + "(Cnpj, Local, Nome) "
										 + "VALUES "
										 + "(?, ?, ?)");
			st.setInt(1, obj.getCnpj());
			st.setString(2, obj.getLocal());
			st.setString(3, obj.getName());
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
			st = conn.prepareStatement("UPDATE clinic " 
										+ "SET Local = ?, Nome = ? "
										+ "WHERE Cnpj = ?");
			st.setString(1, obj.getLocal());
			st.setString(2, obj.getName());
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
			st = conn.prepareStatement("DELETE FROM clinic WHERE Cnpj = ?");
			st.setInt(1, obj.getCnpj());
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
	public Clinic findByCnpj(Integer cnpj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT c.* "
									 	+ "FROM clinic as c "
									 	+ "WHERE c.Cnpj = ?");
			st.setInt(1, cnpj);
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
		// TODO Auto-generated method stub
		return null;
	}
	
	private Clinic instantiateClinic(ResultSet rs)throws SQLException {
		Clinic obj = new Clinic();
		obj.setCnpj(rs.getInt("Cnpj"));
		obj.setLocal(rs.getString("Local"));
		obj.setName(rs.getString("Name"));
		return obj;
	}
}
