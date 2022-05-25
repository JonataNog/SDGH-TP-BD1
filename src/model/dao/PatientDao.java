package model.dao;

import java.util.List;

import entities.Patient;

public interface PatientDao {

	void insert(Patient obj);
	void update(Patient obj);
	void deleteByCnpj(Patient obj);
	Patient findByCpf(Integer protocol);
	List<Patient> findAll();
	
}
