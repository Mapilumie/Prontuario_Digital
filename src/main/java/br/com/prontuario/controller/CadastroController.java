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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtCoren.setTextFormatter(new TextFormatter<String>(change -> {
		    String text = change.getControlNewText().toUpperCase();

		    if (!text.matches("[A-Z0-9 \\-]*")) {
		        return null;
		    }

		    text = text.replaceAll("^COREN", "COREN");
		    text = text.replaceAll("^COREN-?", "COREN-");

		    if (!text.startsWith("COREN-")) {
		        text = "COREN-";
		    }

		    if (text.length() > 18) {
		        return null;
		    }

		    change.setText(text);
		    change.setRange(0, change.getControlText().length());

		    return change;
		}));
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