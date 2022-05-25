package model.dao;

import java.util.List;

import entities.Consultation;

public interface ConsultationDao {
	
	void insert(Consultation obj);
	void update(Consultation obj);
	void deleteByCnpj(Consultation obj);
	Consultation findByProtocol(Integer protocol);
	List<Consultation> findAll();

}
