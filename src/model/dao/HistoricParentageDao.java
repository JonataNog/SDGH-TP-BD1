package model.dao;

import model.entities.HistoricParentage;
import model.entities.Parentage;

public interface HistoricParentageDao {
	
	void insert(HistoricParentage obj);
	void update(HistoricParentage obj);
	void delete(HistoricParentage obj);
	Parentage findByCpfName(String cpf, String historic);

}
