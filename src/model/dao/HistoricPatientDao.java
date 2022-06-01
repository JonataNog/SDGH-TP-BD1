package model.dao;

import model.entities.HistoricPatient;
import model.entities.Parentage;

public interface HistoricPatientDao {
	
	void insert(HistoricPatient obj);
	void update(HistoricPatient obj);
	void delete(HistoricPatient obj);
	Parentage findByCpfName(String cpf, String historic);

}
