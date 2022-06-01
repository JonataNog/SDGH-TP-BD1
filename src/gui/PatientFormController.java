package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Patient;

public class PatientFormController implements Initializable{
	
	private Patient entity;
	
	@FXML
	private TextField txtCpf;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtConvenio;
	
	@FXML
	private TextField txtSex;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	private Label labelErrorCpf;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorConvenio;
	
	@FXML
	private Label labelErrorSex;
	
	@FXML
	public void onBtSaveAction() {
		System.out.println("onBtSaveAction");
	}
	
	@FXML
	public void onBtCancelAction() {
		System.out.println("onBtCancelAction");
	}
	
	public void setPatient(Patient entity) {
		this.entity = entity;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldMaxLength(txtCpf, 14);
		Constraints.setTextFieldMaxLength(txtName, 40);
		Constraints.setTextFieldMaxLength(txtConvenio, 30);
		Constraints.setTextFieldMaxLength(txtSex, 1);
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtCpf.setText(entity.getCpf());
		txtName.setText(entity.getName());
		txtConvenio.setText(entity.getConvenio());
		txtSex.setText(entity.getSex());
	}

}
