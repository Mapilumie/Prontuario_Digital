package br.com.prontuario.controller;

import java.io.IOException;
import java.util.List;
import br.com.prontuario.dao.EnfermeiroDAO;
import br.com.prontuario.model.Enfermeiro;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {
	// Componentes da interface
	@FXML private TextField txtEmail;
	@FXML private PasswordField txtSenha;
	@FXML private Label lblCadastrar;
	@FXML private Button btnEntrar;
	@FXML private AnchorPane avisoNaoLogado;
	@FXML private Button btnOkNaoLogado;
	@FXML private AnchorPane avisoCamposVazios;
	@FXML private Button btnOkCamposVazios;
	
	@FXML
	private void hoverEntrar(MouseEvent event) {
		btnEntrar.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px");
	}
	
	@FXML
	private void exitEntrar(MouseEvent event) {
		btnEntrar.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8px");
	}
	
	@FXML 
	private void entrar(ActionEvent event) {
		if (!estaVazio()) {
			String email = txtEmail.getText();
			String senha = txtSenha.getText();
			EnfermeiroDAO dao = new EnfermeiroDAO();
			List<Enfermeiro> enfermeiros = dao.findAll();
			Enfermeiro e = null;
			
			for (int i = 0; i < enfermeiros.size(); i++) {
				if (email.equals(enfermeiros.get(i).getEmail()) && senha.equals(enfermeiros.get(i).getSenha())) {
					e = enfermeiros.get(i);
					break;
				}
			}
			
			if (e != null) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
					Parent root = loader.load();
					Scene scene = new Scene(root);
					PrincipalController p = loader.getController();
					p.carregarUsuario(e);
					p.receber();

					Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					stage.setTitle("Prontuário Digital - " + e.getNome());
					stage.setScene(scene);
				} 
				catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				new Thread() {
					public void run() {
						try {
							Thread.sleep(200);
						} catch (InterruptedException ex) {
							Thread.currentThread().interrupt();
						}

						Platform.runLater(() -> {
							avisoNaoLogado.setVisible(true);
						});
					}
				}.start();
			} 
		}
		else {
			avisoCamposVazios.setVisible(true);
		}
	}
	
	@FXML
	private void hoverCadastrar(MouseEvent event) {
		lblCadastrar.setTextFill(Color.web("#4bbbbf"));
	}
	
	@FXML
	private void exitCadastrar(MouseEvent event) {
		lblCadastrar.setTextFill(Color.web("#90fbff"));
	}
	
	@FXML
	private void abrirTelaCadastrar(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCadastro.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Prontuário Digital - Cadastro");
		stage.setScene(scene);
	}
	
	@FXML
	private void hoverOkNaoLogado(MouseEvent event) {
		btnOkNaoLogado.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitOkNaoLogado(MouseEvent event) {
		btnOkNaoLogado.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML
	private void ocultarAvisoNaoLogado(ActionEvent event) {
		avisoNaoLogado.setVisible(false);
	}
	
	@FXML
	private void hoverOkCamposVazios(MouseEvent event) {
		btnOkCamposVazios.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitOkCamposVazios(MouseEvent event) {
		btnOkCamposVazios.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML
	private void ocultarAvisoCamposVazios(ActionEvent event) {
		avisoCamposVazios.setVisible(false);
	}
	
	private boolean estaVazio() {
		if (txtEmail.getText().isEmpty() || txtSenha.getText().isEmpty()) {
			return true;
		}
		
		return false;
	}
}