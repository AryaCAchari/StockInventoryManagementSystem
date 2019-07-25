package application.controller.stock;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.databaseConnection.MysqlConnectionSingleton;
import application.model.StocksModel;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class StocksController implements Initializable {

	Stage primaryStage = new Stage();

	Connection connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	String time = null;
	// ObservableList for fetching all product details
	ObservableList<StocksModel> observableList = FXCollections.observableArrayList();
	ObservableList<StocksModel> observableListByProduct = FXCollections.observableArrayList();
	ObservableList<StocksModel> observableDropList = FXCollections.observableArrayList();

	@FXML
	private Label inventoryLabel;

	@FXML
	private JFXButton logoutButton;

	@FXML
	private Label currentDate;

	@FXML
	private TextField searchProducts;

	@FXML
	private JFXButton magnifyingGlass;

	@FXML
	private TableView<?> stocksView;

	@FXML
	private TableView<StocksModel> detailsOfStocks;

	@FXML
	private TableColumn<StocksModel, String> productName;

	@FXML
	private TableColumn<StocksModel, String> productModel;

	@FXML
	private TableColumn<StocksModel, String> productCode;

	@FXML
	private TableColumn<StocksModel, String> partNumber;

	@FXML
	private TableColumn<StocksModel, String> batchNumber;

	@FXML
	private TableColumn<StocksModel, Integer> quantity;

	@FXML
	private TableColumn<StocksModel, Double> purchaseRate;

	@FXML
	private TableColumn<StocksModel, Double> sellRateUnit;

	@FXML
	private TableColumn<StocksModel, String> invoiceNumber;

	@FXML
	private TableColumn<StocksModel, String> supplierName;

	@FXML
	private TableColumn<StocksModel, String> brandName;

	@FXML
	private TableColumn<StocksModel, String> categoryName;

	@FXML
	private JFXButton dashboardImage;

	@FXML
	private JFXButton salesImage;

	@FXML
	private JFXButton categoryImage;

	@FXML
	private JFXButton brandImage;

	@FXML
	private JFXButton supplierIamge;

	@FXML
	private JFXButton reportImage;

	@FXML
	private JFXButton save;

	@FXML
	private JFXButton editStocks;

	@FXML
	private JFXButton dropStock;

	@FXML
	private JFXButton goBack;

	/**
	 * @param event Drop selected data and update the table
	 */
	@FXML
	public void dropStocksDetails(ActionEvent event) {

		int id = 0, flag = 0;
		try {

			// select the each row from tableview
			observableDropList = detailsOfStocks.getSelectionModel().getSelectedItems();

			for (int i = 0; i < observableDropList.size(); i++) {

				id = observableDropList.get(i).getStkSlno();
			}

			String sql = "UPDATE stockdetails SET active_indicator=0 WHERE stk_slno='" + id + "'";
			preparedStatement = connection.prepareStatement(sql);
			flag = preparedStatement.executeUpdate();

			if (flag == 1) {
				System.out.println("Data deleted!");
				loadStockDetailsToTableViewHandler();
			} else {
				System.out.println("Data not found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param event edit stock details using transfer method from edit class
	 */
	@FXML
	public void editStocksDetials(ActionEvent event) {

		int stkSlno = 0, quantity = 0;
		Double purchaseRate = null, sellRateUnit = null;
		String invoiceNumber = null, batchNumber = null, productCode = null, prdctName = null, prdctModel = null,
				partNo = null, supName = null, brndName = null, ctgryName = null;

		try {

			observableList = detailsOfStocks.getSelectionModel().getSelectedItems();

			for (int i = 0; i < observableList.size(); i++) {
				stkSlno = observableList.get(i).getStkSlno();
				quantity = observableList.get(i).getQuantity();
				purchaseRate = observableList.get(i).getPurchaseRate();
				sellRateUnit = observableList.get(i).getSellRateUnit();
				invoiceNumber = observableList.get(i).getInvoiceNumber();
				batchNumber = observableList.get(i).getBatchNumber();
				productCode = observableList.get(i).getProductCode();
				prdctName = observableList.get(i).getProductName();
				prdctModel = observableList.get(i).getProductModel();
				partNo = observableList.get(i).getPartNumber();
				supName = observableList.get(i).getSupplierName();
				brndName = observableList.get(i).getBrandName();
				ctgryName = observableList.get(i).getCategoryName();

			}
			System.out.println(supName + " : " + brndName + " : " + ctgryName);

			StocksModel stocksModel = new StocksModel();
			stocksModel.setStkSlno(stkSlno);

			((Node) event.getSource()).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxmlpage/stock/EditStocks.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 768, 535);

			EditStocksController editStocksController = loader.getController();
			editStocksController.transferDate(stkSlno, prdctName, prdctModel, productCode, partNo, batchNumber,
					quantity, purchaseRate, sellRateUnit, invoiceNumber, supName, brndName, ctgryName);

			primaryStage.setScene(scene);
			primaryStage.setTitle("EDIT STOCK");
			primaryStage.isResizable();
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void goBackToPreviousPage(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/Dashbord.fxml"));
		Scene scene = new Scene(root, 1024, 768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("DASHBOARD");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		// primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	public void logoutFromTheSystem(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/StockInventryLogin.fxml"));
		Scene scene = new Scene(root, 375, 475);
		primaryStage.setScene(scene);
		primaryStage.getMinWidth();
		primaryStage.setMinWidth(375);
		primaryStage.getMinHeight();
		primaryStage.setMinHeight(475);
		primaryStage.isResizable();
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	@FXML
	void goToBrandSection(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/brand/Brands.fxml"));
		Scene scene = new Scene(root, 1024, 768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("BRANDS");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		// primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	void goToCategorySection(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/category/Category.fxml"));
		Scene scene = new Scene(root, 1024, 768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("CATEGORYS");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		// primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	void goToDashboard(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/Dashbord.fxml"));
		Scene scene = new Scene(root, 1024, 768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("DASHBOARD");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		// primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	void goToReportSection(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/ProuctAndService.fxml"));
		Scene scene = new Scene(root, 1024, 768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("REPORT");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		// primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	void goToSalesSection(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/sales/Sales.fxml"));
		Scene scene = new Scene(root, 1024, 768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("SALES");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		// primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	void goToSupplierSection(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/supplier/Supplier.fxml"));
		Scene scene = new Scene(root, 1024, 768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("SUPPLIERS");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		// primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	/**
	 * @param event refresh the table
	 */
	@FXML
	void onRefreshTableHandler(ActionEvent event) {
		loadStockDetailsToTableViewHandler();
	}

	/**
	 * @param event
	 * @throws IOException go to add screen
	 */
	@FXML
	public void saveStocksDetails(ActionEvent event) throws IOException {

		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/stock/AddStocks.fxml"));
		Scene scene = new Scene(root, 768, 535);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ADD STOCK");
		primaryStage.isResizable();
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * @param event search product details by stock property
	 */
	@FXML
	public void searchByProductsDetails(ActionEvent event) {		

		if (searchProducts.getText().isEmpty() || searchProducts.getText().trim().isEmpty()) {
			System.out.println("Load Table");
			loadStockDetailsToTableViewHandler();
		} else {
			observableList.clear();
			try {
				String sql = "SELECT * FROM stockdetails WHERE active_indicator=1 AND(stk_slno=? OR invoice_num=? OR batch_number=? OR product_code=? OR prdct_name=? OR prdct_model=? OR part_No=? OR sup_name=? OR brnd_name=? OR ctgry_name=?)";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, searchProducts.getText());
				preparedStatement.setString(2, searchProducts.getText());
				preparedStatement.setString(3, searchProducts.getText());
				preparedStatement.setString(4, searchProducts.getText());
				preparedStatement.setString(5, searchProducts.getText());
				preparedStatement.setString(6, searchProducts.getText());
				preparedStatement.setString(7, searchProducts.getText());
				preparedStatement.setString(8, searchProducts.getText());
				preparedStatement.setString(9, searchProducts.getText());
				preparedStatement.setString(10, searchProducts.getText());
				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					/*
					 * String commadityDate = resultSet.getString("purchase_date"); Date date = new
					 * SimpleDateFormat("dd/MM/yyyy").parse(commadityDate);
					 */
					observableList.add(new StocksModel(resultSet.getInt("stk_slno"), resultSet.getString("prdct_name"),
							resultSet.getString("prdct_model"), resultSet.getString("product_code"),
							resultSet.getString("part_No"), resultSet.getString("batch_number"),
							resultSet.getInt("quanty"), resultSet.getDouble("purchase_rate"),
							resultSet.getDouble("sell_rate_unit"), resultSet.getString("invoice_num"),
							resultSet.getString("sup_name"), resultSet.getString("brnd_name"),
							resultSet.getString("ctgry_name")));

				}

				productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
				productModel.setCellValueFactory(new PropertyValueFactory<>("productModel"));
				productCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
				partNumber.setCellValueFactory(new PropertyValueFactory<>("partNumber"));
				batchNumber.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
				quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
				purchaseRate.setCellValueFactory(new PropertyValueFactory<>("purchaseRate"));
				sellRateUnit.setCellValueFactory(new PropertyValueFactory<>("sellRateUnit"));
				invoiceNumber.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
				supplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
				brandName.setCellValueFactory(new PropertyValueFactory<>("brandName"));
				categoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
				detailsOfStocks.setItems(observableList);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * load stock details
	 */
	public void loadStockDetailsToTableViewHandler() {

		observableList.clear();
		try {

			String sql = "SELECT * FROM stockdetails WHERE active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				observableList.add(new StocksModel(resultSet.getInt("stk_slno"), resultSet.getString("prdct_name"),
						resultSet.getString("prdct_model"), resultSet.getString("product_code"),
						resultSet.getString("part_No"), resultSet.getString("batch_number"), resultSet.getInt("quanty"),
						resultSet.getDouble("purchase_rate"), resultSet.getDouble("sell_rate_unit"),
						resultSet.getString("invoice_num"), resultSet.getString("sup_name"),
						resultSet.getString("brnd_name"), resultSet.getString("ctgry_name")));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
		productModel.setCellValueFactory(new PropertyValueFactory<>("productModel"));
		productCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
		partNumber.setCellValueFactory(new PropertyValueFactory<>("partNumber"));
		batchNumber.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		purchaseRate.setCellValueFactory(new PropertyValueFactory<>("purchaseRate"));
		sellRateUnit.setCellValueFactory(new PropertyValueFactory<>("sellRateUnit"));
		invoiceNumber.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
		supplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
		brandName.setCellValueFactory(new PropertyValueFactory<>("brandName"));
		categoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
		detailsOfStocks.setItems(observableList);

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
		observableList = FXCollections.observableArrayList();
		loadStockDetailsToTableViewHandler();
		dynamicClock();
	}

}
