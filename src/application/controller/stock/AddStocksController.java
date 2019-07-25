package application.controller.stock;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import application.databaseConnection.MysqlConnectionSingleton;
import application.model.BrandsModel;
import application.model.CategoryModel;
import application.model.SupplierModel;
import application.utilities.Utilities;
import javafx.application.Platform;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddStocksController implements Initializable {
	
	Stage primaryStage = new Stage();

	Connection connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	ObservableList<String> observableSupplierList = FXCollections.observableArrayList();
	ObservableList<String> observableBrandList = FXCollections.observableArrayList();
	ObservableList<String> observableCategoryList = FXCollections.observableArrayList();
	
	String time = null;

	@FXML
	private TextField invoiceNumber;

	@FXML
	private Label currentDate;

	@FXML
	private TextField batchNumber;

	@FXML
	private TextField productName;

	@FXML
	private TextField partNumber;

	@FXML
	private DatePicker purchaseDate;

	@FXML
	private TextField princePerUnit;

	@FXML
	private ComboBox<String> brandName;

	@FXML
	private TextField productCode;

	@FXML
	private TextField productModel;

	@FXML
	private TextField quantity;

	@FXML
	private TextField purchaseRate;

	@FXML
	private ComboBox<String> supplierName;

	@FXML
	private ComboBox<String> categoryName;

	@FXML
	private Button save;

	@FXML
	private Button cancel;
	
	 @FXML
	 private Button goBack;

	/**
	 * @param event
	 * @throws IOException
	 * when cancel the add stock details
	 */
	@FXML
	public void onCancelAddStockDetailsHandler(ActionEvent event) throws IOException {
		
		invoiceNumber.setText(null);
		batchNumber.setText(null);
		productCode.setText(null);
		productName.setText(null);
		productModel.setText(null);
		partNumber.setText(null);
		quantity.setText(null);
		purchaseDate.setValue(null);
		purchaseRate.setText(null);
		princePerUnit.setText(null);
		supplierName.setValue(null);
		brandName.setValue(null);
		categoryName.setValue(null);
	}
	
	@FXML
    public void goBackToPreviousPage(ActionEvent event) throws IOException {
		
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/stock/Stocks.fxml"));
		Scene scene = new Scene(root, 1024,768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("STOCK");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		//primaryStage.setFullScreen(true);
		primaryStage.show();
    }

	/**
	 * @param event
	 * @throws IOException
	 * @throws ParseException
	 * Add stock details
	 */
	@FXML
	public void onSaveStockDetailsHandler(ActionEvent event) throws IOException, ParseException {

		String supplier = onSelectSupplierNameHandler(event);
		String brand = onSelectBrandNameHandler(event);
		String category = onSelectCategoryNameHandler(event);
		String currentTime = Utilities.currentDateAndTime();
		
		String dateValue = purchaseDate.getEditor().getText().toString().replace('/', '-');
		System.out.println(dateValue +" " +dateValue.length());
		String subDateValue = null;		
		if(dateValue.length() == 8) {
			subDateValue = dateValue.substring(4, 8)+"-"+dateValue.substring(0, 3);
			System.out.println(subDateValue);			
		}else if(dateValue.length()==9) {
			subDateValue = dateValue.substring(5, 9)+"-"+dateValue.substring(0, 4);
			System.out.println(subDateValue);
		}else {
			subDateValue = dateValue.substring(6, 10)+"-"+dateValue.substring(0, 5);
			System.out.println(subDateValue);
		}
		
		Date date = Date.valueOf(subDateValue);
		System.out.println("In sql date format:  "+date);
		
		boolean flag = true;
		try {

			String sql = "INSERT INTO stockdetails(invoice_num, batch_number, product_code, prdct_name, prdct_model, part_No, quanty, purchase_date, purchase_rate, sell_rate_unit, sup_name, brnd_name, ctgry_name , created_at, updated_at, active_indicator)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, invoiceNumber.getText());
			preparedStatement.setString(2, batchNumber.getText());
			preparedStatement.setString(3, productCode.getText());
			preparedStatement.setString(4, productName.getText());
			preparedStatement.setString(5, productModel.getText());
			preparedStatement.setString(6, partNumber.getText());
			preparedStatement.setString(7, quantity.getText());
			preparedStatement.setDate(8, date);
			preparedStatement.setString(9, purchaseRate.getText());
			preparedStatement.setString(10, princePerUnit.getText());
			preparedStatement.setString(11, supplier);
			preparedStatement.setString(12, brand);
			preparedStatement.setString(13, category);
			preparedStatement.setString(14, currentTime);
			preparedStatement.setString(15, currentTime);
			preparedStatement.setInt(16, 1);

			flag = preparedStatement.execute();

			if (flag == false) {
				System.out.println("Stock added");
			} else {
				System.out.println("Can not add stock details");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		invoiceNumber.setText(null);
		batchNumber.setText(null);
		productCode.setText(null);
		productName.setText(null);
		productModel.setText(null);
		partNumber.setText(null);
		quantity.setText(null);
		purchaseDate.setValue(null);
		purchaseRate.setText(null);
		princePerUnit.setText(null);
		supplierName.setValue(null);
		brandName.setValue(null);
		categoryName.setValue(null);
	}

	/**
	 * @param event
	 * @return
	 * read selected brand name from combo box
	 */
	@FXML
	public String onSelectBrandNameHandler(ActionEvent event) {

		String selectedBrandName = brandName.getValue();
		return selectedBrandName;
	}

	/**
	 * fetch all the brand name to combo box
	 */
	public void fetchBrandNameHandler() {

		try {

			String sql = "SELECT brand_name FROM branddetails WHERE active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				BrandsModel brandsModel = new BrandsModel();
				brandsModel.setBrand_name(resultSet.getString("brand_name"));
				observableBrandList.add(resultSet.getString("brand_name"));
				brandName.setItems(observableBrandList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load all the category to combo box
	 */
	public void fetchCategoryNameHandler() {

		try {

			String sql = "SELECT category_name FROM categorydetails WHERE active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				CategoryModel categoryModel = new CategoryModel();
				categoryModel.setCategoryName(resultSet.getString("category_name"));
				observableCategoryList.add(categoryModel.getCategoryName());
				categoryName.setItems(observableCategoryList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param event
	 * @return
	 * select category name from combo box  
	 */
	@FXML
	public String onSelectCategoryNameHandler(ActionEvent event) {

		String onSelectCategory = categoryName.getValue();
		return onSelectCategory;
	}

	/**
	 * @param event
	 * @return
	 * read data from supplier category combo box 
	 */
	@FXML
	public String onSelectSupplierNameHandler(ActionEvent event) {

		String selectedSupplierName = supplierName.getValue();
		return selectedSupplierName;
	}

	/**
	 * load all supplier name to combo box
	 */
	public void fetchSupplierNameHandler() {

		try {

			String sql = "SELECT sup_name FROM supplierdetails WHERE active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				SupplierModel supplierModel = new SupplierModel();
				supplierModel.setSupplierName(resultSet.getString("sup_name"));
				observableSupplierList.add(supplierModel.getSupplierName());
				supplierName.setItems(observableSupplierList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dynamicClock() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Calendar calendar = new GregorianCalendar();
							int day = calendar.get(Calendar.DAY_OF_MONTH);
							int month = calendar.get(Calendar.MONTH);
							int year = calendar.get(Calendar.YEAR);

							int seconds = calendar.get(Calendar.SECOND);
							int minutes = calendar.get(Calendar.MINUTE);
							int hours = calendar.get(Calendar.HOUR);
							int amOrPm = calendar.get(Calendar.AM_PM);
							String timeMeridian = null;
							if (amOrPm == 1) {
								timeMeridian = "PM";
							} else {
								timeMeridian = "AM";
							}

							time = year + ":" + month + ":" + day + ": " + hours + ":" + minutes + ":" + seconds + ":"
									+ timeMeridian;
							currentDate.setText(time);

						}
					});
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		dynamicClock();
		fetchSupplierNameHandler();
		fetchBrandNameHandler();
		fetchCategoryNameHandler();
	}

}
