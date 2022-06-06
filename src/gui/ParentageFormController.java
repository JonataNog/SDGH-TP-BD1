package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Parentage;
import model.entities.Patient;
import model.exceptions.ValidationException;
import model.services.ParentageService;
import model.services.PatientService;

public class ParentageFormController implements Initializable {

	private Parentage entity;

	private ParentageService service;
	private PatientService patientService;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtNameParent;

	@FXML
	private TextField txtCpf;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtParentage;

	@FXML
	private ComboBox<Patient> comboBoxPatient;

	@FXML
	private Label labelErrorNameParent;

	@FXML
	private Label labelErrorCpf;

	@FXML
	private Label labelErrorName;

	@FXML
	private Label labelErrorParentage;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	private ObservableList<Patient> obsList;

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Parentage getFormData() {
		Parentage obj = new Parentage();

		ValidationException exception = new ValidationException("Validation error");
		
		obj.setPatient(comboBoxPatient.getValue());

		if (txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addError("cpf", "Field can't be empty");
		}
		obj.setCpf(txtCpf.getText());
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		if (txtParentage.getText() == null || txtParentage.getText().trim().equals("")) {
			exception.addError("parentage", "Field can't be empty");
		}
		obj.setParentage(txtParentage.getText());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	public void setParentage(Parentage entity) {
		this.entity = entity;
	}

	public void setServices(ParentageService service, PatientService patientService) {
		this.service = service;
		this.patientService = patientService;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		initializeComboBoxPatient();
		Constraints.setTextFieldMaxLength(txtCpf, 14);
		Constraints.setTextFieldMaxLength(txtName, 40);
		Constraints.setTextFieldMaxLength(txtParentage, 30);
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if(entity.getPatient() == null) {
			comboBoxPatient.getSelectionModel().selectFirst();
		}
		else {
			comboBoxPatient.setValue(entity.getPatient());
		}
		txtCpf.setText(entity.getCpf());
		txtName.setText(entity.getName());
		txtParentage.setText(entity.getParentage());

	}

	public void loadAssociatedObjetcs() {
		if (patientService == null) {
			throw new IllegalStateException("PatientService was null");
		}
		List<Patient> list = patientService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxPatient.setItems(obsList);
	}

	public void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("nameParent")) {
			labelErrorNameParent.setText(errors.get("nameParent"));
		}
		if (fields.contains("cpf")) {
			labelErrorCpf.setText(errors.get("cpf"));
		}
		if (fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
		if (fields.contains("parentage")) {
			labelErrorParentage.setText(errors.get("parentage"));
		}
	}

	private void initializeComboBoxPatient() {
		Callback<ListView<Patient>, ListCell<Patient>> factory = lv -> new ListCell<Patient>() {
			@Override
			protected void updateItem(Patient item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		comboBoxPatient.setCellFactory(factory);
		comboBoxPatient.setButtonCell(factory.call(null));
	}
}
