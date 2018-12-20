package client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainGUI extends Application{
	@Override
	public void start(Stage stage) throws Exception {
		ClientPanel clientPanel = new ClientPanel();
		Group root = new Group();
		root.getChildren().add(clientPanel);
		Scene scene = new Scene(root, 500, 825);
		stage.setTitle("Pendu");
		stage.setScene(scene);
		stage.show();
	}

}
