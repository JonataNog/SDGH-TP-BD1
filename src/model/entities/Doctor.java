package model.entities;

import java.util.Objects;

public class Doctor {
	
	private String crm;
	private String name;
	
	public Doctor() {
	}
	
	public Doctor(String crm, String name) {
		this.crm = crm;
		this.name = name;
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
		return "Doctor [crm=" + crm + ", name=" + name + "]";
	}

}
