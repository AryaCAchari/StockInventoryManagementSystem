package application.controller.sales;

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
import application.model.SalesModel;
import application.model.StocksModel;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * @author USER
 *
 */
public class SalesController implements Initializable {

	int id = 0, serialNumber = 0;
	String name, code, model, brand, billNumber;
	int quantity = 0;
	Double sellingRate = 0D, ratePerUnit = 0D, amountCalculated = 0D, sum = 0D;
	Double paidAmount = 0D, netAmout = 0D, balanceAmount = 0D;
	String time = null;
	Double readAmount[];

	Stage primaryStage = new Stage();
	Connection connection = MysqlConnectionSingleton.dbConnectionblock().getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	ObservableList<StocksModel> observableListProduct = FXCollections.observableArrayList();
	ObservableList<SalesModel> observableListSales = FXCollections.observableArrayList();

	@FXML
	private Label inventoryLabel;

	@FXML
	private JFXButton logoutButton;
	
	@FXML
    private Label currentDate;

	@FXML
	private TextField typeProductname;

	@FXML
	private JFXButton searchButton;

	@FXML
	private MenuItem selectRaw;
	
	@FXML
	private TableView<StocksModel> productView;

	@FXML
	private TableColumn<StocksModel, String> productName;

	@FXML
	private TableColumn<StocksModel, String> totalQuantity;

	@FXML
	private TableColumn<StocksModel, String> pricePerUnit;

	@FXML
	private TableView<SalesModel> salesDetailsTable;

	@FXML
	private TableColumn<SalesModel, String> commadityName;

	@FXML
	private TableColumn<SalesModel, String> productCode;

	@FXML
	private TableColumn<SalesModel, String> productModel;

	@FXML
	private TableColumn<SalesModel, String> productBrand;

	@FXML
	private TableColumn<SalesModel, Integer> productQuantity;

	@FXML
	private TableColumn<SalesModel, Double> price;

	@FXML
	private TableColumn<SalesModel, Double> amount;

	@FXML
	private TextField productNameTextField;

	@FXML
	private TextField brandTextField;

	@FXML
	private TextField priceTextField;

	@FXML
	private TextField quantityTextField;

	@FXML
	private TextField totalAmount;

	@FXML
	private TextField userPaidAmout;

	@FXML
	private TextField balanceToUser;
	
	@FXML
	private JFXButton balanceCalculator;

	@FXML
	private JFXButton saveBill;

	@FXML
	private JFXButton cancelSales;

	@FXML
	private JFXButton goBack;

	@FXML
	public void goBacktoProductServicePage(ActionEvent event) throws IOException {

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
	public void logoutFromSystem(ActionEvent event) throws IOException {
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
	
	/**
	 * @param event
	 * select each row, to load onto sales table
	 */
	@FXML
	void onSelectProductRaw(ActionEvent event) {

		try {
			observableListProduct = productView.getSelectionModel().getSelectedItems();
			this.id = observableListProduct.get(0).getStkSlno();
			this.name = observableListProduct.get(0).getProductName();
			this.code = observableListProduct.get(0).getProductCode();
			this.model = observableListProduct.get(0).getProductModel();
			this.brand = observableListProduct.get(0).getBrandName();
			this.ratePerUnit = observableListProduct.get(0).getSellRateUnit();
			this.quantity = observableListProduct.get(0).getQuantity();

			this.productNameTextField.setText(name);
			this.brandTextField.setText(brand);
			this.priceTextField.setText(Double.toString(ratePerUnit));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param event
	 * prevent the purchase, and refresh the entire code 
	 */
	@FXML
	public void refreshAndCancelSales(ActionEvent event) {
		
		//salesDetailsTable.getItems().clear();	
		this.salesDetailsTable.setItems(null);
		this.totalAmount.setText(null);
		this.userPaidAmout.setText(null);
		this.balanceToUser.setText(null);
		
		try {			
			String sql = "UPDATE sales SET active_indicator=0 WHERE bill_number='"+this.billNumber+"'";
			preparedStatement = connection.prepareStatement(sql);
			int flag  = preparedStatement.executeUpdate();
			System.out.println("Bill Canceled: " +flag);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param event
	 * 
	 * To calculate the total amount and load into table
	 */
	@FXML
	public void onCalculateAmountAndSaveData(ActionEvent event) {
		
		try {
			int purchaseQuantity = Integer.parseInt(quantityTextField.getText());
			this.amountCalculated = ratePerUnit * purchaseQuantity;
			observableListSales.addAll(
					new SalesModel(id, name, model, code, brand, purchaseQuantity, ratePerUnit, amountCalculated));
			commadityName.setCellValueFactory(new PropertyValueFactory<>("commadityNameSales"));
			productCode.setCellValueFactory(new PropertyValueFactory<>("productCodeSales"));
			productModel.setCellValueFactory(new PropertyValueFactory<>("productModelSales"));
			productBrand.setCellValueFactory(new PropertyValueFactory<>("productBrandSales"));
			productQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
			price.setCellValueFactory(new PropertyValueFactory<>("price"));
			amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
			salesDetailsTable.setItems(observableListSales);
			
			System.out.println(salesDetailsTable.getItems().size());
			readAmount = new Double[salesDetailsTable.getItems().size()];
			
			for(int i = 0; i < salesDetailsTable.getItems().size(); i++) {				
				observableListSales = salesDetailsTable.getItems();
				readAmount[i] = observableListSales.get(i).getAmount();
				System.out.println("Read amount: " +i+" : " +readAmount[i]);
				
				if(i == 0) {
					sum=readAmount[i];
					System.out.println("Total Amount:" +sum);
				}else {
					sum+=readAmount[i];
					System.out.println("Total Amount:" +sum);
				}
				this.serialNumber=(i+1);
				System.out.println("Serial Number Of each row: " +(i+1));
				
			}
			
			totalAmount.setText((this.sum).toString());
			
			System.out.println("Id of Stocks: " +this.id);
			System.out.println("Purchase Quantity: " +quantityTextField.getText());
			System.out.println("Price per peice: " +priceTextField.getText());
			System.out.println("Amount of Each commadity: " +this.amountCalculated);
			System.out.println("Bill Number: " +billNumber);
			System.out.println("Current Time: " +Utilities.currentDateAndTime());
			
			//Saving each row to table with unique bill number
			String sql = "INSERT INTO sales(bill_number, serial_number, quantity, unit_price, amount, created_at, updated_at, active_indicator, stock_id)VALUES(?,?,?,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, billNumber);
			preparedStatement.setInt(2, this.serialNumber);
			preparedStatement.setInt(3, Integer.parseInt(quantityTextField.getText()));
			preparedStatement.setDouble(4, ratePerUnit);
			preparedStatement.setDouble(5, this.amountCalculated);
			preparedStatement.setString(6, Utilities.currentDateAndTime());
			preparedStatement.setString(7, Utilities.currentDateAndTime());
			preparedStatement.setInt(8, 1);
			preparedStatement.setInt(9, this.id);
			boolean flag = preparedStatement.execute();
			if(flag==false) {
				System.out.println("Bill saved");
			}else {
				System.out.println("Data saving error");
			}
			
			updateProductTableByQuantity();
			
			this.productNameTextField.setText(null);
			this.brandTextField.setText(null);
			this.priceTextField.setText(null);
			this.quantityTextField.setText(null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *Update the stock table according to balanced quantity 
	 */
	public void updateProductTableByQuantity() {
		
		try {
			String quantityPurchase = this.quantityTextField.getText();
//			int balanceQuantity = this.quantity-(Integer.parseInt(quantityTextField.getText()));
			int balanceQuantity = this.quantity-(Integer.parseInt(quantityPurchase.trim()));
			System.out.println("Balance Inquiry: " +balanceQuantity);
			String sql = "UPDATE stockdetails SET quanty='"+balanceQuantity+"' WHERE stk_slno='"+this.id+"' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			int flag = preparedStatement.executeUpdate();
			if(flag == 1) {
				System.out.println("Stock updated!: " +flag);
			}else {
				System.out.println("Stock can not updated");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param event
	 * calculate balance remit to customers
	 */
	@FXML
	public  void calculateBalnceAmountToUser(ActionEvent event) {
		
		this.paidAmount = Double.valueOf(userPaidAmout.getText());
		this.netAmout = Double.valueOf(totalAmount.getText());
		if(this.paidAmount>=this.netAmout) {
			this.balanceAmount = this.paidAmount-netAmout;
			balanceToUser.setText(this.balanceAmount.toString());
		}
	}

	/**
	 * @param event
	 * save the total, paid amount by user and balance to sales table accortding to billNumber
	 */
	@FXML
	public void saveSalesDetails(ActionEvent event) {
		
		try {
			String sql = "UPDATE sales SET total_amount=?, paid=?, balance=?, updated_at=? WHERE bill_number='"+this.billNumber+"' AND active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setDouble(1, this.netAmout);
			preparedStatement.setDouble(2, this.paidAmount);
			preparedStatement.setDouble(3, this.balanceAmount);
			preparedStatement.setString(4, Utilities.currentDateAndTime());
			
			int flag = preparedStatement.executeUpdate();
			System.out.println("Query executed: " +flag);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.salesDetailsTable.setItems(null);
		this.totalAmount.setText(null);
		this.userPaidAmout.setText(null);
		this.balanceToUser.setText(null);
	}
	
	/**
	 * @param event
	 * search by stk_slno=, invoice_num, batch_number, product_code, prdct_name, prdct_model, part_No, brnd_name, ctgry_name 
	 */
	@FXML
	public void searchProductName(ActionEvent event) {
		
		if(typeProductname.getText().isEmpty() || typeProductname.getText().trim().isEmpty()) {
			loadProductTableDetails();
		}else {
			observableListProduct.clear();
			try {
				String sql = "SELECT * FROM stockdetails WHERE active_indicator=1 AND(stk_slno=? OR invoice_num=? OR batch_number=? OR product_code=? OR prdct_name=? OR prdct_model=? OR part_No=? OR brnd_name=? OR ctgry_name=?)";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, typeProductname.getText());
				preparedStatement.setString(2, typeProductname.getText());
				preparedStatement.setString(3, typeProductname.getText());
				preparedStatement.setString(4, typeProductname.getText());
				preparedStatement.setString(5, typeProductname.getText());
				preparedStatement.setString(6, typeProductname.getText());
				preparedStatement.setString(7, typeProductname.getText());
				preparedStatement.setString(8, typeProductname.getText());
				preparedStatement.setString(9, typeProductname.getText());
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()) {				
					this.id = resultSet.getInt("stk_slno");
					this.sellingRate = resultSet.getDouble("sell_rate_unit");
					observableListProduct.add(new StocksModel(resultSet.getInt("stk_slno"),
							resultSet.getString("prdct_name"), resultSet.getString("prdct_model"),
							resultSet.getString("product_code"), resultSet.getString("part_No"),
							resultSet.getString("batch_number"), resultSet.getInt("quanty"),
							resultSet.getDouble("purchase_rate"), resultSet.getDouble("sell_rate_unit"),
							resultSet.getString("invoice_num"), resultSet.getString("sup_name"),
							resultSet.getString("brnd_name"), resultSet.getString("ctgry_name")));

				}
				productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
				totalQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
				pricePerUnit.setCellValueFactory(new PropertyValueFactory<>("sellRateUnit"));
				productView.setItems(observableListProduct);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * load the stock table 
	 */
	public void loadProductTableDetails() {

		observableListProduct.clear();
		try {

			String sql = "select * from stockdetails where active_indicator=1";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				this.id = resultSet.getInt("stk_slno");
				this.sellingRate = resultSet.getDouble("sell_rate_unit");
				observableListProduct.add(new StocksModel(resultSet.getInt("stk_slno"),
						resultSet.getString("prdct_name"), resultSet.getString("prdct_model"),
						resultSet.getString("product_code"), resultSet.getString("part_No"),
						resultSet.getString("batch_number"), resultSet.getInt("quanty"),
						resultSet.getDouble("purchase_rate"), resultSet.getDouble("sell_rate_unit"),
						resultSet.getString("invoice_num"), resultSet.getString("sup_name"),
						resultSet.getString("brnd_name"), resultSet.getString("ctgry_name")));

			}

			productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
			totalQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			pricePerUnit.setCellValueFactory(new PropertyValueFactory<>("sellRateUnit"));
			productView.setItems(observableListProduct);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
		
	public void dynamicClock() {		
		new Thread(new Runnable() {
		    @Override public void run() {
		        for (;;) {
		            Platform.runLater(new Runnable() {
		                @Override public void run() {
		                	Calendar calendar = new GregorianCalendar();
							int day = calendar.get(Calendar.DAY_OF_MONTH);
							int month = calendar.get(Calendar.MONTH);
							int year = calendar.get(Calendar.YEAR);

							int seconds = calendar.get(Calendar.SECOND);
							int minutes = calendar.get(Calendar.MINUTE);
							int hours = calendar.get(Calendar.HOUR);
							int amOrPm = calendar.get(Calendar.AM_PM);
							String timeMeridian = null;
							if(amOrPm==1) {
								timeMeridian="PM";
							}else {
								timeMeridian="AM";
							}
							
							time = year+":"+month+":"+day+": "+hours+":"+minutes+":"+seconds+":"+timeMeridian;													
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
		loadProductTableDetails();
		billNumber = Utilities.generateBillNumber();
		dynamicClock();
	}

}
