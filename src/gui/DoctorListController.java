package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Doctor;
import model.services.DoctorService;

public class DoctorListController implements Initializable{
	
	private DoctorService service;
	
	@FXML
	private TableView<Doctor> tableViewDoctor;
	
	@FXML
	private TableColumn<Doctor, String> tableColumnCrm;
	
	@FXML
	private TableColumn<Doctor, String> tableColumnName;
	
	@FXML
	private TableColumn<Doctor, String> tableColumnSpecialization;
	
	@FXML
	private TableColumn<Doctor, String> tableColumnClinic;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Doctor> obsList;
	
	@FXML 
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		createDialogForm("/gui/ClinicForm.fxml", parentStage);
	}
	
	public void setDoctorService(DoctorService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}	

	private void initializeNodes() {
		tableColumnCrm.setCellValueFactory(new PropertyValueFactory<>("crm"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
		tableColumnClinic.setCellValueFactory(new PropertyValueFactory<>("clinic"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDoctor.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Doctor> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDoctor.setItems(obsList);
	}
	
	private void createDialogForm(String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Doctor data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
