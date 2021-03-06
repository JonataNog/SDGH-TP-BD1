package gui;

import java.io.IOException;
import java.net.URL;
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
import model.entities.Clinic;
import model.services.ClinicService;

public class ClinicListController implements Initializable, DataChangeListener {

	private ClinicService service;
	
	@FXML
	private TextField txtSearchCnpj;

	@FXML
	private TableView<Clinic> tableViewClinic;

	@FXML
	private TableColumn<Clinic, String> tableColumnCnpj;

	@FXML
	private TableColumn<Clinic, String> tableColumnName;

	@FXML
	private TableColumn<Clinic, String> tableColumnLocal;

	@FXML
	private TableColumn<Clinic, Clinic> tableColumnEDIT;

	@FXML
	private TableColumn<Clinic, Clinic> tableColumnREMOVE;

	@FXML
	private Button btNew;
	
	@FXML
	private Button btSearchCnpj;
	
	@FXML
	private Button btFindAll;

	private ObservableList<Clinic> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Clinic obj = new Clinic();
		createDialogForm(obj, "/gui/ClinicForm.fxml", parentStage);
	}
	
	@FXML
	public void onBtSearchCnpjAction() {
		try {
			if (txtSearchCnpj.getText() == null || txtSearchCnpj.getText().trim().equals("")) {
				Alerts.showAlert("Error", "Field can't be empty",null, AlertType.ERROR);
			}
			else {
				String cnpj = txtSearchCnpj.getText();
				Clinic obj = service.findByCnpj(cnpj);
				if (obj == null) {
					throw new NullPointerException();
				}
				obsList = FXCollections.observableArrayList(obj);
				tableViewClinic.setItems(obsList);
				initEditButtons();
				initRemoveButtons();
			}
		}
		catch(NullPointerException e) {
			Alerts.showAlert("Error", "CRM not exist",null, AlertType.ERROR);
		}
	}
	
	@FXML
	public void onBtSearchFindAllAction() {
		updateTableView();
	}

	public void setClinicService(ClinicService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnLocal.setCellValueFactory(new PropertyValueFactory<>("local"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewClinic.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Clinic> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewClinic.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Clinic obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ClinicFormController controller = loader.getController();
			controller.setClinic(obj);
			controller.setClinicService(new ClinicService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Clinic data");
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
		tableColumnEDIT.setCellFactory(param -> new TableCell<Clinic, Clinic>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Clinic obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/ClinicForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Clinic, Clinic>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Clinic obj, boolean empty) {
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

	private void removeEntity(Clinic obj) {
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
