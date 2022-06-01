package model.entities;

public class Parentage {
	
	private String parentageCpf;
	private String name;
	private String parentage;
	
	private Patient patient;

	public Parentage() {
	}

	public Parentage(String parentageCpf, String name, String parentage, Patient patient) {
		this.parentageCpf = parentageCpf;
		this.name = name;
		this.parentage = parentage;
		this.patient = patient;
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

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getParentageCpf() {
		return parentageCpf;
	}

	public void setParentageCpf(String parentageCpf) {
		this.parentageCpf = parentageCpf;
	}
	
	@Override
	public String toString() {
		return "Parentage [parentageCpf=" + parentageCpf + ", name=" + name + ", parentage=" + parentage + ", patient="
				+ patient + "]";
	}

}
