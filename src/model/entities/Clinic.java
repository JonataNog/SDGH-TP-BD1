package model.entities;

import java.util.Objects;

public class Clinic {

	private String cnpj;
	private String name;
	
	public Clinic() {
	}
	
	public Clinic(String cnpj, String name) {
		this.cnpj = cnpj;
		this.name = name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Clinic other = (Clinic) obj;
		return Objects.equals(cnpj, other.cnpj);
	}

	@Override
	public String toString() {
		return "Clinic [cnpj=" + cnpj + ", name=" + name + "]";
	}

}
