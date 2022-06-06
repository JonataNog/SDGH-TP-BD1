package model.services;

import model.dao.ConsultationDao;
import model.dao.DaoFactory;
import model.entities.Consultation;

public class ConsultationService {
	
	private ConsultationDao dao = DaoFactory.createConsultationDao();
	
	public Consultation findByProtocol(Integer protocol){
		return dao.findByProtocol(protocol);
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
