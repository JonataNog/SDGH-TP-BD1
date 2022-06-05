package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ConsultationDao;
import model.entities.Clinic;
import model.entities.Consultation;
import model.entities.Patient;

public class ConsultationDaoJDBC implements ConsultationDao {
	
	private Connection conn;
	
	public ConsultationDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Consultation obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO consulta "
										 + "(Protocolo, Data, Laudo, Medicacao, Cnpj, Cpf) "
										 + "VALUES "
										 + "(?, ?, ?, ?, ?, ?, ?)");
			st.setInt(1, obj.getProtocol());
			st.setDate(2, new Date(obj.getDate().getTime()));
			st.setString(3, obj.getLaudo());
			st.setString(4, obj.getMedication());
			st.setString(5, obj.getClinic().getCnpj());
			st.setString(6, obj.getPatient().getCpf());
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
	public void update(Consultation obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE consulta " 
										+ "SET Data = ?, Laudo = ?, Medicacao = ?, Cnpj = ?, Cpf = ? "
										+ "WHERE Protocolo = ?");
			st.setDate(1, new Date(obj.getDate().getTime()));
			st.setString(2, obj.getLaudo());
			st.setString(3, obj.getMedication());
			st.setString(4, obj.getClinic().getCnpj());
			st.setString(5, obj.getPatient().getCpf());
			st.setInt(6, obj.getProtocol());
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
	public void deleteByProtocol(Consultation obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM consulta WHERE Protocol = ?");
			st.setInt(1, obj.getProtocol());
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
	public Consultation findByProtocol(Integer protocol) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT c.Protocolo, c.Data, c.Laudo, c.Medicacao, cl.Nome, p.Pnome "
									 	+ "FROM consulta as c, clinica as cl, paciente as p "
									 	+ "WHERE c.Protocol = ? and c.Crm = m.Crm and c.Cnpj = cl.Cnpj and c.Cpf = p.Cpf");
			st.setInt(1, protocol);
			rs = st.executeQuery();
			if(rs.next()) {
				Clinic clinic = instantiateClinic(rs);
				Patient patient = instantiatePatient(rs);
				Consultation obj = instantiateConsultation(rs, clinic, patient);
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
	public List<Consultation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private Patient instantiatePatient(ResultSet rs)throws SQLException {
		Patient obj = new Patient();
		obj.setCpf(rs.getString("Cpf"));
		obj.setName(rs.getString("Nome"));
		obj.setConvenio(rs.getString("Convenio"));
		obj.setSex(rs.getString("Sexo"));
		
		return null;
	}

	private Clinic instantiateClinic(ResultSet rs)throws SQLException {
		Clinic obj = new Clinic();
		obj.setCnpj(rs.getString("Cnpj"));
		obj.setName(rs.getString("Nome"));
		
		return obj;
	}


	private Consultation instantiateConsultation(ResultSet rs, Clinic clinic, Patient patient)throws SQLException {
		Consultation obj = new Consultation();
		obj.setProtocol(rs.getInt("Protocolo"));
		obj.setDate(rs.getDate("Data"));
		obj.setLaudo(rs.getString("Laudo"));
		obj.setMedication(rs.getString("Medicacao"));
		obj.setClinic(clinic);
		obj.setPatient(patient);
		
		return obj;
	}

}
