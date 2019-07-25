package application.controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
	
	static boolean choice;
	
	static Stage primaryStage = new Stage();
	
	public static boolean displayAlertbox(String title, String content) {
		
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.setTitle(title);
		primaryStage.setMinWidth(250);
		
		Label contentLable = new Label();
		contentLable.setText(content);
		Button yesButton = new Button("LOGOUT");
		yesButton.setOnAction(e -> {
			choice = true;
			primaryStage.close();
			
		});
		
		Button noButton = new Button("CANCEL");
		yesButton.setOnAction(e -> {
			choice = false;
			primaryStage.close();
		});
		
		HBox hboxLayout = new HBox(10);
		Region region = new Region();
		hboxLayout.getChildren().addAll(yesButton, region, noButton);
		hboxLayout.setAlignment(Pos.CENTER);
		
		VBox vboxLayout = new VBox(50);
		vboxLayout.getChildren().addAll(contentLable, hboxLayout);
		vboxLayout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(vboxLayout);
		primaryStage.setScene(scene);
		primaryStage.showAndWait();
		
		return choice;
	}
	
}
