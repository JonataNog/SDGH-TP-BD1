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
import model.entities.Clinic;
import model.exceptions.ValidationException;
import model.services.ClinicService;

public class ClinicFormController implements Initializable{
	
	private Clinic entity;
	
	private ClinicService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtCnpj;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtLocal;
	
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

	private Clinic getFormData() {
		Clinic obj = new Clinic();
		
		ValidationException exception = new ValidationException("Validation error");
		
		if(txtCnpj.getText() == null || txtCnpj.getText().trim().equals("")) {
			exception.addError("cnpj", "Field can't be empty");
		}
		obj.setCnpj(txtCnpj.getText());
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		if(txtLocal.getText() == null || txtLocal.getText().trim().equals("")) {
			exception.addError("local", "Field can't be empty");
		}
		obj.setLocal(txtLocal.getText());
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public void setClinic(Clinic entity) {
		this.entity = entity;
	}
	
	public void setClinicService(ClinicService service) {
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
		Constraints.setTextFieldMaxLength(txtCnpj, 18);
		Constraints.setTextFieldMaxLength(txtName, 15);
		Constraints.setTextFieldMaxLength(txtLocal, 60);
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtLocal.setText(entity.getLocal());
		txtCnpj.setText(entity.getCnpj());
		txtName.setText(entity.getName());
		
	}

	public void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("cnpj")) {
			labelErrorCnpj.setText(errors.get("cnpj"));
		}
		if(fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
		if(fields.contains("specialization")) {
			labelErrorLocal.setText(errors.get("local"));
		}
	}
}
