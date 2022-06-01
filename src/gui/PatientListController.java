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
import model.entities.Patient;
import model.services.PatientService;

public class PatientListController implements Initializable{
	
	private PatientService service;
	
	@FXML
	private TableView<Patient> tableViewPatient;
	
	@FXML
	private TableColumn<Patient, String> tableColumnCpf;
	
	@FXML
	private TableColumn<Patient, String> tableColumnName;
	
	@FXML
	private TableColumn<Patient, String> tableColumnConvenio;
	
	@FXML
	private TableColumn<Patient, String> tableColumnSex;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Patient> obsList;
	
	@FXML 
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		createDialogForm("/gui/ClinicForm.fxml", parentStage);
	}
	
	public void setPatientService(PatientService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}	

	private void initializeNodes() {
		tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnConvenio.setCellValueFactory(new PropertyValueFactory<>("convenio"));
		tableColumnSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewPatient.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Patient> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewPatient.setItems(obsList);
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