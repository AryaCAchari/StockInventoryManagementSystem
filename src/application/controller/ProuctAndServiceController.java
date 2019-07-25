package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.databaseConnection.MysqlConnectionSingleton;
import application.model.ProuctAndServiceModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProuctAndServiceController implements Initializable {

	Stage primaryStage = new Stage();
	String time = null;
	Connection connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	int stockid = 0;

	@FXML
	private Label iconLabel;

	@FXML
	private JFXButton logoutButton;

	@FXML
	private Label currentTimeLabel;

	@FXML
	private JFXButton supplierNavigator;

	@FXML
	private JFXButton brandButton;

	@FXML
	private JFXButton categoryNavigory;

	@FXML
	private JFXButton stockNavigator;

	@FXML
	private TableView<ProuctAndServiceModel> brandTable;

	@FXML
	private TableColumn<ProuctAndServiceModel, String> brandName;

	@FXML
	private TableColumn<ProuctAndServiceModel, Integer> brandCount;

	@FXML
	private TableColumn<ProuctAndServiceModel, Date> brandDate;

	@FXML
	private TableView<ProuctAndServiceModel> ctaegoryTable;

	@FXML
	private TableColumn<ProuctAndServiceModel, String> categoryName;

	@FXML
	private TableColumn<ProuctAndServiceModel, Integer> categoryCount;

	@FXML
	private TableColumn<ProuctAndServiceModel, Date> categotySoldDate;

	@FXML
	private TableView<ProuctAndServiceModel> productTable;

	@FXML
	private TableColumn<ProuctAndServiceModel, String> modelName;

	@FXML
	private TableColumn<ProuctAndServiceModel, String> modelBrand;

	@FXML
	private TableColumn<ProuctAndServiceModel, Integer> modelCount;

	@FXML
	private TableColumn<ProuctAndServiceModel, Date> modelDate;

	@FXML
	private JFXButton bckbutton;

	@FXML
	private Label inventoryLabel;

	@FXML
	private JFXButton logoutButton1;

	@FXML
	private Label currentDate;

	@FXML
	private JFXButton dashboardImage;

	@FXML
	private JFXButton stockImage;

	@FXML
	private JFXButton salesImage;

	@FXML
	private JFXButton supplierImage;

	@FXML
	private JFXButton brandImage;

	@FXML
	private JFXButton categoryImage;

	private ObservableList<ProuctAndServiceModel> brandObservableList = FXCollections.observableArrayList();
	private ObservableList<ProuctAndServiceModel> categoryObservableList = FXCollections.observableArrayList();
	private ObservableList<ProuctAndServiceModel> productObservableList = FXCollections.observableArrayList();
	
	@FXML
	public void goTodashboardSection(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/Dashbord.fxml"));
		Scene scene = new Scene(root, 1024,768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("DASHBOARD");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		//primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	public void gotoBrandButton(ActionEvent event) throws IOException {

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
	public void gotoCategorySection(ActionEvent event) throws IOException {

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
	public void gotoPreviousSection(ActionEvent event) throws IOException {

		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/Dashbord.fxml"));
		Scene scene = new Scene(root, 1024,768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("DASHBOARD");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		//primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	public void gotoStockSection(ActionEvent event) throws IOException {

		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/stock/Stocks.fxml"));
		Scene scene = new Scene(root, 1024,768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("STOCKS");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		//primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	public void gotoSupplierSection(ActionEvent event) throws IOException {

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
	public void goToSalesSection(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/sales/Sales.fxml"));
		Scene scene = new Scene(root, 1024,768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("SALES");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		//primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	void logoutFromTheSystem(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/StockInventryLogin.fxml"));
		Scene scene = new Scene(root,375,475);
		primaryStage.setScene(scene);
		primaryStage.getMinWidth();
		primaryStage.setMinWidth(375);
		primaryStage.getMinHeight();
		primaryStage.setMinHeight(475);
		primaryStage.isResizable();
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public void reportOfPurchase() {
		try {
			String sql = "select stockdetails.prdct_name, stockdetails.prdct_model, stockdetails.brnd_name, stockdetails.ctgry_name, sales.quantity, sales.stock_id, sales.updated_at\r\n"
					+ "from stockdetails\r\n" + "inner join sales on sales.id = stockdetails.stk_slno  \r\n"
					+ "where stockdetails.active_indicator=1\r\n" + "order by sales.quantity desc limit 5;";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getString("prdct_name") + " : " + resultSet.getString("prdct_model") + " : " + resultSet.getString("brnd_name") + " : " + resultSet.getString("ctgry_name") + " : " + resultSet.getInt("quantity") + " : "
						+ resultSet.getString("stock_id")+ " : " + resultSet.getDate("updated_at"));
				brandObservableList.addAll(new ProuctAndServiceModel(resultSet.getString("prdct_model"), resultSet.getString("brnd_name"), resultSet.getString("ctgry_name"), resultSet.getInt("quantity"), resultSet.getDate("updated_at")));
				categoryObservableList.addAll(new ProuctAndServiceModel(resultSet.getString("prdct_model"), resultSet.getString("brnd_name"), resultSet.getString("ctgry_name"), resultSet.getInt("quantity"), resultSet.getDate("updated_at")));
				productObservableList.addAll(new ProuctAndServiceModel(resultSet.getString("prdct_model"), resultSet.getString("brnd_name"), resultSet.getString("ctgry_name"), resultSet.getInt("quantity"), resultSet.getDate("updated_at")));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		brandName.setCellValueFactory(new PropertyValueFactory<>("brandName"));
		brandCount.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		brandDate.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
		brandTable.setItems(brandObservableList);
		
		categoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
		categoryCount.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		categotySoldDate.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
		ctaegoryTable.setItems(categoryObservableList);
		
		modelName.setCellValueFactory(new PropertyValueFactory<>("productModel"));
		modelBrand.setCellValueFactory(new PropertyValueFactory<>("brandName"));
		modelCount.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		modelDate.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
		productTable.setItems(productObservableList);
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
	public void initialize(URL location, ResourceBundle resources) {
		dynamicClock();
		reportOfPurchase();
	}
}
