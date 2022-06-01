package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Clinic;
import model.entities.Doctor;

public class DoctorService {
	
	public List<Doctor> findAll(){
		List<Doctor> list = new ArrayList<>();
		Clinic clinic1 = new Clinic("123456789", "Clinica Medica", "SP");
		Clinic clinic2 = new Clinic("987654321", "Clinica Ortopedica", "MG");
		
		list.add(new Doctor("123456789", "Antonio", "Ortopedista", clinic1));
		list.add(new Doctor("987654321", "Ciro", "Pediatra", clinic2));
		return list;
	}


}
