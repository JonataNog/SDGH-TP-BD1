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
										 + "(cpf_parente, cpf, nome, parentesco) "
										 + "VALUES "
										 + "(?, ?, ?, ?)");
			st.setString(1, obj.getPatient().getCpf());
			st.setString(2, obj.getCpf());
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
										+ "SET nome = ?, parentesco = ? "
										+ "WHERE cpf_parente = ? and cpf = ?");
			st.setString(1, obj.getName());
			st.setString(2, obj.getParentage());
			st.setString(3, obj.getPatient().getCpf());
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
	public void delete(Parentage obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM parente WHERE cpf_parente = ? and cpf = ?");
			st.setString(1, obj.getPatient().getCpf());
			st.setString(2, obj.getCpf());
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
			st = conn.prepareStatement("SELECT p.come, p.parentesco, pa.nome "
									 	+ "FROM paciente as pa, parente as p "
									 	+ "WHERE pa.cpf = ? and pa.cpf = p.cpf_parente");
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
	
	@Override
	public List<Parentage> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT paciente.nome as ParentName, p.* "
					+ "FROM parente as p, paciente "
					+ "WHERE p.cpf_parente = paciente.cpf "
					+ "ORDER BY nome");
			
			rs = st.executeQuery();
			
			List<Parentage> list = new ArrayList<>();
			Map<String, Patient> map = new HashMap<>();
			
			while(rs.next()) {
				Patient pat = map.get(rs.getString("cpf_parente"));
				
				if (pat == null) {
					pat = instantiatePatient(rs);
					map.put(rs.getString("cpf_parente"), pat);
				}
				
				Parentage obj = instantiateParentage(rs, pat);
				list.add(obj);
			}
			return list;
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
		obj.setCpf(rs.getString("cpf_parente"));
		obj.setName(rs.getString("ParentName"));
		
		return obj;
	}

	private Parentage instantiateParentage(ResultSet rs, Patient pat) throws SQLException {
		Parentage obj = new Parentage();
		obj.setCpf(rs.getString("cpf"));
		obj.setName(rs.getString("nome"));
		obj.setParentage(rs.getString("parentesco"));
		obj.setPatient(pat);
		obj.setNameParent(pat);
		
		return obj;
	}

}
