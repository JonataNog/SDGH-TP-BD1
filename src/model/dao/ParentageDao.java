package model.dao;

import java.util.List;

import model.entities.Parentage;
import model.entities.Patient;

public interface ParentageDao {
	
	void insert(Parentage obj);
	void update(Parentage obj);
	void delete(Parentage obj);
	List<Parentage> findByPatient(Patient patient);
	List<Parentage> findAll();
	
}
