package br.com.prontuario.app;

import java.io.IOException;
import br.com.prontuario.controller.SplashController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaSplash.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		SplashController s = loader.getController();
		
		stage.setTitle("Prontuário Digital");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
	
    public static void main(String[] args) {
        launch(args);
    }
}