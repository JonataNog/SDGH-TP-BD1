package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import model.services.ClinicService;
import model.services.DoctorService;
import model.services.ParentageService;
import model.services.PatientService;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemClinic;
	
	@FXML
	private MenuItem menuItemDoctor;
	
	@FXML
	private MenuItem menuItemPatient;
	
	@FXML
	private MenuItem menuItemParentage;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemClinicAction() {
		loadView("/gui/ClinicList.fxml", (ClinicListController controller) -> {
			controller.setClinicService(new ClinicService());
			controller.updateTableView();
			});
	}
	
	@FXML
	public void onMenuItemDoctorAction() {
		loadView("/gui/DoctorList.fxml", (DoctorListController controller) -> {
			controller.setDoctorService(new DoctorService());
			controller.updateTableView();
			});
	}
	
	@FXML
	public void onMenuItemPatientAction() {
		loadView("/gui/PatientList.fxml", (PatientListController controller) -> {
			controller.setPatientService(new PatientService());
			controller.updateTableView();
			});
	}
	
	@FXML
	public void onMenuItemParentageAction() {
		loadView("/gui/ParentageList.fxml", (ParentageListController controller) -> {
			controller.setParentageService(new ParentageService());
			controller.updateTableView();
			});
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}
	

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
	}
	
	public synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox)((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear(); 
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVbox.getChildren());
			
			T controller = loader.getController();
			initializingAction.accept(controller);
		}
		catch(IOException e) {
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
