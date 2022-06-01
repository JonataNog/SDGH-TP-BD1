package model.dao;

import java.util.List;

import model.entities.Parentage;

public interface ParentageDao {
	
	void insert(Parentage obj);
	void update(Parentage obj);
	void delete(Parentage obj);
	Parentage findByCpfs(String cpf);
	List<Parentage> findAll();
	
}
