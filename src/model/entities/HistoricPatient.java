package model.entities;

import java.util.Arrays;
import java.util.Objects;

public class HistoricPatient {
	
	private String[] historic;
	
	private Patient patient;

	public HistoricPatient(String[] historic, Patient patient) {
		this.historic = historic;
		this.patient = patient;
	}

	public String[] getHistoric() {
		return historic;
	}

	public void setHistoric(String[] historic) {
		this.historic = historic;
	}
	
	public String getPatientCpf() {
		return patient.getCpf();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(historic);
		result = prime * result + Objects.hash(patient);
		return result;
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
		return Arrays.equals(historic, other.historic) && Objects.equals(patient, other.patient);
	}

	@Override
	public String toString() {
		return "HistoricPatient [historic=" + Arrays.toString(historic) + "]";
	}
	
}