package song_library.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import song_library.view.SongLibraryController;

public class SongLibraryApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// create FXML loader
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/song_library/view/PlaylistView.fxml"));
		
		// load fmxl, root layout manager in fxml file is AnchorPane
		AnchorPane root = (AnchorPane)loader.load();

		//Controller
		SongLibraryController controller = loader.getController()
;		controller.start(primaryStage);
		
		// set scene to root
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
