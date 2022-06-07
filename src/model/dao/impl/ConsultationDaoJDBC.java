package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
										 + "(data, laudo, medicação, crm, cpf, cnpj) "
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
			st = conn.prepareStatement("SELECT c.*, m.nome as DoctorName, cl.nome as ClinicName, p.nome  as PatientName "
									 	+ "FROM consulta as c, medico as m, clinica as cl, paciente as p "
									 	+ "WHERE c.protocolo = ? and c.cpf = p.cpf and c.crm = m.crm and c.cnpj = cl.cnpj");
			st.setInt(1, protocol);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Clinic clinic = instantiateClinic(rs);
				Doctor doc = instantiateDoctor(rs);
				Patient pat = instantiatePatient(rs);
				Consultation obj = instantiateConsultation(rs, doc, clinic, pat);
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
	public List<Consultation> findByPatient(Patient patient) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT c.*, m.nome as DoctorName, cl.nome as ClinicName, p.nome  as PatientName "
									 	+ "FROM consulta as c, medico as m, clinica as cl, paciente as p "
									 	+ "WHERE c.cpf = ?");
			st.setString(1, patient.getCpf());
			rs = st.executeQuery();
			
			List<Consultation> list = new ArrayList<>();
			Map<String, Doctor> mapDoctor = new HashMap<>();
			Map<String, Clinic> mapClinic = new HashMap<>();
			Map<String, Patient> mapPatient = new HashMap<>();
			
			if(rs.next()) {
				Doctor doc = mapDoctor.get(rs.getString("crm"));
				if(doc == null) {
					doc = instantiateDoctor(rs);
				}
				Clinic clinic = mapClinic.get(rs.getString("cnpj"));
				if(clinic == null) {
					clinic = instantiateClinic(rs);
				}
				Patient pat = mapPatient.get(rs.getString("cpf"));
				if(pat == null) {
					pat = instantiatePatient(rs);
				}
				Consultation obj = instantiateConsultation(rs, doc, clinic, pat);
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

	@Override
	public List<Consultation> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT medico.nome as DoctorName, clinica.nome as ClinicName, paciente.nome as PatientName, consulta.* "
				 	+ "FROM consulta, medico, clinica, paciente "
				 	+ "WHERE consulta.cpf = paciente.cpf and consulta.crm = medico.crm and consulta.cnpj = clinica.cnpj "
				 	+ "ORDER BY protocolo");
			
			rs = st.executeQuery();
			
			List<Consultation> list = new ArrayList<>();
			Map<String, Doctor> mapDoctor = new HashMap<>();
			Map<String, Clinic> mapClinic = new HashMap<>();
			Map<String, Patient> mapPatient = new HashMap<>();
			
			while(rs.next()) {
				Doctor doc = mapDoctor.get(rs.getString("crm"));
				if(doc == null) {
					doc = instantiateDoctor(rs);
				}
				Clinic clinic = mapClinic.get(rs.getString("cnpj"));
				if(clinic == null) {
					clinic = instantiateClinic(rs);
				}
				Patient pat = mapPatient.get(rs.getString("cpf"));
				if(pat == null) {
					pat = instantiatePatient(rs);
				}
				Consultation obj = instantiateConsultation(rs, doc, clinic, pat);
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
	
	private Doctor instantiateDoctor(ResultSet rs) throws SQLException{
		Doctor obj = new Doctor();
		obj.setCrm(rs.getString("crm"));
		obj.setName(rs.getString("DoctorName"));
		
		return obj;
	}

	private Patient instantiatePatient(ResultSet rs)throws SQLException {
		Patient obj = new Patient();
		obj.setCpf(rs.getString("cpf"));
		obj.setName(rs.getString("PatientName"));
		
		return obj;
	}

	private Clinic instantiateClinic(ResultSet rs)throws SQLException {
		Clinic obj = new Clinic();
		obj.setCnpj(rs.getString("cnpj"));
		obj.setName(rs.getString("ClinicName"));
		
		return obj;
	}


	private Consultation instantiateConsultation(ResultSet rs, Doctor doctor, Clinic clinic, Patient patient)throws SQLException {
		Consultation obj = new Consultation();
		obj.setProtocol(rs.getInt("protocolo"));
		obj.setDate(new java.util.Date(rs.getTimestamp("data").getTime()));
		obj.setLaudo(rs.getString("laudo"));
		obj.setMedication(rs.getString("medicação"));
		obj.setClinic(clinic);
		obj.setPatient(patient);
		obj.setDoctor(doctor);
		obj.setDoctorName(doctor);
		obj.setPatientName(patient);
		obj.setClinicName(clinic);
		
		return obj;
	}

}
