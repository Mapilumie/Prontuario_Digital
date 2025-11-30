package br.com.prontuario.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.prontuario.dao.EnfermeiroDAO;
import br.com.prontuario.model.Enfermeiro;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class LoginController implements Initializable {
	// Componentes da interface
	@FXML private TextField txtEmail;
	@FXML private PasswordField txtSenha;
	@FXML private Label lblCadastrar;
	@FXML private Button btnEntrar;
	@FXML private AnchorPane avisoNaoLogado;
	@FXML private Button btnOkNaoLogado;
	@FXML private AnchorPane avisoCamposVazios;
	@FXML private Button btnOkCamposVazios;
	
	// Variaveis e instancias
	private boolean emailVazio;
	private boolean senhaVazia;
	
	@Override 
	public void initialize(URL url, ResourceBundle rb) {
		txtEmail.textProperty().addListener((obs, oldValue, newValue) -> {
			if (emailVazio && !newValue.isEmpty()) {
				txtEmail.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8px");
				emailVazio = false;
			}
		});
		
		txtSenha.textProperty().addListener((obs, oldValue, newValue) -> {
			if (senhaVazia && !newValue.isEmpty()) {
				txtSenha.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8px");
				senhaVazia = false;
			}
		});
	}
	
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
			
			for (int i = 0; i < enfermeiros.size(); i++) { // Linha 78
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
			} 
			else {
				avisoNaoLogado.setVisible(true);
			} 
		}
		else {
			emailVazio = txtEmail.getText().isEmpty();
			senhaVazia = txtSenha.getText().isEmpty();
			
			if (emailVazio) txtEmail.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8px; -fx-border-color: #f6373a; -fx-border-width: 2px; -fx-border-radius: 8px");
			if (senhaVazia) txtSenha.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8px; -fx-border-color: #f6373a; -fx-border-width: 2px; -fx-border-radius: 8px");
			
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