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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Clinic;
import model.entities.Consultation;
import model.entities.Doctor;
import model.entities.Patient;
import model.exceptions.ValidationException;
import model.services.ClinicService;
import model.services.ConsultationService;
import model.services.DoctorService;
import model.services.PatientService;

public class ConsultationFormController implements Initializable {

	private Consultation entity;

	private ConsultationService service;
	private PatientService patientService;
	private ClinicService clinicService;
	private DoctorService doctorService;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtProtocol;

	@FXML
	private DatePicker dpDate;

	@FXML
	private TextField txtLaudo;

	@FXML
	private TextField txtMedication;
	
	@FXML
	private TextField txtDoctorName;
	
	@FXML
	private TextField txtClinicName;

	@FXML
	private TextField txtPatientName;

	@FXML
	private TextField txtDoctor;
	
	@FXML
	private TextField txtClinic;
	
	@FXML
	private TextField txtPatient;
	
	@FXML
	private ComboBox<Doctor> comboBoxDoctor;
	
	@FXML
	private ComboBox<Clinic> comboBoxClinic;
	
	@FXML
	private ComboBox<Patient> comboBoxPatient;

	@FXML
	private Label labelErrorDate;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	private ObservableList<Doctor> obsListDoctor;
	private ObservableList<Clinic> obsListClinic;
	private ObservableList<Patient> obsListPatient;

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

	private Consultation getFormData() {
		Consultation obj = new Consultation();

		ValidationException exception = new ValidationException("Validation error");
		
		
		obj.setProtocol(Utils.tryParseToInt(txtProtocol.getText()));
		if (dpDate.getValue() == null) {
			exception.addError("date", "Field can't be empty");
		}
		else {
			Instant instant = Instant.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setDate(Date.from(instant));
		}
		obj.setLaudo(txtLaudo.getText());
		obj.setMedication(txtMedication.getText());
		
		obj.setDoctor(comboBoxDoctor.getValue());
		obj.setClinic(comboBoxClinic.getValue());
		obj.setPatient(comboBoxPatient.getValue());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	public void setConsultation(Consultation entity) {
		this.entity = entity;
	}

	public void setServices(ConsultationService service, PatientService patientService, DoctorService doctorService,
			ClinicService clinicService) {
		this.service = service;
		this.clinicService = clinicService;
		this.doctorService = doctorService;
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
		Constraints.setTextFieldInteger(txtProtocol);
		Utils.formatDatePicker(dpDate, "dd/MM/yyyy");
		Constraints.setTextFieldMaxLength(txtLaudo, 60);
		Constraints.setTextFieldMaxLength(txtMedication, 60);
		
		initializeComboBoxDoctor();
		initializeComboBoxClinic();
		initializeComboBoxPatient();
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		txtProtocol.setText(String.valueOf(entity.getProtocol()));
		if(entity.getDate() != null) {
			dpDate.setValue(LocalDateTime.ofInstant(entity.getDate().toInstant(), ZoneId.systemDefault()).toLocalDate());
		}
		txtLaudo.setText(entity.getLaudo());
		txtMedication.setText(entity.getMedication());
		
		if(entity.getDoctor() == null) {
			comboBoxDoctor.getSelectionModel().selectFirst();
		}
		else {
			comboBoxDoctor.setValue(entity.getDoctor());
		}
		
		if(entity.getClinic() == null) {
			comboBoxClinic.getSelectionModel().selectFirst();
		}
		else {
			comboBoxClinic.setValue(entity.getClinic());
		}
		
		if(entity.getPatient() == null) {
			comboBoxPatient.getSelectionModel().selectFirst();
		}
		else {
			comboBoxPatient.setValue(entity.getPatient());
		}

	}

	public void loadAssociatedObjetcs() {
		if (patientService == null || clinicService == null || doctorService == null) {
			throw new IllegalStateException("One service is null");
		}
		List<Doctor> listDoctor = doctorService.findAll();
		List<Clinic> listClinic = clinicService.findAll();
		List<Patient> listPatient = patientService.findAll();
		obsListDoctor = FXCollections.observableArrayList(listDoctor);
		obsListClinic = FXCollections.observableArrayList(listClinic);
		obsListPatient = FXCollections.observableArrayList(listPatient);
		comboBoxDoctor.setItems(obsListDoctor);
		comboBoxClinic.setItems(obsListClinic);
		comboBoxPatient.setItems(obsListPatient);
	}

	public void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("date")) {
			labelErrorDate.setText(errors.get("date"));
		}
		
	}
	
	private void initializeComboBoxDoctor() {
		Callback<ListView<Doctor>, ListCell<Doctor>> factory = lv -> new ListCell<Doctor>() {
			@Override
			protected void updateItem(Doctor item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		comboBoxDoctor.setCellFactory(factory);
		comboBoxDoctor.setButtonCell(factory.call(null));
	}

	private void initializeComboBoxClinic() {
		Callback<ListView<Clinic>, ListCell<Clinic>> factory = lv -> new ListCell<Clinic>() {
			@Override
			protected void updateItem(Clinic item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		comboBoxClinic.setCellFactory(factory);
		comboBoxClinic.setButtonCell(factory.call(null));
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
