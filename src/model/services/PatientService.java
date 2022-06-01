package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Patient;

public class PatientService {
	
	public List<Patient> findAll(){
		List<Patient> list = new ArrayList<>();
		list.add(new Patient("123456789", "Joao Monteiro", "Golden Cross", "M"));
		list.add(new Patient("987654321", "Maria de Lourdes", "Unimed", "F"));
		return list;
	}

}
