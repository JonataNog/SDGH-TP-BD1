package model.dao;

import java.util.List;

import model.entities.Clinic;

public interface ClinicDao {
	
	void insert(Clinic obj);
	void update(Clinic obj);
	void deleteByCnpj(Clinic obj);
	Clinic findByCnpj(String cnpj);
	List<Clinic> findAll();

}
