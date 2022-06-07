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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Consultation;
import model.services.ClinicService;
import model.services.ConsultationService;
import model.services.DoctorService;
import model.services.PatientService;

public class ConsultationListController implements Initializable, DataChangeListener {

	private ConsultationService service;

	@FXML
	private TableView<Consultation> tableViewConsultation;

	@FXML
	private TableColumn<Consultation, String> tableColumnProtocol;

	@FXML
	private TableColumn<Consultation, Date> tableColumnDate;

	@FXML
	private TableColumn<Consultation, String> tableColumnLaudo;
	
	@FXML
	private TableColumn<Consultation, String> tableColumnMedication;

	@FXML
	private TableColumn<Consultation, String> tableColumnDoctorName;
	
	@FXML
	private TableColumn<Consultation, String> tableColumnClinicName;
	
	@FXML
	private TableColumn<Consultation, String> tableColumnPatientNome;

	@FXML
	private TableColumn<Consultation, Consultation> tableColumnEDIT;

	@FXML
	private TableColumn<Consultation, Consultation> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<Consultation> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Consultation obj = new Consultation();
		createDialogForm(obj, "/gui/ConsultationForm.fxml", parentStage);
	}

	public void setConsultationService(ConsultationService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnProtocol.setCellValueFactory(new PropertyValueFactory<>("protocol"));
		tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		Utils.formatTableColumnDate(tableColumnDate, "dd/MM/yyyy");
		tableColumnLaudo.setCellValueFactory(new PropertyValueFactory<>("laudo"));
		tableColumnMedication.setCellValueFactory(new PropertyValueFactory<>("medication"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewConsultation.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Consultation> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewConsultation.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Consultation obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ConsultationFormController controller = loader.getController();
			controller.setConsultation(obj);
			controller.setServices(new ConsultationService(), new PatientService(), new DoctorService(), new ClinicService());
			controller.loadAssociatedObjetcs();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Consultation data");
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
		tableColumnEDIT.setCellFactory(param -> new TableCell<Consultation, Consultation>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Consultation obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/ConsultationForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Consultation, Consultation>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Consultation obj, boolean empty) {
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

	private void removeEntity(Consultation obj) {
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
