package model.services;

import java.util.List;

import model.dao.PatientDao;
import model.dao.DaoFactory;
import model.entities.Patient;

public class PatientService {
	
	private PatientDao dao = DaoFactory.createPatientDao();
	
	public List<Patient> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Patient obj) {
		if(cpfInList(obj)) {
			dao.update(obj);
		}
		else {
			dao.insert(obj);
		}
	}
	
	private boolean cpfInList(Patient clinic) {
		List<Patient> list = dao.findAll();
		for(Patient obj : list) {
			if(clinic.getCpf() == obj.getCpf()) {
				return true;
			}
		}
		return false;
	}
	
	public void remove(Patient obj) {
		dao.deleteByCpf(obj);
	}
}
