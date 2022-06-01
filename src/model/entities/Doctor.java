package model.entities;

import java.util.Objects;

public class Doctor {
	
	private String crm;
	private String name;
	private String specialization;
	
	private Clinic clinic;
	
	public Doctor() {
	}
	
	public Doctor(String crm, String name, String specialization, Clinic clinic) {
		this.crm = crm;
		this.name = name;
		this.specialization = specialization;
		this.clinic = clinic;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	@Override
	public int hashCode() {
		return Objects.hash(crm);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Doctor other = (Doctor) obj;
		return Objects.equals(crm, other.crm);
	}

	@Override
	public String toString() {
		return "Doctor [crm=" + crm + ", name=" + name + ", specialization=" + specialization + ", clinic=" + clinic
				+ "]";
	}
}
