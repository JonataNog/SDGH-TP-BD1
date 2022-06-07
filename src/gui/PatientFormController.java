package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.entities.Patient;
import model.exceptions.ValidationException;
import model.services.PatientService;

public class PatientFormController implements Initializable{
	
	private Patient entity;
	
	private PatientService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtCpf;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtConvenio;
	
	@FXML
	private RadioButton rdSex;
	
	@FXML
	private ToggleGroup sexGroup;
	
	@FXML
	private DatePicker dpBirthDate;
	
	@FXML
	private Label labelErrorCpf;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorSex;
	
	@FXML
	private Label labelErrorBirthDate;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch(ValidationException e ) {
			setErrorMessages(e.getErrors());
		}
		catch(DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Patient getFormData() {
Patient obj = new Patient();
		
		ValidationException exception = new ValidationException("Validation error");
		
		if(txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addError("cpf", "Field can't be empty");
		}
		obj.setCpf(txtCpf.getText());
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		obj.setConvenio(txtConvenio.getText());
		rdSex = (RadioButton) sexGroup.getSelectedToggle();
		obj.setSex(rdSex.getText());
		if(dpBirthDate.getValue() == null) {
			exception.addError("birthDate", "Field can't be empty");
		}
		else {
			Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setBirthDate(Date.from(instant));
		}
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public void setPatient(Patient entity) {
		this.entity = entity;
	}
	
	public void setPatientService(PatientService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldMaxLength(txtCpf, 18);
		Constraints.setTextFieldMaxLength(txtName, 15);
		Constraints.setTextFieldMaxLength(txtConvenio, 15);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtCpf.setText(entity.getCpf());
		txtName.setText(entity.getName());
		txtConvenio.setText(entity.getConvenio());
		if(rdSex != null) {
			rdSex = (RadioButton) sexGroup.getSelectedToggle();
			rdSex.setText(entity.getSex());
		}
		if(entity.getBirthDate() != null) {
			dpBirthDate.setValue(LocalDateTime.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()).toLocalDate());
		}
	}

	public void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("cpf")) {
			labelErrorCpf.setText(errors.get("cpf"));
		}
		if(fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
		if(fields.contains("sex")) {
			labelErrorSex.setText(errors.get("sex"));
		}
		if(fields.contains("birthDate")) {
			labelErrorBirthDate.setText(errors.get("birthDate"));
		}
	}
}
