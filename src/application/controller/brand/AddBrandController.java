package application.controller.brand;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import application.databaseConnection.MysqlConnectionSingleton;
import application.model.SupplierModel;
import application.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class AddBrandController implements Initializable{
	
	Connection connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
	PreparedStatement preparedStatment = null;
	ResultSet resultSet = null;
	ObservableList<String> supplierOptions = FXCollections.observableArrayList();
	public String comboboxValue = null;
	boolean flag = true;
	Stage primaryStage = new Stage();
	
	@FXML
	private JFXTextField brandName;

	@FXML
	private  ComboBox<String> supplierName;

	@FXML
	private Button saveBrandName;

	@FXML
	private Button cancelButtonName;
	
	@FXML
	public void goToPreviousPage(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/brand/Brands.fxml"));
		Scene scene = new Scene(root, 1024,768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("BRANDS");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		//primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	public void cancelButtenNameHandler(ActionEvent event) throws IOException {
		
		brandName.setText(null);
		supplierName.setItems(null);	
		
	}
	
	@FXML
    public void comboboxSetOnAction(ActionEvent event) {
		
		comboboxValue = supplierName.getValue();
		System.out.println("Selected comboBox value: " +comboboxValue);
    }


	@FXML
	public void saveBrandNameHandler(ActionEvent event) throws IOException {
		
		String currentTime = Utilities.currentDateAndTime();
		System.out.println(currentTime);
		
		try {
			
			String sql = "INSERT INTO branddetails(brand_name, sup_name, created_at, updated_at, active_indicator)VALUES(?, ?, ?, ?, ?)";
			preparedStatment = connection.prepareStatement(sql);
			preparedStatment.setString(1, brandName.getText());
			preparedStatment.setString(2, this.comboboxValue);
			preparedStatment.setString(3, currentTime);
			preparedStatment.setString(4, currentTime);
			preparedStatment.setInt(5, 1);
			
			flag = preparedStatment.execute();
			
			if(flag == false) {
				System.out.println("New Brand Added");
			}else {
				System.out.println("Data connection problem");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		brandName.setText(null);
		supplierName.setItems(null);
	}

	public void supplierNameHandler() {
		
		try {
			
			String sql = "SELECT sup_name FROM supplierdetails WHERE active_indicator=1";
			preparedStatment = connection.prepareStatement(sql);
			resultSet = preparedStatment.executeQuery();
			
			while(resultSet.next()) {
				SupplierModel supplierModel = new SupplierModel();
				supplierModel.setName(resultSet.getString("sup_name"));
				supplierOptions.addAll(supplierModel.getName());
				supplierName.setItems(supplierOptions);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		supplierNameHandler();
		
	}
}
