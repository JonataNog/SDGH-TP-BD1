package model.dao;

import db.DB;
import model.dao.impl.ClinicDaoJDBC;

public class DaoFactory {

	public static ClinicDao createClinicDao() {
		return new ClinicDaoJDBC(DB.getConnection());
	}
	
	public static ConsultationDao createConsultationDao() {
		return null;
	}
	
	public static DoctorDao createDoctorDao() {
		return null;
	}
	
	public static PatientDao createPatientDao() {
		return null;
	}
	
	public static ParentageDao createParentageDao() {
		return null;
	}
	
	public static HistoricParentageDao createHistoricParentageDao() {
		return null;
	}
	
	public static HistoricPatientDao createHistoricPatientDao() {
		return null;
	}
	
}
