module SistemaGH {
	
	exports gui;
	
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
	opens gui to javafx.fxml;
	
	
}
