package model.dao;

import java.util.List;

import entities.Doctor;

public interface DoctorDao {
	
	void insert(Doctor obj);
	void update(Doctor obj);
	void deleteByCnpj(Doctor obj);
	Doctor findByCrm(Integer protocol);
	List<Doctor> findAll();

}
