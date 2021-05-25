package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			Image icon = new Image(String.format("%s",classLoader.getResource("icon.png").toURI()));
			primaryStage.getIcons().add(icon);
			CardEvent.setStage(primaryStage);
			CardsPane.setStage(primaryStage);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("MainWindow.fxml"));
			VBox root = loader.load();
			BoardController controller = loader.getController();
			controller.setStage(primaryStage);
			controller.setup();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.setResizable(false);
			primaryStage.setTitle("Shlomi Fridman - Solitaire");
			primaryStage.setX(50);
			primaryStage.setY(50);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
