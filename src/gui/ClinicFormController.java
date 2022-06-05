package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
		obj.setCnpj(txtCnpj.getText());
		obj.setName(txtName.getText());
		obj.setLocal(txtLocal.getText());
		
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
		Constraints.setTextFieldMaxLength(txtName, 40);
		Constraints.setTextFieldMaxLength(txtLocal, 30);
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtCnpj.setText(entity.getCnpj());
		txtName.setText(entity.getName());
		txtLocal.setText(entity.getLocal());
	}

}
