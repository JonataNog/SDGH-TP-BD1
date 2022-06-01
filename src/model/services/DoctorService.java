package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Doctor;

public class DoctorService {
	
	public List<Doctor> findAll(){
		List<Doctor> list = new ArrayList<>();
		list.add(new Doctor("123456789", "Antonio"));
		list.add(new Doctor("987654321", "Ciro"));
		return list;
	}


}
