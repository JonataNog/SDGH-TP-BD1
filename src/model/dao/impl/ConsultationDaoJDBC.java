package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ConsultationDao;
import model.entities.Clinic;
import model.entities.Consultation;
import model.entities.Doctor;
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
										 + "(data, laudo, medicacao, crm, cpf, cnpj) "
										 + "VALUES "
										 + "(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setDate(1, new Date(obj.getDate().getTime()));
			st.setString(2, obj.getLaudo());
			st.setString(3, obj.getMedication());
			st.setString(4, obj.getDoctor().getCrm());
			st.setString(5, obj.getPatient().getCpf());
			st.setString(6, obj.getClinic().getCnpj());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int protocol = rs.getInt(1);
					obj.setProtocol(protocol);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
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
										+ "SET data = ?, laudo = ?, medicacao = ?, crm = ?, Cpf = ?, Cnpj = ? "
										+ "WHERE Protocolo = ?");
			st.setDate(1, new Date(obj.getDate().getTime()));
			st.setString(2, obj.getLaudo());
			st.setString(3, obj.getMedication());
			st.setString(4, obj.getDoctor().getCrm());
			st.setString(5, obj.getPatient().getCpf());
			st.setString(6, obj.getClinic().getCnpj());
			st.setInt(7, obj.getProtocol());
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
			st = conn.prepareStatement("DELETE FROM consulta WHERE protocolo = ?");
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
			st = conn.prepareStatement("SELECT c.protocolo, c.data, c.laudo, c.medicacao, m.nome as NomeMedico, "
					+ "cl.nome as NomeClinica, p.nome  as NomePaciente "
									 	+ "FROM consulta as c, medico as m, clinica as cl, paciente as p "
									 	+ "WHERE c.protocolo = ? and c.crm = m.crm and c.cnpj = cl.cnpj and c.cpf = p.cpf");
			st.setInt(1, protocol);
			rs = st.executeQuery();
			if(rs.next()) {
				Doctor doctor = instantiateDoctor(rs);
				Clinic clinic = instantiateClinic(rs);
				Patient patient = instantiatePatient(rs);
				Consultation obj = instantiateConsultation(rs, doctor, clinic, patient);
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
	
	private Doctor instantiateDoctor(ResultSet rs) throws SQLException{
		Doctor obj = new Doctor();
		obj.setCrm(rs.getString("crm"));
		obj.setName(rs.getString("nome"));
		
		return obj;
	}

	private Patient instantiatePatient(ResultSet rs)throws SQLException {
		Patient obj = new Patient();
		obj.setCpf(rs.getString("Cpf"));
		obj.setName(rs.getString("Nome"));
		
		return null;
	}

	private Clinic instantiateClinic(ResultSet rs)throws SQLException {
		Clinic obj = new Clinic();
		obj.setCnpj(rs.getString("Cnpj"));
		obj.setName(rs.getString("Nome"));
		
		return obj;
	}


	private Consultation instantiateConsultation(ResultSet rs, Doctor doctor, Clinic clinic, Patient patient)throws SQLException {
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
