package application.controller.stock;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
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

public class EditStocksController implements Initializable {

	Stage primaryStage = new Stage();

	Connection connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	ObservableList<String> observableSupplierList = FXCollections.observableArrayList();
	ObservableList<String> observableBrandList = FXCollections.observableArrayList();
	ObservableList<String> observableCategoryList = FXCollections.observableArrayList();

	int id = 0;
	String time = null;

	@FXML
	private TextField invoicenumber;

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
	private ComboBox<String> brandName;

	@FXML
	private TextField productCode;

	@FXML
	private TextField productModel;

	@FXML
	private TextField purchaseRate;

	@FXML
	private TextField quantity;

	@FXML
	private TextField sellingPricePerUnit;

	@FXML
	private ComboBox<String> supplierName;

	@FXML
	private ComboBox<String> categoryName;
	
	@FXML
    private Button goBack;
	
	String suppliersName=null, brandsName = null, categoriesName = null;

	/**
	 * @param stkSlno
	 * @param prdctName
	 * @param prdctModel
	 * @param productCode
	 * @param partNo
	 * @param batchNumber
	 * @param quantity
	 * @param purchaseRate
	 * @param sellRateUnit
	 * @param invoiceNumber
	 * @param supName
	 * @param brndName
	 * @param ctgryName
	 * read data from controller and passed to save process
	 */
	public void transferDate(int stkSlno, String prdctName, String prdctModel, String productCode, String partNo,
			String batchNumber, int quantity, Double purchaseRate, Double sellRateUnit, String invoiceNumber,
			String supName, String brndName, String ctgryName) {
		
		this.id = stkSlno;
		productName.setText(prdctName);
		productModel.setText(prdctModel);
		this.productCode.setText(productCode);
		partNumber.setText(partNo);
		this.batchNumber.setText(batchNumber);

		this.quantity.setText(String.valueOf(quantity));
		this.purchaseRate.setText(String.valueOf(purchaseRate));
		sellingPricePerUnit.setText(String.valueOf(sellRateUnit));

		invoicenumber.setText(invoiceNumber);
		suppliersName=supName;
		categoriesName=ctgryName;
		brandsName=brndName;
		System.out.println(suppliersName+" : "+categoriesName+" : "+brandsName);
		/*brandName.setEditable(true);
		categoryName.setEditable(true);
		supplierName.setEditable(true);*/

		try {

			String sql = "SELECT purchase_date FROM stockdetails WHERE stk_slno = '" + this.id + "' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				Date purchaseDate = resultSet.getDate("purchase_date");
				Instant instant = Instant.ofEpochMilli(purchaseDate.getTime());
				LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
				LocalDate value = localDateTime.toLocalDate();
				this.purchaseDate.setValue(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		fetchBrandNameHandler(brandsName);
		fetchCategoryNameHandler(categoriesName);
		fetchSupplierNameHandler(suppliersName);

	}

	/**
	 * @param event
	 * @throws IOException
	 * cancel the edit process
	 */
	@FXML
	public void cancelStockDetailsHandler(ActionEvent event) throws IOException {

		invoicenumber.setText(null);
		batchNumber.setText(null);
		productCode.setText(null);
		productName.setText(null);
		productModel.setText(null);
		partNumber.setText(null);
		quantity.setText(null);
		purchaseDate.setValue(null);
		purchaseRate.setText(null);
		sellingPricePerUnit.setText(null);
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
	 * edit the stock details
	 */
	@FXML
	public void updateStockDetailsHandler(ActionEvent event) throws IOException {

		try {

			String supplier = onSupplierNameSelectionHandler(event);
			String brand = onBrandNameSelectionHandler(event);
			String category = onCategoryNameSelectionHandler(event);

			Double purchaseAmount = Double.parseDouble(purchaseRate.getText());
			Double sellingAmount = Double.parseDouble(sellingPricePerUnit.getText());
			int quantityOfComadity = Integer.parseInt(quantity.getText());
			
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
			
			java.sql.Date date = java.sql.Date.valueOf(subDateValue);
			System.out.println("In sql date format:  "+date);

			String sql = "UPDATE stockdetails SET invoice_num=?, batch_number=?, product_code=?, prdct_name=?, prdct_model=?, part_No=?, quanty=?, purchase_date=?, purchase_rate=?, sell_rate_unit=?, sup_name=?, brnd_name=?, ctgry_name=?, updated_at=? WHERE stk_slno='"+this.id+"' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, invoicenumber.getText());
			preparedStatement.setString(2, batchNumber.getText());
			preparedStatement.setString(3, productCode.getText());
			preparedStatement.setString(4, productName.getText());
			preparedStatement.setString(5, productModel.getText());
			preparedStatement.setString(6, partNumber.getText());
			preparedStatement.setInt(7, quantityOfComadity);
			preparedStatement.setDate(8, date);
			preparedStatement.setDouble(9, purchaseAmount);
			preparedStatement.setDouble(10, sellingAmount);
			preparedStatement.setString(11, supplier);
			preparedStatement.setString(12, brand);
			preparedStatement.setString(13, category);
			preparedStatement.setString(14, Utilities.currentDateAndTime());
			
			int flag = preparedStatement.executeUpdate();

			if (flag == 1) {
				System.out.println("Details updated");
			} else {
				System.out.println("Data saving error!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		invoicenumber.setText(null);
		batchNumber.setText(null);
		productCode.setText(null);
		productName.setText(null);
		productModel.setText(null);
		partNumber.setText(null);
		quantity.setText(null);
		purchaseDate.setValue(null);
		purchaseRate.setText(null);
		sellingPricePerUnit.setText(null);
		supplierName.setValue(null);
		brandName.setValue(null);
		categoryName.setValue(null);		
	}

	/**
	 * load supplier name to combo box
	 */
	public void fetchSupplierNameHandler(String supplier) {

		try {

			String sql = "SELECT sup_name FROM supplierdetails WHERE active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				SupplierModel supplierModel = new SupplierModel();
				supplierModel.setSupplierName(resultSet.getString("sup_name"));
				observableSupplierList.add(supplierModel.getSupplierName());
				if(supplier.equals(resultSet.getString("sup_name"))) {
					supplierName.setValue(supplier);
					supplierName.setItems(observableSupplierList);
				}else {
					supplierName.setItems(observableSupplierList);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *load brand name to combo box 
	 */
	public void fetchBrandNameHandler(String brand) {

		try {

			String sql = "SELECT brand_name FROM branddetails WHERE active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				BrandsModel brandsModel = new BrandsModel();
				brandsModel.setBrand_name(resultSet.getString("brand_name"));
				observableBrandList.add(resultSet.getString("brand_name"));
				if(brand.equals(resultSet.getString("brand_name"))) {
					brandName.setValue(brand);
					brandName.setItems(observableBrandList);
				}else {
					brandName.setItems(observableBrandList);
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fetchCategoryNameHandler(String category) {

		try {

			String sql = "SELECT category_name FROM categorydetails WHERE active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				CategoryModel categoryModel = new CategoryModel();
				categoryModel.setCategoryName(resultSet.getString("category_name"));
				observableCategoryList.add(categoryModel.getCategoryName());
				if(category.equals(resultSet.getString("category_name"))) {
					categoryName.setValue(category);
					categoryName.setItems(observableCategoryList);
				}else {
					categoryName.setItems(observableCategoryList);
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param event
	 * @return
	 * brand name from combo box
	 */
	@FXML
	public String onBrandNameSelectionHandler(ActionEvent event) {

		String onSelectBrand = brandName.getValue();
		return onSelectBrand;
	}

	/**
	 * @param event
	 * @return
	 * category from combo box
	 */
	@FXML
	public String onCategoryNameSelectionHandler(ActionEvent event) {

		String onSelectCategory = categoryName.getValue();
		return onSelectCategory;
	}

	/**
	 * @param event
	 * @return supplier from combo box
	 */
	@FXML
	public String onSupplierNameSelectionHandler(ActionEvent event) {

		String onSelectSupplier = supplierName.getValue();
		return onSelectSupplier;
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

	}

}
