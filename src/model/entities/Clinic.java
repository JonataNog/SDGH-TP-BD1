package model.entities;

import java.util.Objects;

public class Clinic {

	private String cnpj;
	private String name;
	private String local;
	
	public Clinic() {
	}
	
	public Clinic(String cnpj, String name, String local) {
		this.cnpj = cnpj;
		this.name = name;
		this.local = local;
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

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
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
		return "Clinic [cnpj=" + cnpj + ", name=" + name + ", local=" + local + "]";
	}

}
