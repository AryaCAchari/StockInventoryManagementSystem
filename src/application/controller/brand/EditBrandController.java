package application.controller.brand;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

public class EditBrandController implements Initializable{
	
	Connection connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
	PreparedStatement preparedStatement = null;
	Statement statement = null;
	ResultSet resultSet = null;
	
	ObservableList<String> observableList = FXCollections.observableArrayList();
	int flag = 0;
	Stage primaryStage = new Stage();
	
	@FXML
	private JFXTextField brandName;

	@FXML
	private ComboBox<String> updateSupplierName;

	@FXML
	private Button updateBrandName;

	@FXML
	private Button cancelButtonName;
	
	int id = 0;
	public void transferDetails(int brnd_slno, String brand_name, String sup_name) {
		
		this.id = brnd_slno;
		brandName.setText(brand_name);	
		
		try {
			
			String sql = "SELECT sup_name FROM supplierdetails WHERE active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				SupplierModel supplierModel = new SupplierModel();
				supplierModel.setName(resultSet.getString("sup_name"));
				observableList.addAll(supplierModel.getName());
				if(sup_name.equals(resultSet.getString("sup_name"))) {
					updateSupplierName.setValue(sup_name);
					updateSupplierName.setItems(observableList);
				}else {
					updateSupplierName.setItems(observableList);
				}				
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void cancelButtenNameHandler(ActionEvent event) throws IOException {
		
		brandName.setText(null);
		updateSupplierName.setItems(null);
		
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/brand/Brands.fxml"));
		Scene scene = new Scene(root, 1024,768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("BRAND");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		//primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	public String comboboxSetOnAction(ActionEvent event) {
		
		String comboBox = null;
		comboBox = updateSupplierName.getValue();
		System.out.println("Combobox valu: " +comboBox);
		return comboBox;
	}
	
	@FXML
    public void goToPreviousPage(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/brand/Brands.fxml"));
		Scene scene = new Scene(root, 1024,768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("BRAND");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		//primaryStage.setFullScreen(true);
		primaryStage.show();
    }

	@FXML
	public void updateBrandNameHandler(ActionEvent event) throws IOException {
		
		String suppliername = comboboxSetOnAction(event);
		String currentTime = Utilities.currentDateAndTime();
		
		try {		
			
			String sql = "UPDATE branddetails SET brand_name = ?, sup_name = ?, updated_at = ? WHERE brnd_slno = '"+this.id+"' AND active_indicator = 1";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, brandName.getText());
			preparedStatement.setString(2, suppliername);
			preparedStatement.setString(3, currentTime);
			
			flag = preparedStatement.executeUpdate();
			System.out.println("FLAG VALUE " +flag);
		}catch (Exception e) {
			e.printStackTrace();
		}
		brandName.setText(null);
		updateSupplierName.setItems(null);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
}
