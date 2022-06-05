package model.dao;

import db.DB;
import model.dao.impl.ClinicDaoJDBC;
import model.dao.impl.ConsultationDaoJDBC;
import model.dao.impl.DoctorDaoJDBC;
import model.dao.impl.ParentageDaoJDBC;
import model.dao.impl.PatientDaoJDBC;

public class DaoFactory {

	public static ClinicDao createClinicDao() {
		return new ClinicDaoJDBC(DB.getConnection());
	}
	
	public static ConsultationDao createConsultationDao() {
		return new ConsultationDaoJDBC(DB.getConnection());
	}
	
	public static DoctorDao createDoctorDao() {
		return new DoctorDaoJDBC(DB.getConnection());
	}
	
	public static PatientDao createPatientDao() {
		return new PatientDaoJDBC(DB.getConnection());
	}
	
	public static ParentageDao createParentageDao() {
		return new ParentageDaoJDBC(DB.getConnection());
	}
	
	public static HistoricParentageDao createHistoricParentageDao() {
		return null;
	}
	
	public static HistoricPatientDao createHistoricPatientDao() {
		return null;
	}
	
}
