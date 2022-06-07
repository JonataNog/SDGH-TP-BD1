package model.services;

import java.util.List;

import model.dao.ConsultationDao;
import model.dao.DaoFactory;
import model.entities.Consultation;
import model.entities.Patient;

public class ConsultationService {
	
	private ConsultationDao dao = DaoFactory.createConsultationDao();
	
	public Consultation findByProtocol(Integer protocol){
		return dao.findByProtocol(protocol);
	}
	
	public List<Consultation> findAll(){
		return dao.findAll();
	}
	
	public List<Consultation> findByPatient(Patient patient){
		return dao.findByPatient(patient);
	}
	
	public void saveOrUpdate(Consultation obj) {
		if(obj.getProtocol() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Consultation obj) {
		dao.deleteByProtocol(obj);
	}
}
