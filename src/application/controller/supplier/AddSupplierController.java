package application.controller.supplier;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import application.databaseConnection.MysqlConnectionSingleton;
import application.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AddSupplierController {
	
	private Connection con = null;
	private PreparedStatement prdStatement = null;
	boolean flag = true;
	Stage primaryStage = new Stage();
	
	@FXML
	private JFXTextField supplierName;

	@FXML
	private JFXTextField mobileNumber;

	@FXML
	private JFXTextField landlineNumber;

	@FXML
	private JFXTextArea address;

	@FXML
	private Button saveSupplier;

	@FXML
	private Button clearFeilds;
	
	@FXML
    private Button backbutton;

    @FXML
   public void gobacktoPreviousStage(ActionEvent event) throws IOException {
    	
    	((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/supplier/Supplier.fxml"));
		Scene scene = new Scene(root, 1024,768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("SUPPLIER");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		//primaryStage.setFullScreen(true);
		primaryStage.show();
   }


	@FXML
	public void refreshOrClearScreen(ActionEvent event) throws IOException {
		
		supplierName.setText(null);
		mobileNumber.setText(null);
		landlineNumber.setText(null);
		address.setText(null);
		
/*		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/supplier/Supplier.fxml"));
		Scene scene = new Scene(root, 1000, 750);
		primaryStage.setScene(scene);
		primaryStage.setTitle("SUPPLIER");
		primaryStage.isResizable();
		primaryStage.show();*/
		
	}

	/**
	 * @param event
	 * add supplier details
	 */
	@FXML
	public void saveSupplieretails(ActionEvent event) {
		
		String currentTime = Utilities.currentDateAndTime();
		try {
			
			con = MysqlConnectionSingleton.dbConnectionblock().getConnection();			
			String sql = "INSERT INTO supplierdetails(sup_name, sup_mob, sup_landnum, sup_addr, created_at, updated_at, active_indicator)VALUES(?,?,?,?,?,?,?)";		
			prdStatement = con.prepareStatement(sql);
			
			prdStatement.setString(1, supplierName.getText());
			prdStatement.setString(2, mobileNumber.getText());
			prdStatement.setString(3, landlineNumber.getText());
			prdStatement.setString(4, address.getText());
			prdStatement.setString(5, currentTime);
			prdStatement.setString(6, currentTime);
			prdStatement.setInt(7, 1);
			
			flag = prdStatement.execute();
			System.out.println("Flag value is: " +flag);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		supplierName.setText(null);
		mobileNumber.setText(null);
		landlineNumber.setText(null);
		address.setText(null);
	}

}
