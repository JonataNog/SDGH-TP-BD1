package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Clinic;

public class ClinicFormController implements Initializable{
	
	private Clinic entity;

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
	public void onBtSaveAction() {
		System.out.println("onBtSaveAction");
	}
	
	@FXML
	public void onBtCancelAction() {
		System.out.println("onBtCancelAction");
	}
	
	public void setClinic(Clinic entity) {
		this.entity = entity;
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
