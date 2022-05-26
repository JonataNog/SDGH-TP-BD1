package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

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
		loadView("/gui/About.fxml");
	}
	

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
	}
	
	public synchronized void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox)((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear(); 
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVbox.getChildren());
		}
		catch(IOException e) {
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
