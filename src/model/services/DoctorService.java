package model.services;

import java.util.List;

import model.dao.DoctorDao;
import model.dao.DaoFactory;
import model.entities.Doctor;

public class DoctorService {
	
	private DoctorDao dao = DaoFactory.createDoctorDao();
	
	public List<Doctor> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Doctor obj) {
		if(crmInList(obj)) {
			dao.update(obj);
		}
		else {
			dao.insert(obj);
		}
	}
	
	private boolean crmInList(Doctor doc) {
		List<Doctor> list = dao.findAll();
		for(Doctor obj : list) {
			if(doc.getCrm() == obj.getCrm()) {
				return true;
			}
		}
		return false;
	}
	
	public void remove(Doctor obj) {
		dao.deleteByCrm(obj);
	}
}
