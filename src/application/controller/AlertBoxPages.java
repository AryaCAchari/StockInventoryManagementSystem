package application.controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBoxPages {
	
	Stage primaryStage = new Stage();
	
	public  void displayAlertbox(String title, String content) {
		
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.setTitle(title);
		primaryStage.setMinWidth(250);
		
		Label contentLable = new Label();
		contentLable.setText(content);
		Button closeButton = new Button("OK");
		closeButton.setOnAction(e -> primaryStage.close());
		
		VBox vboxLayout = new VBox(25);
		vboxLayout.getChildren().addAll(contentLable, closeButton);
		vboxLayout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(vboxLayout);
		primaryStage.setScene(scene);
		primaryStage.showAndWait();
	}
	
}
