package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ParentageDao;
import model.entities.Parentage;
import model.entities.Patient;

public class ParentageService {
	
	private ParentageDao dao = DaoFactory.createParentageDao();
	
	public List<Parentage> findAll(){
		return dao.findAll();
	}
	
	public List<Parentage> findByPatient(Patient patient){
		return dao.findByPatient(patient);
	}
	
	public void saveOrUpdate(Parentage obj) {
		if(cpfsInList(obj)) {
			dao.update(obj);
		}
		else {
			dao.insert(obj);
		}
	}
	
	private boolean cpfsInList(Parentage parentage) {
		List<Parentage> list = dao.findAll();
		for(Parentage obj : list) {
			if(parentage.getCpf().equals(obj.getCpf()) && parentage.getPatient().getCpf().equals(obj.getPatient().getCpf())) {
				return true;
			}
		}
		return false;
	}
	
	public void remove(Parentage obj) {
		dao.delete(obj);
	}
}
