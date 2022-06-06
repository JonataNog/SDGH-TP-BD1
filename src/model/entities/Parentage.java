package model.entities;

public class Parentage {
	
	private String cpf;
	private String name;
	private String parentage;
	private String nameParent;
	
	private Patient patient;

	public Parentage() {
	}

	public Parentage(String cpf, String name, String parentage, Patient patient) {
		this.cpf = cpf;
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
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNameParent() {
		return nameParent;
	}

	public void setNameParent(Patient obj) {
		this.nameParent = obj.getName();
	}

	@Override
	public String toString() {
		return "Parentage [cpf=" + cpf + ", name=" + name + ", parentage=" + parentage + ", patient=" + patient + "]";
	}

}
