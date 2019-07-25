package application.controller;

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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DashbordController implements Initializable {

	Stage primaryStage = new Stage();
	String time = null;
	Connection connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	ObservableList<StocksModel> observableList = FXCollections.observableArrayList();
	Integer count = 0;
	Integer total = 0;

	@FXML
	private Label logoSpace;

	@FXML
	private JFXButton logoutButton;

	@FXML
	private Label curretenDate;

	@FXML
	private Label dashImgae;

	@FXML
	private JFXButton dashboardNavigator;

	@FXML
	private Label stockImage;

	@FXML
	private JFXButton stocksNavigator;

	@FXML
	private Label brandImage;

	@FXML
	private JFXButton brandNavigator;

	@FXML
	private Label categryImage;

	@FXML
	private JFXButton categoryNavigator;

	@FXML
	private Label suppImage;

	@FXML
	private JFXButton supplierNavigator;

	@FXML
	private Label salesImage;

	@FXML
	private JFXButton billingNavigator;

	@FXML
	private Label reportImage;

	@FXML
	private JFXButton reportNavigator;

	@FXML
	private JFXButton productSerrvice;

	@FXML
	private Label cpuImage;

	@FXML
	private Label countOne;

	@FXML
	private Label monitorImage;

	@FXML
	private Label countTwo;

	@FXML
	private Label keyImage;

	@FXML
	private Label countThree;

	@FXML
	private Label mouseImage;

	@FXML
	private Label countfour;

	@FXML
	private Label dvdImage;

	@FXML
	private Label countFive;

	@FXML
	private Label cdImage;

	@FXML
	private Label countSix;

	@FXML
	private Label hddImage;

	@FXML
	private Label countSeven;

	@FXML
	private Label usbImage;

	@FXML
	private Label countEight;

	@FXML
	private Label hdmiImage;

	@FXML
	private Label countNine;

	@FXML
	private Label dataImage;

	@FXML
	private Label countTen;

	@FXML
	private Label cableImage;

	@FXML
	private Label countEleven;

	@FXML
	private Label headImage;

	@FXML
	private Label countTwelv;

	@FXML
	private Label powerImage;

	@FXML
	private Label countThirteen;

	@FXML
	private Label printerImage;

	@FXML
	private Label countFourteen;

	@FXML
	private Label scannerImage;

	@FXML
	private Label countFifteen;

	@FXML
	private TableView<StocksModel> tableView;

	@FXML
	private TableColumn<StocksModel, String> productName;

	@FXML
	private TableColumn<StocksModel, String> modelName;

	@FXML
	private TableColumn<StocksModel, String> brandName;

	@FXML
	private TableColumn<StocksModel, Integer> qunatity;

	@FXML
	private TableColumn<StocksModel, Double> price;

	@FXML
	private MenuItem refreshPage;

	@FXML
	public void gotoBrandSection(ActionEvent event) throws IOException {

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
	public void gotoProductAndServices(ActionEvent event) throws IOException {

		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/ProuctAndService.fxml"));
		Scene scene = new Scene(root, 1024,768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("REPORT");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		//primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	public void gotoReportSection(ActionEvent event) throws IOException {

		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/Reports.fxml"));
		Scene scene = new Scene(root, 1024,768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("REPORT");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		//primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	public void gotoSalesSection(ActionEvent event) throws IOException {

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
	public void goToCategorySection(ActionEvent event) throws IOException {
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
	public void goToSupplierSection(ActionEvent event) throws IOException {
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
	public void logoutfromSystem(ActionEvent event) throws IOException {
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

	@FXML
	void onRefershTableAndPage(ActionEvent event) {

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

				observableList.addAll(new StocksModel(resultSet.getInt("stk_slno"), resultSet.getString("prdct_name"),
						resultSet.getString("prdct_model"), resultSet.getString("product_code"),
						resultSet.getString("part_No"), resultSet.getString("batch_number"), resultSet.getInt("quanty"),
						resultSet.getDouble("purchase_rate"), resultSet.getDouble("sell_rate_unit"),
						resultSet.getString("invoice_num"), resultSet.getString("sup_name"),
						resultSet.getString("brnd_name"), resultSet.getString("ctgry_name")));
				
				productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
				modelName.setCellValueFactory(new PropertyValueFactory<>("productModel"));
				brandName.setCellValueFactory(new PropertyValueFactory<>("brandName"));
				qunatity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
				this.price.setCellValueFactory(new PropertyValueFactory<>("sellRateUnit"));		
				tableView.setItems(observableList);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

	/**
	 * current time displaying
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
							curretenDate.setText(time);

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

	public void categoryList() {
		try {
			String sql = "SELECT * FROM categorydetails WHERE active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet != null) {
				resultSet.getString("category_name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*public void takeCountOfSpecificCategory() {

		try {

			String sql = "SELECT COUNT(*) FROM stockdetails WHERE ctgry_name='Thin and light laptops' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt(1);
				//System.out.println("Count of Thin and light laptops: " + count);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
//	Thin and light laptops
	public void totalCountOne() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='CPU' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
//				//System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
//				//System.out.println(data.intValue());
				sum+=data.intValue();
				countOne.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalCountTwo() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='Monitor' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				////System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
				//System.out.println(data.intValue());
				sum+=data.intValue();
				countTwo.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalCountThree() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='Keyboard' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
				//System.out.println(data.intValue());
				sum+=data.intValue();
				countThree.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalCountFour() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='Mouse' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
				//System.out.println(data.intValue());
				sum+=data.intValue();
				countfour.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalCountFive() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='DVD' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
				//System.out.println(data.intValue());
				sum+=data.intValue();
				countFive.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalCountSix() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='CD' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
				//System.out.println(data.intValue());
				sum+=data.intValue();
				countSix.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalCountSeven() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='HDD' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
				//System.out.println(data.intValue());
				sum+=data.intValue();
				countSeven.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalCountEight() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='USB' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
				//System.out.println(data.intValue());
				sum+=data.intValue();
				countEight.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalCountNine() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='HDMI Cable' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
				//System.out.println(data.intValue());
				sum+=data.intValue();
				countNine.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalCountTen() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='Data Cables' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
				//System.out.println(data.intValue());
				sum+=data.intValue();
				countTen.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalCountEleven() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='Cables' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
				//System.out.println(data.intValue());
				sum+=data.intValue();
				countEleven.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalCountTwelv() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='Head Phone' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
				//System.out.println(data.intValue());
				sum+=data.intValue();
				countTwelv.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalCountThirteen() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='Power Bank' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
				//System.out.println(data.intValue());
				sum+=data.intValue();
				countThirteen.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalCountFourteen() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='Printer' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
				//System.out.println(data.intValue());
				sum+=data.intValue();
				countFourteen.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalCountFifteen() {
		Integer sum = 0;
		try {			
			String sql = "SELECT quanty FROM stockdetails WHERE ctgry_name='Scanner' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//System.out.println("count of Each row: " +resultSet.getDouble("quanty"));
				Double data =  resultSet.getDouble("quanty");
				//System.out.println(data.intValue());
				sum+=data.intValue();
				countFifteen.setText(sum.toString());
				//System.out.println("total: " +sum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadStockDetailsToTableViewHandler();
		dynamicClock();
		//takeCountOfSpecificCategory();
		totalCountOne();
		totalCountTwo();
		totalCountThree();
		totalCountFour();
		totalCountFive();
		totalCountSix();
		totalCountSeven();
		totalCountEight();
		totalCountNine();
		totalCountTen();
		totalCountEleven();
		totalCountTwelv();
		totalCountThirteen();
		totalCountFourteen();
		totalCountFifteen();
	}

}
