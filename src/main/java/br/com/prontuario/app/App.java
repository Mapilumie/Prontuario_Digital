package br.com.prontuario.app;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaLogin.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		Font.loadFont(getClass().getResourceAsStream("/fonts/Afacad-Bold.ttf"), 18);
		Font.loadFont(getClass().getResourceAsStream("/fonts/Afacad-SemiBold.ttf"), 18);
		Font.loadFont(getClass().getResourceAsStream("/fonts/Afacad-VariableFont_wght.ttf"), 18);
		Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto-Bold.ttf"), 18);
		Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto-SemiBold.ttf"), 18);
		Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto-VariableFont_wdth,wght.ttf"), 18);
		
		Image icone = new Image(getClass().getResource("/images/prdigitalicon.png").toExternalForm());
		stage.getIcons().add(icone);
		
		stage.setTitle("Prontuário Digital - Login");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.DECORATED);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}