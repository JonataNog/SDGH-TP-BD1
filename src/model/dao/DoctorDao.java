package model.dao;

import java.util.List;

import model.entities.Doctor;

public interface DoctorDao {
	
	void insert(Doctor obj);
	void update(Doctor obj);
	void deleteByCrm(Doctor obj);
	Doctor findByCrm(String crm);
	List<Doctor> findAll();

}
