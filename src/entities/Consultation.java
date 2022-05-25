package entities;

import java.util.Date;
import java.util.Objects;

public class Consultation {
	
	private Integer protocol;
	private String medication;
	private String laudo;
	private Date date;
	
	public Consultation(Integer protocol, String medication, String laudo, Date date) {
		super();
		this.protocol = protocol;
		this.medication = medication;
		this.laudo = laudo;
		this.date = date;
	}

	public Integer getProtocol() {
		return protocol;
	}

	public void setProtocol(Integer protocol) {
		this.protocol = protocol;
	}

	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	public String getLaudo() {
		return laudo;
	}

	public void setLaudo(String laudo) {
		this.laudo = laudo;
	}

	public Date getDate() {
		return date;
	}

	public void setData(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(protocol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Consultation other = (Consultation) obj;
		return Objects.equals(protocol, other.protocol);
	}

	@Override
	public String toString() {
		return "Consultation [protocol=" + protocol + ", medication=" + medication + ", laudo=" + laudo + ", date="
				+ date + "]";
	}

}
