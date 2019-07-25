package application.controller.supplier;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import application.databaseConnection.MysqlConnectionSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EditSupplierDetailsController implements Initializable {

	private Connection con = null;
	private PreparedStatement prdStatement = null;
	boolean flag = true;
	Stage primaryStage = new Stage();

	@FXML
	private JFXTextField editName;

	@FXML
	private JFXTextField editMobileNumber;

	@FXML
	private JFXTextField editLandlineNumber;

	@FXML
	private JFXTextArea editAddress;

	@FXML
	private Button editSupplier;

	@FXML
	private Button clearFeilds;

	@FXML
	private Button backbutton;

	int id = 0;

	/**
	 * @param slNum
	 * @param name
	 * @param phoneNumber
	 * @param landlineNumber
	 * @param address
	 * 
	 * read above details from controller
	 */
	public void transferDetails(int slNum, String name, String phoneNumber, String landlineNumber, String address) {

		this.id = slNum;
		editName.setText(name);
		editMobileNumber.setText(phoneNumber);
		editLandlineNumber.setText(landlineNumber);
		editAddress.setText(address);

	}

	/**
	 * @param event
	 * @throws IOException
	 * edit supplier details
	 */
	@FXML
	public void editSupplieretails(ActionEvent event) throws IOException {
		System.out.println("Is in editController: " + this.id);
		try {

			con = MysqlConnectionSingleton.dbConnectionblock().getConnection();
			String sql = "UPDATE supplierdetails SET sup_name=?, sup_mob=?, sup_landnum=?, sup_addr=? WHERE sl_num = '"
					+ this.id + "' AND active_indicator=1";
			prdStatement = con.prepareStatement(sql);

			prdStatement.setString(1, editName.getText());
			prdStatement.setString(2, editMobileNumber.getText());
			prdStatement.setString(3, editLandlineNumber.getText());
			prdStatement.setString(4, editAddress.getText());
			int flag = prdStatement.executeUpdate();
			System.out.println(flag);

			if (flag == 1) {

				System.out.println("Supply Details Updated!");
				editName.setText(null);
				editMobileNumber.setText(null);
				editLandlineNumber.setText(null);
				editAddress.setText(null);
				((Node) event.getSource()).getScene().getWindow().hide();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void gobacktoPreviousStage(ActionEvent event) throws IOException {

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

	/**
	 * @param event
	 * refresh the table
	 */
	@FXML
	public void refreshOrClearScreen(ActionEvent event) {

		editName.setText(null);
		editMobileNumber.setText(null);
		editLandlineNumber.setText(null);
		editAddress.setText(null);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
