package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemClinic;
	
	@FXML
	private MenuItem menuItemDoctor;
	
	@FXML
	private MenuItem menuItemPatient;
	
	@FXML
	private MenuItem menuItemConsultation;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemClinicAction() {
		System.out.println("onMenuItemClinicAction");
	}
	
	@FXML
	public void onMenuItemDoctorAction() {
		System.out.println("onMenuItemDoctorAction");
	}
	
	@FXML
	public void onMenuItemPatientAction() {
		System.out.println("onMenuItemPatientAction");
	}
	
	@FXML
	public void onMenuItemConsultationAction() {
		System.out.println("onMenuItemConsultationAction");
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		System.out.println("onMenuItemAboutAction");
	}
	

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
