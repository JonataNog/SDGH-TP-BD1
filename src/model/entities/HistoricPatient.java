package model.entities;

import java.util.Objects;

public class HistoricPatient {
	
	private String historic;
	
	private Patient patient;

	public HistoricPatient(String historic, Patient patient) {
		this.historic = historic;
		this.patient = patient;
	}

	public String getHistoric() {
		return historic;
	}

	public void setHistoric(String historic) {
		this.historic = historic;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public int hashCode() {
		return Objects.hash(historic, patient);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoricPatient other = (HistoricPatient) obj;
		return Objects.equals(historic, other.historic) && Objects.equals(patient, other.patient);
	}

	@Override
	public String toString() {
		return "HistoricPatient [historic=" + historic + ", patient=" + patient + "]";
	}

}