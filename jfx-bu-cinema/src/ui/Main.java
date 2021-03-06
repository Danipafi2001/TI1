package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
	
	private CinemaGUI cinemagui;
	
	public Main() {
		cinemagui = new CinemaGUI();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/main-pane.fxml"));
		fxmlLoader.setController(cinemagui);
		Parent root = fxmlLoader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("BU Cinema");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("PNG/icon.png")));
		cinemagui.loaderIDS();
		cinemagui.loaderPane("FXML/signin.fxml");
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
}