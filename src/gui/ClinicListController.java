package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Clinic;
import model.services.ClinicService;

public class ClinicListController implements Initializable{
	
	private ClinicService service;
	
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
	
	private ObservableList<Clinic> obsList;
	
	@FXML 
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	
	public void setClinicService(ClinicService service) {
		this.service = service;
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
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Clinic> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewClinic.setItems(obsList);
	}

}
