module SistemaGH {
	requires javafx.controls;
	
	opens application to javafx.graphics, javafx.fxml;
	requires java.sql;
}
