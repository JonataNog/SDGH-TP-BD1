package model.dao;

import java.util.List;

import model.entities.Consultation;
import model.entities.Patient;

public interface ConsultationDao {
	
	void insert(Consultation obj);
	void update(Consultation obj);
	void deleteByProtocol(Consultation obj);
	Consultation findByProtocol(Integer protocol);
	List<Consultation> findAll();
	List<Consultation> findByPatient(Patient patient);

}
