package br.com.prontuario.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.prontuario.dao.EnfermeiroDAO;
import br.com.prontuario.model.Enfermeiro;
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
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CadastroController implements Initializable {
	// Componentes da interface
	@FXML private TextField txtNome;
	@FXML private TextField txtCoren;
	@FXML private TextField txtEmail;
	@FXML private PasswordField txtSenha;
	@FXML private Label lblLogin;
	@FXML private Button btnCadastrar;
	@FXML private AnchorPane avisoCamposVazios;
	@FXML private Button btnOkCamposVazios;
	
	// Variaveis e instancias
	private boolean nomeVazio;
	private boolean corenVazio;
	private boolean emailVazio;
	private boolean senhaVazia;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtCoren.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue.equals(oldValue)) return;
			
			String raw = newValue.replaceAll("[^A-Z0-9]", "").toUpperCase();
			
			 if (raw.isEmpty()) {
		        txtCoren.setText("COREN-");
		        txtCoren.positionCaret(txtCoren.getText().length());
		        return;
			 }
			 
			 if (raw.startsWith("COREN")) {
				 raw = raw.substring(5);
			 }
			 
			 String uf = raw.replaceAll("[^A-Z]", "");
			 if (uf.length() > 2) uf = uf.substring(0, 2);
			 
			 String digits = raw.replaceAll("[A-Z]", "");
			 if (digits.length() > 6) digits = digits.substring(0, 6);
			 
			 StringBuilder sb = new StringBuilder("COREN-");
			 sb.append(uf);
			 
			 if (uf.length() == 2 && digits.length() > 0) {
		        sb.append(" ");
			 }
			 
			 if (digits.length() <= 3) {
		        sb.append(digits);
		     } 
			 else {
		        sb.append(digits.substring(0, 3)).append(".").append(digits.substring(3));
			 }

		    String formatted = sb.toString();

		    if (!formatted.equals(newValue)) {
		        txtCoren.setText(formatted);
		        txtCoren.positionCaret(formatted.length());
		    }
		});
		
		txtNome.textProperty().addListener((obs, oldValue, newValue) -> {
			if (nomeVazio && !newValue.isEmpty()) {
				txtNome.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8px");
				nomeVazio = false;
			}
		});
		
		txtCoren.textProperty().addListener((obs, oldValue, newValue) -> {
			if (corenVazio && !newValue.replaceAll("\\D", "").isEmpty()) {
				txtCoren.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8px");
				corenVazio = false;
			}
		});
		
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
	private void hoverCadastrar(MouseEvent event) {
		btnCadastrar.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px");
	}
	
	@FXML
	private void exitCadastrar(MouseEvent event) {
		btnCadastrar.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8px");
	}
	
	@FXML 
	private void cadastrar(ActionEvent event) {
		if (!estaVazio()) {
			String nome = txtNome.getText();
			String coren = txtCoren.getText().replaceAll("\\D", "");
			String email = txtEmail.getText();
			String senha = txtSenha.getText();
			EnfermeiroDAO dao = new EnfermeiroDAO();
			
			Enfermeiro e = new Enfermeiro(coren, nome, email, senha);
			dao.save(e);
			
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
			nomeVazio = txtNome.getText().isEmpty();
			corenVazio = txtCoren.getText().replaceAll("\\D", "").isEmpty();
			emailVazio = txtEmail.getText().isEmpty();
			senhaVazia = txtSenha.getText().isEmpty();
			
			if (nomeVazio) txtNome.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8px; -fx-border-color: #f6373a; -fx-border-width: 2px; -fx-border-radius: 8px");
			if (corenVazio) txtCoren.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8px; -fx-border-color: #f6373a; -fx-border-width: 2px; -fx-border-radius: 8px");
			if (emailVazio) txtEmail.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8px; -fx-border-color: #f6373a; -fx-border-width: 2px; -fx-border-radius: 8px");
			if (senhaVazia) txtSenha.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8px; -fx-border-color: #f6373a; -fx-border-width: 2px; -fx-border-radius: 8px");
			
			avisoCamposVazios.setVisible(true);
		}
	}
	
	@FXML
	private void hoverLogin(MouseEvent event) {
		lblLogin.setTextFill(Color.web("#4bbbbf"));
	}
	
	@FXML
	private void exitLogin(MouseEvent event) {
		lblLogin.setTextFill(Color.web("#90fbff"));
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
	
	@FXML
	private void abrirTelaLogin(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaLogin.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Prontuário Digital - Login");
		stage.setScene(scene);
	}
	
	private boolean estaVazio() {
		if (txtNome.getText().isEmpty() || txtCoren.getText().replaceAll("\\D", "").isEmpty() || txtEmail.getText().isEmpty() || txtSenha.getText().isEmpty()) {
			return true;
		}
		
		return false;
	}
}