package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPageController {

	@FXML
	private TextField textfeild_UserName;
	
	@FXML
	private TextField passFeild_Password;
	
	Stage primaryStage = new Stage();
	
	public void loginTosystem(ActionEvent event) throws Exception {
		
		if(textfeild_UserName.getText().equals("admin") && passFeild_Password.getText().equals("admin")) {
			System.out.println("Login Success!");
			((Node)event.getSource()).getScene().getWindow().hide();
			Parent root = FXMLLoader.load(getClass().getResource("/application/fxmlpage/Dashbord.fxml"));
			Scene scene = new Scene(root,1024,768);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("DASHBOARD");
			primaryStage.isResizable();
			primaryStage.setResizable(true);
			primaryStage.setMaximized(true);
			//primaryStage.setFullScreen(true);
			primaryStage.show();
			
			textfeild_UserName.setText("");
			passFeild_Password.setText("");
		}else {
			System.out.println("Failed");
		}
	}
}
