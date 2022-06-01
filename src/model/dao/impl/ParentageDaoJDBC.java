package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ParentageDao;
import model.entities.Parentage;
import model.entities.Patient;

public class ParentageDaoJDBC implements ParentageDao{
	
	private Connection conn;

	public ParentageDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Parentage obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO parente "
										 + "(Cpf, Cpf_parente, Nome, Parentesco) "
										 + "VALUES "
										 + "(?, ?, ?, ?)");
			st.setString(1, obj.getPatient().getCpf());
			st.setString(2, obj.getParentageCpf());
			st.setString(3, obj.getName());
			st.setString(4, obj.getParentage());
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
	public void update(Parentage obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE parente " 
										+ "SET Nome = ?, Parentesco = ? "
										+ "WHERE Cpf = ? and Cpf_parente = ?");
			st.setString(1, obj.getName());
			st.setString(2, obj.getParentage());
			st.setString(3, obj.getPatient().getCpf());
			st.setString(4, obj.getParentageCpf());
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
	public void delete(Parentage obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM parente WHERE Cpf = ? and Cpf_parente = ?");
			st.setString(1, obj.getPatient().getCpf());
			st.setString(2, obj.getParentageCpf());
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
	public Parentage findByCpfs(String cpf) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT c.Nome, c.Parentesco, p.PNome "
									 	+ "FROM clinica as c, parente as p "
									 	+ "WHERE p.Cpf = ? and c.Cpf = p.Cpf");
			st.setString(1, cpf);
			rs = st.executeQuery();
			if(rs.next()) {
				Patient pat = instantiatePatient(rs);
				Parentage obj = instantiateParentage(rs, pat);
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

	private Patient instantiatePatient(ResultSet rs) throws SQLException{
		Patient obj = new Patient();
		obj.setCpf(rs.getString("Cpf"));
		obj.setName(rs.getString("Nome"));
		obj.setConvenio(rs.getString("Convenio"));
		obj.setSex(rs.getString("Sexo"));
		
		return obj;
	}

	@Override
	public List<Parentage> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private Parentage instantiateParentage(ResultSet rs, Patient pat) throws SQLException {
		Parentage obj = new Parentage();
		obj.setParentageCpf(rs.getString("Cpf_parente"));
		obj.setName(rs.getString("Nome"));
		obj.setParentage(rs.getString("Parentesco"));
		obj.setPatient(pat);
		
		return obj;
	}

}
