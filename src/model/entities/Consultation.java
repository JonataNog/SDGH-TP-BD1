package model.entities;

import java.util.Date;
import java.util.Objects;

public class Consultation {
	
	private Integer protocol;
	private String medication;
	private String laudo;
	private Date date;
	
	private Doctor doctor;
	private Clinic clinic;
	private Patient patient;
	
	public Consultation() {
	}
	
	public Consultation(Integer protocol, String medication, String laudo, Date date, Doctor doctor, Clinic clinic,
			Patient patient) {
		this.protocol = protocol;
		this.medication = medication;
		this.laudo = laudo;
		this.date = date;
		this.doctor = doctor;
		this.clinic = clinic;
		this.patient = patient;
	}



	public Integer getProtocol() {
		return protocol;
	}

	public void setProtocol(Integer protocol) {
		this.protocol = protocol;
	}

	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	public String getLaudo() {
		return laudo;
	}

	public void setLaudo(String laudo) {
		this.laudo = laudo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(protocol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Consultation other = (Consultation) obj;
		return Objects.equals(protocol, other.protocol);
	}

	@Override
	public String toString() {
		return "Consultation [protocol=" + protocol + ", medication=" + medication + ", laudo=" + laudo + ", date="
				+ date + ", doctor=" + doctor + ", clinic=" + clinic + ", patient=" + patient + "]";
	}

}
