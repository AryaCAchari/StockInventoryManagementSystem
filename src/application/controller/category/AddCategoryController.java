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

public class AddCategoryController implements Initializable{
	
	Connection connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	ObservableList<String> brandList = FXCollections.observableArrayList();
	ObservableList<String> supplierList = FXCollections.observableArrayList();
	Stage primaryStage = new Stage();
	
	@FXML
    private JFXTextField categoryName;

    @FXML
    private JFXTextField createdBy;

    @FXML
    private ComboBox<String> brandName;

    @FXML
    private ComboBox<String> supplierName;

    @FXML
    private JFXTextArea categoryDescription;

    @FXML
    private Button save;

    @FXML
    private Button cancel;
    
    @FXML
    private Button goBack;
    
    /**
     * load all the brand names
     */
    public void getAllBrandName() {
    	
    	try {
    		
    		String sql = "SELECT brand_name FROM branddetails WHERE active_indicator=1";
    		preparedStatement = connection.prepareStatement(sql);
    		resultSet = preparedStatement.executeQuery();
    		
    		while(resultSet.next()) {
    			
    			BrandsModel brandsModel = new BrandsModel();
    			brandsModel.setBrand_name(resultSet.getString("brand_name"));
    			brandList.add(resultSet.getString("brand_name"));
    			brandName.setItems(brandList);
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * load all supplier name
     */
    public void getSupplierNameList() {
    	
    	try {
    		
    		String sql = "SELECT sup_name FROM supplierdetails WHERE active_indicator=1";
    		preparedStatement = connection.prepareStatement(sql);
    		resultSet = preparedStatement.executeQuery();
    		
    		while(resultSet.next()) {
    			
    			SupplierModel supplierModel = new SupplierModel();
    			supplierModel.setSupplierName(resultSet.getString("sup_name"));
    			supplierList.add(supplierModel.getSupplierName());
    			supplierName.setItems(supplierList);
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    public void cancelCategoryDetails(ActionEvent event) {
    	
    	categoryName.setText(null);
    	brandName.setItems(null);
    	supplierName.setItems(null);
    	createdBy.setText(null);
    	categoryDescription.setText(null);
    }
    
    @FXML
    void goBackToPreviousPage(ActionEvent event) throws IOException {
    	
    	categoryName.setText(null);
    	brandName.setItems(null);
    	supplierName.setItems(null);
    	createdBy.setText(null);
    	categoryDescription.setText(null);
    	
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

    /**
     * @param event
     * @return select from brand combo box
     */
    @FXML
    public String onSelectBrandName(ActionEvent event) {
    	
    	String brandname = brandName.getValue();
    	return brandname;
    }

    /**
     * @param event
     * @return select from supplier combo box
     */
    @FXML
    public String onSelectSupplierName(ActionEvent event) {
    	
    	String supplier = supplierName.getValue();
    	return supplier;
    }

    /**
     * @param event
     * @throws IOException
     * save all the category details
     */
    @FXML
    public void saveCategoryDetails(ActionEvent event) throws IOException {
    	
    	String supplier = onSelectSupplierName(event);
    	String brand = onSelectBrandName(event);
    	boolean flag = true;
		String currentTime = Utilities.currentDateAndTime();
    	try {
    		
    		String sql = "INSERT INTO categorydetails(category_name, brand_name, sup_name, categoryDescription, cat_creator, cat_date, created_at, updated_at, active_indicator)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    		preparedStatement = connection.prepareStatement(sql);
    		preparedStatement.setString(1, categoryName.getText());
    		preparedStatement.setString(2, brand);
    		preparedStatement.setString(3, supplier);
    		preparedStatement.setString(4, categoryDescription.getText());
    		preparedStatement.setString(5, createdBy.getText());
    		preparedStatement.setString(6, currentTime);
    		preparedStatement.setString(7, currentTime);
    		preparedStatement.setString(8, currentTime);
    		preparedStatement.setInt(9, 1);
    		
    		flag = preparedStatement.execute();
    		if(flag == false) {
    			System.out.println("Data saved!" +flag);
    		}else {
    			System.out.println("Data saving error!" +flag);
    		}
    		
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	
    	categoryName.setText(null);
    	createdBy.setText(null);
    	categoryDescription.setText(null);
    	
    }

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getAllBrandName();
		getSupplierNameList();
		
	}

}
