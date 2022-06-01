package model.dao;

import java.util.List;

import model.entities.Consultation;

public interface ConsultationDao {
	
	void insert(Consultation obj);
	void update(Consultation obj);
	void deleteByProtocol(Consultation obj);
	Consultation findByProtocol(Integer protocol);
	List<Consultation> findAll();

}
