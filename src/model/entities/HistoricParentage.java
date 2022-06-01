package model.entities;

public class HistoricParentage {

	private String historic;
	
	private Parentage parentage;

	public HistoricParentage(String historic, Parentage parentage) {
		this.historic = historic;
		this.parentage = parentage;
	}

	public String getHistoric() {
		return historic;
	}

	public void setHistoric(String historic) {
		this.historic = historic;
	}

	public Parentage getParentage() {
		return parentage;
	}

	public void setParentage(Parentage parentage) {
		this.parentage = parentage;
	}
	
}