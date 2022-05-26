package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Clinic;

public class ClinicService {
	
	public List<Clinic> findAll(){
		List<Clinic> list = new ArrayList<>();
		list.add(new Clinic(123456789, "Clinica Medica", "Rua Sergipe"));
		list.add(new Clinic(987654321, "Clinica Ortopedica", "Rua Alagoas"));
		return list;
	}

}
