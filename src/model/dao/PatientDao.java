package model.dao;

import java.util.List;

import model.entities.Patient;

public interface PatientDao {

	void insert(Patient obj);
	void update(Patient obj);
	void deleteByCpf(Patient obj);
	Patient findByCpf(String cpf);
	List<Patient> findAll();
	
}
