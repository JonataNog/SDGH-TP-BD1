package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Patient;
import model.services.PatientService;

public class PatientListController implements Initializable, DataChangeListener {

	private PatientService service;
	
	@FXML
	private TextField txtSearchCpf;

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
	private TableColumn<Patient, Date> tableColumnBirthDate;

	@FXML
	private TableColumn<Patient, Patient> tableColumnEDIT;

	@FXML
	private TableColumn<Patient, Patient> tableColumnREMOVE;

	@FXML
	private Button btNew;
	
	@FXML
	private Button btSearchCpf;
	
	@FXML
	private Button btSearchFindAll;

	private ObservableList<Patient> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Patient obj = new Patient();
		createDialogForm(obj, "/gui/PatientForm.fxml", parentStage);
	}
	
	@FXML
	public void onBtSearchCpfAction()  {
		try {
			if (txtSearchCpf.getText() == null || txtSearchCpf.getText().trim().equals("")) {
				Alerts.showAlert("Error", "Field can't be empty",null, AlertType.ERROR);
			}
			else {
				String cpf = txtSearchCpf.getText();
				Patient obj = service.findByCpf(cpf);
				if (obj == null) {
					throw new NullPointerException();
				}
				obsList = FXCollections.observableArrayList(obj);
				tableViewPatient.setItems(obsList);
				initEditButtons();
				initRemoveButtons();
			}
		}
		catch(NullPointerException e) {
			Alerts.showAlert("Error", "CPF not exist",null, AlertType.ERROR);
		}
	}
	
	@FXML
	public void onBtSearchFindAllAction() {
		updateTableView();
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
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewPatient.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Patient> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewPatient.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Patient obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			PatientFormController controller = loader.getController();
			controller.setPatient(obj);
			controller.setPatientService(new PatientService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Patient data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Patient, Patient>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Patient obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/PatientForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Patient, Patient>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Patient obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Patient obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch(Exception e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

}
