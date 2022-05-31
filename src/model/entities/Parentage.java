package model.entities;

import java.util.Objects;

public class Parentage {
	
	private String parentageCpf;
	private String name;
	private String parentage;
	
	private Patient patient;

	public Parentage(String parentageCpf, String name, String parentage, Patient patient) {
		this.parentageCpf = parentageCpf;
		this.name = name;
		this.parentage = parentage;
		this.patient = patient;
	}

	public String getParentageCpf() {
		return parentageCpf;
	}

	public void setParentageCpf(String parentageCpf) {
		this.parentageCpf = parentageCpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentage() {
		return parentage;
	}

	public void setParentage(String parentage) {
		this.parentage = parentage;
	}
	
	public String getPatientCpf() {
		return patient.getCpf();
	}

	@Override
	public int hashCode() {
		return Objects.hash(parentageCpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parentage other = (Parentage) obj;
		return Objects.equals(parentageCpf, other.parentageCpf);
	}

	@Override
	public String toString() {
		return "Parentage [parentageCpf=" + parentageCpf + ", name=" + name + ", parentage=" + parentage + "]";
	}

}
