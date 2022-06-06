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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Parentage;
import model.services.ParentageService;
import model.services.PatientService;

public class ParentageListController implements Initializable, DataChangeListener {

	private ParentageService service;

	@FXML
	private TableView<Parentage> tableViewParentage;

	@FXML
	private TableColumn<Parentage, String> tableColumnNameParent;

	@FXML
	private TableColumn<Parentage, String> tableColumnCpf;

	@FXML
	private TableColumn<Parentage, String> tableColumnName;
	
	@FXML
	private TableColumn<Parentage, String> tableColumnParentage;

	@FXML
	private TableColumn<Parentage, Parentage> tableColumnEDIT;

	@FXML
	private TableColumn<Parentage, Parentage> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<Parentage> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Parentage obj = new Parentage();
		createDialogForm(obj, "/gui/ParentageForm.fxml", parentStage);
	}

	public void setParentageService(ParentageService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnNameParent.setCellValueFactory(new PropertyValueFactory<>("nameParent"));
		tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnParentage.setCellValueFactory(new PropertyValueFactory<>("parentage"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewParentage.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Parentage> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewParentage.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Parentage obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ParentageFormController controller = loader.getController();
			controller.setParentage(obj);
			controller.setServices(new ParentageService(), new PatientService());
			controller.loadAssociatedObjetcs();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Parentage data");
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
		tableColumnEDIT.setCellFactory(param -> new TableCell<Parentage, Parentage>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Parentage obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/ParentageForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Parentage, Parentage>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Parentage obj, boolean empty) {
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

	private void removeEntity(Parentage obj) {
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
