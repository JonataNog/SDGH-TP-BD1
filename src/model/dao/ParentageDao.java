package model.dao;

import java.util.List;

import model.entities.Parentage;

public interface ParentageDao {
	
	void insert(Parentage obj);
	void update(Parentage obj);
	void delete(Parentage obj);
	Parentage findByCpfName(String cpf, String name);
	List<Parentage> findAll();
	
}
