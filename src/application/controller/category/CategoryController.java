package application.controller.category;

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
import application.model.CategoryModel;
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

public class CategoryController implements Initializable {

	String time = null;
	Stage primaryStage = new Stage();
	Connection connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	ObservableList<CategoryModel> observableList = FXCollections.observableArrayList();
	ObservableList<CategoryModel> observableDropList = FXCollections.observableArrayList();

	@FXML
	private Label inventoryLabel;

	@FXML
	private JFXButton logoutButton;

	@FXML
	private Label currentDate;

	@FXML
	private TextField searchCategory;

	@FXML
	private JFXButton magnifyingGlass;

	@FXML
	private JFXButton dashboardImage;

	@FXML
	private JFXButton stockImage;

	@FXML
	private JFXButton salesImage;

	@FXML
	private JFXButton brandImage;

	@FXML
	private JFXButton categoryImage;

	@FXML
	private JFXButton supplierImage;

	@FXML
	private TableView<?> categoryView;

	@FXML
	private TableView<CategoryModel> detailsOfCategory;

	@FXML
	private TableColumn<CategoryModel, String> categoryName;

	@FXML
	private TableColumn<CategoryModel, String> brandName;

	@FXML
	private TableColumn<CategoryModel, String> suplierName;

	@FXML
	private TableColumn<CategoryModel, String> categoryDescription;

	@FXML
	private MenuItem refresh;

	@FXML
	private JFXButton save;

	@FXML
	private JFXButton editStocks;

	@FXML
	private JFXButton dropStock;

	@FXML
	private JFXButton goBack;

	/**
	 * @param event drop category details and refresh the table
	 */
	@FXML
	public void dropCategoryDetails(ActionEvent event) {

		int id = 0, flag = 0;

		try {

			observableDropList = detailsOfCategory.getSelectionModel().getSelectedItems();

			for (int i = 0; i < observableDropList.size(); i++) {
				id = observableDropList.get(i).getCatSlno();
			}
			System.out.println("cat Id: " + id);

			String sql = "UPDATE categorydetails SET active_indicator=0 WHERE cat_slno=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			flag = preparedStatement.executeUpdate();

			if (flag == 1) {
				System.out.println("Raw deleted");
				loadCategoryDetails();
			} else {
				System.out.println("Data not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param event pass data to carry out edit process
	 */
	@FXML
	public void editCategoryDetials(ActionEvent event) {

		int slno = 0;
		String categoryName = null, brandName = null, supplierName = null, categoryDescription = null;

		try {

			observableList = detailsOfCategory.getSelectionModel().getSelectedItems();

			for (int i = 0; i < observableList.size(); i++) {
				slno = observableList.get(i).getCatSlno();
				categoryName = observableList.get(i).getCategoryName();
				brandName = observableList.get(i).getBrandName();
				supplierName = observableList.get(i).getSupplierName();
				categoryDescription = observableList.get(i).getCategoryDescription();
			}

			System.out.println(
					slno + ": " + categoryName + ": " + brandName + ": " + supplierName + ": " + categoryDescription);
			((Node) event.getSource()).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/application/fxmlpage/category/EditCategory.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 600, 400);

			EditCategoryController editCategoryController = loader.getController();
			editCategoryController.transferDetails(slno, categoryName, brandName, supplierName, categoryDescription);

			primaryStage.setScene(scene);
			primaryStage.setTitle("EDIT CATEGORY");
			primaryStage.isResizable();
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
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
	void goToDashsboard(ActionEvent event) throws IOException {
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
	void goToStocksSection(ActionEvent event) throws IOException {
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
	public void saveCategoryDetails(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/category/AddCategory.fxml"));
		Scene scene = new Scene(root, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ADD CATEGORY");
		primaryStage.isResizable();
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * @param event load all the category to table view, by searching
	 */
	@FXML
	public void searchByCategoryName(ActionEvent event) {
		System.out.println(searchCategory.getText());
		if (searchCategory.getText()==null|| searchCategory.getText().isEmpty() || searchCategory.getText().trim().isEmpty()) {
			loadCategoryDetails();
		} else {
			observableList.clear();
			try {
				String sql = "SELECT * FROM categorydetails WHERE active_indicator=1 AND (cat_slno=? OR category_name=? OR sup_name=? OR categoryDescription=? OR created_at=? OR updated_at=?)";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, searchCategory.getText());
				preparedStatement.setString(2, searchCategory.getText());
				preparedStatement.setString(3, searchCategory.getText());
				preparedStatement.setString(4, searchCategory.getText());
				preparedStatement.setString(5, searchCategory.getText());
				preparedStatement.setString(6, searchCategory.getText());

				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {

					observableList.add(new CategoryModel(resultSet.getInt("cat_slno"),
							resultSet.getString("category_name"), resultSet.getString("brand_name"),
							resultSet.getString("sup_name"), resultSet.getString("categoryDescription"),
							resultSet.getString("cat_creator"), resultSet.getString("created_at"),
							resultSet.getString("updated_at"), resultSet.getInt("active_indicator")));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			categoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
			brandName.setCellValueFactory(new PropertyValueFactory<>("brandName"));
			suplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
			categoryDescription.setCellValueFactory(new PropertyValueFactory<>("categoryDescription"));
			detailsOfCategory.setItems(observableList);
		}

	}

	@FXML
	public void onRefreshTheTable(ActionEvent event) {
		loadCategoryDetails();
	}

	/**
	 * load all the category details
	 */
	public void loadCategoryDetails() {

		observableList.clear();
		try {

			String sql = "SELECT * FROM categorydetails WHERE active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				observableList.add(new CategoryModel(resultSet.getInt("cat_slno"), resultSet.getString("category_name"),
						resultSet.getString("brand_name"), resultSet.getString("sup_name"),
						resultSet.getString("categoryDescription"), resultSet.getString("cat_creator"),
						resultSet.getString("created_at"), resultSet.getString("updated_at"),
						resultSet.getInt("active_indicator")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		categoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
		brandName.setCellValueFactory(new PropertyValueFactory<>("brandName"));
		suplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
		categoryDescription.setCellValueFactory(new PropertyValueFactory<>("categoryDescription"));
		detailsOfCategory.setItems(observableList);
		searchCategory.setText(null);

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
		loadCategoryDetails();
		dynamicClock();
	}
}
