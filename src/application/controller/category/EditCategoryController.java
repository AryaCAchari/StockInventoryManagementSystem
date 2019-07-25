package application.controller.category;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import application.databaseConnection.MysqlConnectionSingleton;
import application.model.BrandsModel;
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

public class EditCategoryController implements Initializable {
	
	Connection connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	ObservableList<String> observableBrandList = FXCollections.observableArrayList();
	ObservableList<String> observableSupplierList = FXCollections.observableArrayList();
	int id = 0;
	Stage primaryStage = new Stage();

	@FXML
	private JFXTextField editCategoryName;

	@FXML
	private ComboBox<String> editBrandName;

	@FXML
	private ComboBox<String> editSupplierName;

	@FXML
	private JFXTextArea editCategoryDiscription;

	@FXML
	private Button update;

	@FXML
	private Button cancel;
	
	@FXML
    private Button goBack;
	
	@FXML
	public String onBrandEditingHandler(ActionEvent event) {		
		String brandName = editBrandName.getValue();
		return brandName;
	}

	@FXML
	public void onCancelCategoryDetailsHandler(ActionEvent event) throws IOException {
		editCategoryName.setText(null);
		editCategoryDiscription.setText(null);
	}
	
	@FXML
    void goBackToPreviousPage(ActionEvent event) throws IOException {
		
		editCategoryName.setText(null);
		editCategoryDiscription.setText(null);
		
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/category/Category.fxml"));
		Scene scene = new Scene(root, 1024,768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("CATEGORY");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		//primaryStage.setFullScreen(true);
		primaryStage.show();
    }

	@FXML
	public String onSupplierNameHandler(ActionEvent event) {
		
		String supplierName = editSupplierName.getValue();
		return supplierName;
	}

	/**
	 * @param event
	 * @throws IOException
	 * update category details
	 */
	@FXML
	public void onUpdateCategoryDetailsHandler(ActionEvent event) throws IOException {
		
		String brandName = onBrandEditingHandler(event);
		String supplier = onSupplierNameHandler(event);
		String currentTime = Utilities.currentDateAndTime();
		
		try {
			
			String sql = "UPDATE categorydetails SET category_name=?, brand_name=?, sup_name=?, categoryDescription=?, updated_at=? WHERE cat_slno='"+this.id+"' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, editCategoryName.getText());
			preparedStatement.setString(2, brandName);
			preparedStatement.setString(3, supplier);
			preparedStatement.setString(4, editCategoryDiscription.getText());
			preparedStatement.setString(5, currentTime);
			
			int flag = preparedStatement.executeUpdate();
			
			if(flag == 1) {
				System.out.println("Data updated");
			}else {
				System.out.println("Data saving error");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		editCategoryDiscription.setText(null);
		editCategoryName.setText(null);
	}

	/**
	 * @param slNo
	 * @param categoryName
	 * @param brandName
	 * @param supplierName
	 * @param categoryDescription
	 * 
	 * get all above data to pass to each text field
	 */
	public void transferDetails(int slNo, String categoryName, String brandName, String supplierName, String categoryDescription) {
		
		this.id = slNo;
		editCategoryName.setText(categoryName);
		editBrandName.setId(brandName);
		editSupplierName.setId(supplierName);
		editCategoryDiscription.setText(categoryDescription);
		getSuppllierNameHandler(supplierName);
		getBrandNameHandler(brandName);
		
	}
	
	/**
	 *load brand details to combo box
	 */
	public void getBrandNameHandler(String brand) {
		
		try {
			
			String sql = "SELECT brand_name FROM branddetails WHERE active_indicator = 1";
			preparedStatement  = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {				
				BrandsModel brandsModel = new BrandsModel();
				brandsModel.setBrand_name(resultSet.getString("brand_name"));
				observableBrandList.add(brandsModel.getBrand_name());
				if(brand.equals(resultSet.getString("brand_name"))) {
					editBrandName.setValue(brand);
					editBrandName.setItems(observableBrandList);
				}else {
					editBrandName.setItems(observableBrandList);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get supplier name to combo box
	 */
	public void getSuppllierNameHandler(String supplier) {
		
		try {
			
			String sql = "SELECT sup_name FROM supplierdetails WHERE active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				SupplierModel supplierModel = new SupplierModel();
				supplierModel.setSupplierName(resultSet.getString("sup_name"));
				observableSupplierList.add(supplierModel.getSupplierName());
				if(supplier.equals(resultSet.getString("sup_name"))) {
					editSupplierName.setValue(supplier);
					editSupplierName.setItems(observableSupplierList);
				}else {
					editSupplierName.setItems(observableSupplierList);
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
