package model.entities;

import java.util.Date;
import java.util.Objects;

public class Patient {
	
	private String cpf;
	private String name;
	private String convenio;
	private String sex;
	private Date birthDate;
	
	public Patient() {
	}
	
	public Patient(String cpf, String name, String convenio, String sex, Date birthDate) {
		this.cpf = cpf;
		this.name = name;
		this.convenio = convenio;
		this.sex = sex;
		this.birthDate = birthDate;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		return Objects.equals(cpf, other.cpf);
	}

	@Override
	public String toString() {
		return "Patient [cpf=" + cpf + ", name=" + name + ", convenio=" + convenio + ", sex=" + sex + ", birthDate="
				+ birthDate + "]";
	}

}
