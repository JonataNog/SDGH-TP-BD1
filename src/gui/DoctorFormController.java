package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Doctor;

public class DoctorFormController implements Initializable{
	
	private Doctor entity;
	
	@FXML
	private TextField txtCrm;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtSpecialization;
	
	@FXML
	private TextField txtClinic;
	
	@FXML
	private Button btSave;
	
	@FXML 
	private Button btCancel;
	
	@FXML
	private Label labelErrorCrm;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorSpecialization;
	
	@FXML
	private Label labelErrorClinic;
	
	@FXML
	public void onBtSaveAction() {
		System.out.println("onBtSaveAction");
	}
	
	@FXML 
	public void onBtCancelAction() {
		System.out.println("onBtCancelAction");
	}
	
	public void setDoctor(Doctor entity) {
		this.entity = entity;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldMaxLength(txtCrm, 9);
		Constraints.setTextFieldMaxLength(txtName, 40);
		Constraints.setTextFieldMaxLength(txtSpecialization, 30);
		Constraints.setTextFieldMaxLength(txtClinic, 30);
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtCrm.setText(entity.getCrm());
		txtName.setText(entity.getName());
		txtSpecialization.setText(entity.getSpecialization());
		txtClinic.setText(String.valueOf(entity.getClinic()));
	}

}
