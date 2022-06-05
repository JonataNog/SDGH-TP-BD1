package model.services;

import java.util.ArrayList;
import java.util.List;

import model.dao.ClinicDao;
import model.dao.DaoFactory;
import model.entities.Clinic;

public class ClinicService {
	
	private ClinicDao dao = DaoFactory.createClinicDao();
	
	public List<Clinic> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Clinic obj) {
		if(cnpjInList(obj)) {
			dao.update(obj);
		}
		else {
			dao.insert(obj);
		}
	}
	
	private boolean cnpjInList(Clinic clinic) {
		List<Clinic> list = new ArrayList<>();
		for(Clinic obj : list) {
			if(clinic.getCnpj() == obj.getCnpj()) {
				return true;
			}
		}
		return false;
	}
}
