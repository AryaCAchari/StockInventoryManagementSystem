package application.controller.supplier;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.databaseConnection.MysqlConnectionSingleton;
import application.model.SupplierModel;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SupplierController implements Initializable {

	Stage primaryStage = new Stage();
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	boolean flag = false;
	String time = null;

	@FXML
	private Label inventoryLabel;

	@FXML
	private JFXButton logoutButton;

	@FXML
	private Label currentDate;

	@FXML
	private MenuItem refrehTable;

	@FXML
	private TextField search;

	@FXML
	private JFXButton magnifyingGlass;

	@FXML
	private TableView<SupplierModel> supplierView;

	@FXML
	private TableColumn<SupplierModel, String> supplierName;

	@FXML
	private TableView<SupplierModel> detailsOfSuppliers;

	@FXML
	private TableColumn<SupplierModel, Integer> slNum;

	@FXML
	private TableColumn<SupplierModel, String> name;

	@FXML
	private TableColumn<SupplierModel, String> mobileNumber;

	@FXML
	private TableColumn<SupplierModel, String> landlineNumber;

	@FXML
	private TableColumn<SupplierModel, String> address;

	@FXML
	private JFXButton add;

	@FXML
	private JFXButton editSupplier;

	@FXML
	private JFXButton dropSupplier;

	@FXML
	private JFXButton goBack;

	ObservableList<SupplierModel> objectList = FXCollections.observableArrayList();
	ObservableList<SupplierModel> objectNameList = FXCollections.observableArrayList();
	ObservableList<SupplierModel> objectRawList = FXCollections.observableArrayList();

	@FXML
	public void addSuppliersDetails(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/supplier/AddSupplier.fxml"));
		Scene scene = new Scene(root, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ADD SUPPLIER");
		primaryStage.isResizable();
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * @param event drop and refresh the table
	 */
	@FXML
	public void dropSuppliersDetails(ActionEvent event) {

		try {

			objectList = detailsOfSuppliers.getSelectionModel().getSelectedItems();
			int id = 0;
			for (int i = 0; i < objectList.size(); i++) {
				id = objectList.get(i).getSlNum();
			}
			System.out.println("ID: " + id);

			connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
			String sql = "UPDATE supplierdetails SET active_indicator=0 WHERE sl_num = '" + id + "' ";
			statement = connection.createStatement();
			int flag = statement.executeUpdate(sql);

			if (flag == 1) {
				System.out.println("FLAG: " + flag);
			} else {
				System.out.println("FLAG not updated: " + flag);
			}

			fetchSupplierDetails();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param event
	 * @throws IOException edit supplier details, by passing data through transfer
	 *                     method from Edit supplier
	 */
	@FXML
	public void editSuppliersDetials(ActionEvent event) throws IOException {

		try {

			objectRawList = detailsOfSuppliers.getSelectionModel().getSelectedItems();
			System.out.println(objectRawList.get(0).getSlNum());

			int id = 0;
			String name = null, phoneNumber = null, landlineNumber = null, address = "";

			for (int i = 0; i < objectRawList.size(); i++) {
				id = objectRawList.get(i).getSlNum();
				name = objectRawList.get(i).getName();
				phoneNumber = objectRawList.get(i).getMobileNumber();
				landlineNumber = objectRawList.get(i).getLandlineNumber();
				address = objectRawList.get(i).getAddress();
			}

			SupplierModel supplier = new SupplierModel();
			supplier.setId(id);

			((Node) event.getSource()).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/application/fxmlpage/supplier/EditSupplierDetails.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 600, 400);

			EditSupplierDetailsController editSupplier = loader.getController();
			editSupplier.transferDetails(id, name, phoneNumber, landlineNumber, address);

			primaryStage.setScene(scene);
			primaryStage.setTitle("EDIT SUPPLIER");
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
	void goToBranadSection(ActionEvent event) throws IOException {
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
		primaryStage.setTitle("CATEGORYs");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		// primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	void goToDashboardSection(ActionEvent event) throws IOException {
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
	void goToStockSection(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/stock/Stocks.fxml"));
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
	public void handleRefershTableView(ActionEvent event) {
		fetchSupplierDetails();
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
	public void searchByAnything(ActionEvent event) {
		
		if(search.getText().isEmpty() || search.getText().trim().isEmpty()) {
			fetchSupplierDetails();
		}else {
			objectRawList.clear();
			try {
				connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
				statement = connection.createStatement();
				String sql = "SELECT * FROM supplierdetails WHERE active_indicator=1 AND(sl_num = '" + search.getText()
						+ "' OR sup_name='" + search.getText() + "' OR sup_mob='" + search.getText()
						+ "' OR  sup_landnum='" + search.getText() + "') ";
				resultSet = statement.executeQuery(sql);
				while (resultSet.next()) {

					objectRawList.add(new SupplierModel(resultSet.getInt("sl_num"), resultSet.getString("sup_name"),
							resultSet.getString("sup_mob"), resultSet.getString("sup_landnum"),
							resultSet.getString("sup_addr")));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			slNum.setCellValueFactory(new PropertyValueFactory<>("slNum"));
			name.setCellValueFactory(new PropertyValueFactory<>("name"));
			mobileNumber.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
			landlineNumber.setCellValueFactory(new PropertyValueFactory<>("landlineNumber"));
			address.setCellValueFactory(new PropertyValueFactory<>("address"));
			detailsOfSuppliers.setItems(objectRawList);
			search.setText(null);
		}

	}

	/**
	 * fetch supplier details
	 */
	public void fetchSupplierDetails() {

		objectRawList.clear();
		try {

			connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
			statement = connection.createStatement();
			String query = "SELECT * FROM supplierdetails WHERE active_indicator=1";
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				objectRawList.add(new SupplierModel(resultSet.getInt("sl_num"), resultSet.getString("sup_name"),
						resultSet.getString("sup_mob"), resultSet.getString("sup_landnum"),
						resultSet.getString("sup_addr")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		slNum.setCellValueFactory(new PropertyValueFactory<>("slNum"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		mobileNumber.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
		landlineNumber.setCellValueFactory(new PropertyValueFactory<>("landlineNumber"));
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		detailsOfSuppliers.setItems(objectRawList);

	}

	/*
	 * public void viewOnlyBySupplierName() {
	 * 
	 * try {
	 * 
	 * connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
	 * statement = connection.createStatement(); String query =
	 * "SELECT sup_name FROM supplierdetails"; resultSet =
	 * statement.executeQuery(query);
	 * 
	 * while (resultSet.next()) { objectNameList.add(new
	 * SupplierModel(resultSet.getString("sup_name"))); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * supplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
	 * supplierView.setItems(objectRawList); }
	 */

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

		fetchSupplierDetails();
//		viewOnlyBySupplierName();
//		objectList = FXCollections.observableArrayList();
		objectRawList = FXCollections.observableArrayList();
//		getIdFromselectedRaw();
		dynamicClock();

	}
}
