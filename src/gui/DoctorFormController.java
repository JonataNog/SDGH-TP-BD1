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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Doctor;
import model.exceptions.ValidationException;
import model.services.DoctorService;

public class DoctorFormController implements Initializable{
	
	private Doctor entity;
	
	private DoctorService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtCrm;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtSpecialization;
	
	@FXML
	private Label labelErrorCnpj;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorLocal;
	
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

	private Doctor getFormData() {
		Doctor obj = new Doctor();
		
		ValidationException exception = new ValidationException("Validation error");
		
		if(txtCrm.getText() == null || txtCrm.getText().trim().equals("")) {
			exception.addError("crm", "Field can't be empty");
		}
		obj.setCrm(txtCrm.getText());
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		if(txtSpecialization.getText() == null || txtSpecialization.getText().trim().equals("")) {
			exception.addError("specialization", "Field can't be empty");
		}
		obj.setSpecialization(txtSpecialization.getText());
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public void setDoctor(Doctor entity) {
		this.entity = entity;
	}
	
	public void setDoctorService(DoctorService service) {
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
		Constraints.setTextFieldMaxLength(txtCrm, 9);
		Constraints.setTextFieldMaxLength(txtName, 40);
		Constraints.setTextFieldMaxLength(txtSpecialization, 30);
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtCrm.setText(entity.getCrm());
		txtName.setText(entity.getName());
		txtSpecialization.setText(entity.getSpecialization());
	}

	public void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("crm")) {
			labelErrorCnpj.setText(errors.get("crm"));
		}
		if(fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
		if(fields.contains("specialization")) {
			labelErrorLocal.setText(errors.get("specialization"));
		}
	}
}
