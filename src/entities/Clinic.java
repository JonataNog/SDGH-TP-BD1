package entities;

import java.util.Objects;

public class Clinic {

	private Integer cnpj;
	private String local;
	private String name;
	
	public Clinic() {
	}
	
	public Clinic(Integer cnpj, String local, String name) {
		this.cnpj = cnpj;
		this.local = local;
		this.name = name;
	}

	public Integer getCnpj() {
		return cnpj;
	}

	public void setCnpj(Integer cnpj) {
		this.cnpj = cnpj;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
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
		return "Clinic [cnpj=" + cnpj + ", local=" + local + ", name=" + name + "]";
	}
	
}
