package application.controller.brand;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.databaseConnection.MysqlConnectionSingleton;
import application.model.BrandsModel;
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

public class BrandsController implements Initializable {

	String time = null;
	Stage primaryStage = new Stage();
	ObservableList<BrandsModel> brandTableView = FXCollections.observableArrayList();
	ObservableList<BrandsModel> observableList = FXCollections.observableArrayList();
	Connection connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Statement statement = null;

	@FXML
	private Label inventoryLabel;

	@FXML
	private JFXButton logoutButton;

	@FXML
	private Label currentDate;

	@FXML
	private TextField searchBrands;

	@FXML
	private JFXButton magnifyingGlass;

	@FXML
	private JFXButton dashBoardImage;

	@FXML
	private JFXButton stockImage;

	@FXML
	private JFXButton salesImage;

	@FXML
	private JFXButton categoryImage;

	@FXML
	private JFXButton supplierImage;

	@FXML
	private TableView<?> brandsView;

	@FXML
	private TableView<BrandsModel> detailsOfBrands;

	@FXML
	private JFXButton saveBrands;

	@FXML
	private JFXButton editBrands;

	@FXML
	private JFXButton dropBrands;

	@FXML
	private JFXButton goBack;

	@FXML
	private TableColumn<BrandsModel, String> brandName;

	@FXML
	private TableColumn<BrandsModel, String> supplierName;

	@FXML
	private MenuItem refershTable;

	@FXML
	public void dropBradnsDetails(ActionEvent event) {

		int flag = 0;
		try {

			observableList = detailsOfBrands.getSelectionModel().getSelectedItems();

			int id = 0;
			for (int i = 0; i < observableList.size(); i++) {
				id = observableList.get(i).getBrnd_slno();
			}
			System.out.println("ID: " + id);

			String sql = "UPDATE branddetails SET active_indicator = 0 WHERE brnd_slno=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			flag = preparedStatement.executeUpdate();

			if (flag == 1) {
				System.out.println("Raw deleted");
			} else {
				System.out.println("Data intruption");
			}

			loadBrandDataToTable();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void editBrandsDetials(ActionEvent event) {

		try {

			brandTableView = detailsOfBrands.getSelectionModel().getSelectedItems();

			int brnd_slno = 0, active_indicator = 0;
			String brand_name = null, sup_name = null;
			// , created_at, updated_at

			for (int i = 0; i < brandTableView.size(); i++) {

				brnd_slno = brandTableView.get(i).getBrnd_slno();
				active_indicator = brandTableView.get(i).getActive_indicator();
				brand_name = brandTableView.get(i).getBrand_name();
				sup_name = brandTableView.get(i).getSup_name();
//				created_at = brandTableView.get(i).getCreated_at();
//				updated_at = brandTableView.get(i).getUpdated_at();
			}

			System.out.println(
					"SELECTED RAW: " + brnd_slno + " : " + active_indicator + " : " + brand_name + " : " + sup_name);
			((Node) event.getSource()).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxmlpage/brand/EditBrand.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 235, 216);

			EditBrandController brandController = loader.getController();
			brandController.transferDetails(brnd_slno, brand_name, sup_name);

			primaryStage.setScene(scene);
			primaryStage.setTitle("EDIT BRANDS");
			primaryStage.isResizable();
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void goBackToPreviousPage(ActionEvent event) throws IOException {
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
	void logoutFromTheSystem(ActionEvent event) throws IOException {
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
	public void goToCategorySection(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/category/Category.fxml"));
		Scene scene = new Scene(root, 1024, 768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("CATEGORY");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		// primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	public void goToDashboard(ActionEvent event) throws IOException {
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
	public void goToSalesSection(ActionEvent event) throws IOException {
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
	public void goToStocksSection(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/stock/Stocks.fxml"));
		Scene scene = new Scene(root, 1024, 768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("STOCKS");
		primaryStage.isResizable();
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		// primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	@FXML
	public void goToSupplierSection(ActionEvent event) throws IOException {
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

	@FXML
	public void saveBrandDetails(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/brand/AddBrand.fxml"));
		Scene scene = new Scene(root, 235, 216);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ADD BRAND");
		primaryStage.isResizable();
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	@FXML
	public void searchByBrandsName(ActionEvent event) {

		if (searchBrands.getText().isEmpty() || searchBrands.getText().trim().isEmpty()) {
			loadBrandDataToTable();
		} else {
			brandTableView.clear();
			try {

				String sql = "SELECT * FROM branddetails WHERE active_indicator = 1 AND brnd_slno=? OR brand_name=? OR sup_name=?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, searchBrands.getText());
				preparedStatement.setString(2, searchBrands.getText());
				preparedStatement.setString(3, searchBrands.getText());

				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {

					brandTableView
							.add(new BrandsModel(resultSet.getInt("brnd_slno"), resultSet.getInt("active_indicator"),
									resultSet.getString("brand_name"), resultSet.getString("sup_name"),
									resultSet.getString("created_at"), resultSet.getString("updated_at")));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			brandName.setCellValueFactory(new PropertyValueFactory<>("brand_name"));
			supplierName.setCellValueFactory(new PropertyValueFactory<>("sup_name"));
			detailsOfBrands.setItems(brandTableView);
			searchBrands.setText(null);
		}

	}

	/**
	 * Fetsch all brand Details
	 */
	public void loadBrandDataToTable() {

		brandTableView.clear();
		try {

			String sql = "SELECT * FROM branddetails WHERE active_indicator = 1";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {

				brandTableView.add(new BrandsModel(resultSet.getInt("brnd_slno"), resultSet.getInt("active_indicator"),
						resultSet.getString("brand_name"), resultSet.getString("sup_name"),
						resultSet.getString("created_at"), resultSet.getString("updated_at")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		brandName.setCellValueFactory(new PropertyValueFactory<>("brand_name"));
		supplierName.setCellValueFactory(new PropertyValueFactory<>("sup_name"));
		detailsOfBrands.setItems(brandTableView);
	}

	@FXML
	public void refreshTableHandler(ActionEvent event) {
		loadBrandDataToTable();
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

		loadBrandDataToTable();
		brandTableView = FXCollections.observableArrayList();
		dynamicClock();
	}
}
