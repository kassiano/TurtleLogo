package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {

		Parent p = FXMLLoader.load(getClass().getResource("Main.fxml"));

		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("turtle.png")));

		primaryStage.setScene(new Scene(p));
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
