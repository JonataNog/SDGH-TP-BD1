package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Clinic;

public class ClinicListController implements Initializable{
	
	@FXML
	private TableView<Clinic> tableViewClinic;
	
	@FXML
	private TableColumn<Clinic, String> tableColumnCnpj;
	
	@FXML
	private TableColumn<Clinic, String> tableColumnName;
	
	@FXML
	private TableColumn<Clinic, String> tableColumnLocal;
	
	@FXML
	private Button btNew;
	
	@FXML 
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
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

}
