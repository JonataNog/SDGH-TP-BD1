package model.services;

import java.util.List;

import model.dao.ClinicDao;
import model.dao.DaoFactory;
import model.entities.Clinic;

public class ClinicService {
	
	private ClinicDao dao = DaoFactory.createClinicDao();
	
	public List<Clinic> findAll(){
		return dao.findAll();
	}
	
	public Clinic findByCnpj(String cnpj) {
		return dao.findByCnpj(cnpj);
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
		List<Clinic> list = dao.findAll();
		for(Clinic obj : list) {
			if(clinic.getCnpj().equals(obj.getCnpj())) {
				return true;
			}
		}
		return false;
	}
	
	public void remove(Clinic obj) {
		dao.deleteByCnpj(obj);
	}
}
