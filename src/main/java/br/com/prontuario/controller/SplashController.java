package br.com.prontuario.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import br.com.prontuario.app.App;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SplashController implements Initializable {
	@FXML private AnchorPane painel;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		carregar();
	}

	private void carregar() {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(5000);
				}
				catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				
				Platform.runLater(() -> {
					try {
						fechar();
						App app = new App();
						app.start(new Stage());
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		}.start();
	}
	
	private void fechar() {
		Stage stage = (Stage) painel.getScene().getWindow();
		stage.close();
	}
}
