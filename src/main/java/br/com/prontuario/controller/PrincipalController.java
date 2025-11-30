package br.com.prontuario.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import br.com.prontuario.app.App;
import br.com.prontuario.dao.PacienteDAO;
import br.com.prontuario.dao.ProntuarioDAO;
import br.com.prontuario.model.Enfermeiro;
import br.com.prontuario.model.FatorRisco;
import br.com.prontuario.model.HistoricoSaude;
import br.com.prontuario.model.Paciente;
import br.com.prontuario.model.Prontuario;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Cursor;
import javafx.scene.Node;

public class PrincipalController implements Initializable {
	// Componentes da interface
	@FXML private AnchorPane avisoBemVindo;
	@FXML private Label lblBemVindo;
	@FXML private Button btnOkBemVindo;
	@FXML private TextField txtPesquisa;
	@FXML private ScrollPane barraPesquisa;
	@FXML private AnchorPane painelPesquisa;
	@FXML private Label lblNenhum;
	@FXML private VBox resPesquisa;
	@FXML private ImageView btnOpcoes;
	@FXML private AnchorPane painelOpcoes;
	@FXML private ComboBox<String> cbPesquisa;
	@FXML private Button btnFecharPrograma;
	@FXML private Button btnEncerrarSessao;
	@FXML private Button btnCadastrarPacientes;
	@FXML private Button btnAcessarPacientes;
	@FXML private AnchorPane avisoPacienteCadastrado;
	@FXML private Button btnOkPacienteCadastrado;
	@FXML private AnchorPane avisoPacienteRemovido;
	@FXML private Button btnOkPacienteRemovido;
	@FXML private AnchorPane avisoEncerrarSessao;
	@FXML private Button btnSimEncerrarSessao;
	@FXML private Button btnNaoEncerrarSessao;
	@FXML private AnchorPane avisoSairPrograma;
	@FXML private Button btnSimSairPrograma;
	@FXML private Button btnNaoSairPrograma;
	
	// Variaveis e instancias
	private Enfermeiro e;
	private int tipoPesquisa;
	private boolean exibirOpcoes;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		exibirOpcoes = false;
		
		ObservableList<String> pesquisa = FXCollections.observableArrayList("Nome", "CPF");
		cbPesquisa.setItems(pesquisa);
		cbPesquisa.getSelectionModel().selectFirst();
		tipoPesquisa = cbPesquisa.getSelectionModel().getSelectedIndex();
		
		txtPesquisa.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue.isEmpty()) {
				painelPesquisa.setVisible(false);
			}
			else {
				painelPesquisa.setVisible(true);
				pesquisar(newValue);
			}
		});
		
		txtPesquisa.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (tipoPesquisa == 1) {
				if (!e.getCharacter().matches("[0-9]")) {
					e.consume();
					return;
				}
				
				String texto = txtPesquisa.getText().replaceAll("[^0-9]", "");
				
				if (texto.length() >= 11) {
					e.consume();
					return;
				}
				
				texto += e.getCharacter();
				StringBuilder sb = new StringBuilder(texto);
				
				if (texto.length() > 3) sb.insert(3, ".");
				if (texto.length() > 6) sb.insert(7, ".");
				if (texto.length() > 9) sb.insert(11, "-");
				
				txtPesquisa.setText(sb.toString());
				txtPesquisa.positionCaret(sb.length());
				e.consume();
			}
		});
		
		Platform.runLater(() -> {
			txtPesquisa.getScene().focusOwnerProperty().addListener((obs, oldNode, newNode) -> {
			    boolean clicouForaDoTextField = newNode != txtPesquisa;
			    boolean clicouForaDoPainelPesquisa = !isAncestorOf(painelPesquisa, newNode);
	
			    if (clicouForaDoTextField && clicouForaDoPainelPesquisa) {
			        painelPesquisa.setVisible(false);
			    }
			});
		});
	}
	
	private void pesquisar(String valor) {
		switch (tipoPesquisa) {
			case 0:
				pesquisarPorNome(valor);
				break;
				
			case 1:
				pesquisarPorCpf(valor);
				break;
				
			default:
				break;
		}
	}
	
	private void pesquisarPorNome(String nome) {
		if (!resPesquisa.getChildren().isEmpty()) {
			resPesquisa.getChildren().clear();
		}
		
		PacienteDAO dao = new PacienteDAO();
		List<Paciente> res = dao.findAllByNomeSobrenome(nome);
		
		if (res.size() != 0) {
			barraPesquisa.setVisible(true);
			lblNenhum.setVisible(false);
			
			for (int i = 0; i < res.size(); i++) {
				Paciente p = res.get(i);
				
				HBox caixa = new HBox();
				caixa.setStyle("-fx-background-color: #ffffff");
				caixa.setPrefWidth(372);
				caixa.setPrefHeight(49);
				caixa.setAlignment(Pos.CENTER_LEFT);
				caixa.setPadding(new Insets(10, 10, 10, 10));
				caixa.setCursor(Cursor.HAND);
				
				Label lblNome = new Label();
				lblNome.setFont(Font.font("Afacad SemiBold", 18));
				lblNome.setText(p.getNome() + " " + p.getSobrenome());
				
				caixa.setOnMouseEntered(event -> {
					caixa.setStyle("-fx-background-color: #bfbfbf");
				});
				
				caixa.setOnMouseExited(event -> {
					caixa.setStyle("-fx-background-color: #ffffff");
				});
				
				caixa.setOnMouseClicked(event -> {
					Paciente selecionado = p;
					
					try {
						carregarPerfil(selecionado, event);
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				});
				
				caixa.getChildren().add(lblNome);
				resPesquisa.getChildren().add(caixa);
			}
		}
		else {
			barraPesquisa.setVisible(false);
			lblNenhum.setVisible(true);
		}
	}
	
	private void pesquisarPorCpf(String cpf) {
		if (!resPesquisa.getChildren().isEmpty()) {
			resPesquisa.getChildren().clear();
		}
		
		String cpfFormatado = cpf.replaceAll("\\D", "");
		PacienteDAO dao = new PacienteDAO();
		Paciente p = dao.findByCpf(cpfFormatado);
		
		if (p != null) {
			barraPesquisa.setVisible(true);
			lblNenhum.setVisible(false);
			
			HBox caixa = new HBox();
			caixa.setStyle("-fx-background-color: #ffffff");
			caixa.setPrefWidth(372);
			caixa.setPrefHeight(49);
			caixa.setAlignment(Pos.CENTER_LEFT);
			caixa.setPadding(new Insets(10, 10, 10, 10));
			caixa.setCursor(Cursor.HAND);
			
			Label lblNome = new Label();
			lblNome.setFont(Font.font("Afacad SemiBold", 18));
			lblNome.setText(p.getNome() + " " + p.getSobrenome());
			
			caixa.setOnMouseEntered(event -> {
				caixa.setStyle("-fx-background-color: #bfbfbf");
			});
			
			caixa.setOnMouseExited(event -> {
				caixa.setStyle("-fx-background-color: #ffffff");
			});
			
			caixa.setOnMouseClicked(event -> {
				Paciente selecionado = p;
				
				try {
					carregarPerfil(selecionado, event);
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			caixa.getChildren().add(lblNome);
			resPesquisa.getChildren().add(caixa);
		}
		else {
			barraPesquisa.setVisible(false);
			lblNenhum.setVisible(true);
		}
	}
	
	private boolean isAncestorOf(Node parent, Node child) {
	    if (parent == null || child == null) return false;

	    Node atual = child;
	    
	    while (atual != null) {
	        if (atual == parent) {
	            return true;
	        }
	        atual = atual.getParent();
	    }
	    
	    return false;
	}
	
	@FXML
	private void selecionarPesquisa(ActionEvent event) {
		tipoPesquisa = cbPesquisa.getSelectionModel().getSelectedIndex();
	}
	
	private void carregarPerfil(Paciente p, MouseEvent event) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPerfilPaciente.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			PerfilPacienteController pP = loader.getController();
			pP.carregarPaciente(p);
			pP.carregarUsuario(e);
			pP.origemTelaPrincipal();
			
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setTitle("Prontuário Digital - Informações de " + p.getNome() + " " + p.getSobrenome());
			stage.setScene(scene);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	@FXML
	private void hoverCadastrarPacientes(MouseEvent event) {
		btnCadastrarPacientes.setStyle("-fx-background-color: #297373; -fx-background-radius: 8px; -fx-padding: 8px");
	}
	
	@FXML
	private void exitCadastrarPacientes(MouseEvent event) {
		btnCadastrarPacientes.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 8px; -fx-padding: 8px");
	}
	
	@FXML
	private void cadastrarPacientes(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCadastroPacientes.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		CadastroPacientesController c = loader.getController();
		c.carregarUsuario(e);
		c.origemTelaPrincipal();
		c.prepararCadastro();
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Prontuário Digital - Cadastrar Novo Paciente");
		stage.setScene(scene);
	}
	
	@FXML
	private void hoverAcessarPacientes(MouseEvent event) {
		btnAcessarPacientes.setStyle("-fx-background-color: #297373; -fx-background-radius: 8px; -fx-padding: 8px");
	}
	
	@FXML
	private void exitAcessarPacientes(MouseEvent event) {
		btnAcessarPacientes.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 8px; -fx-padding: 8px");
	}
	
	@FXML
	private void acessarPacientes(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPacientes.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		PacientesController p = loader.getController();
		p.carregarUsuario(e);
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Prontuário Digital - Lista de Pacientes");
		stage.setScene(scene);
	}
	
	@FXML
	private void mostrarOpcoes(MouseEvent event) {
		exibirOpcoes = !exibirOpcoes;
		painelOpcoes.setVisible(exibirOpcoes);
	}
	
	@FXML
	private void hoverFecharPrograma(MouseEvent event) {
		btnFecharPrograma.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 7px");
	}
	
	@FXML
	private void exitFecharPrograma(MouseEvent event) {
		btnFecharPrograma.setStyle("-fx-background-color: #fefefe; -fx-background-radius: 7px");
	}
	
	@FXML
	private void hoverEncerrarSessao(MouseEvent event) {
		btnEncerrarSessao.setStyle("-fx-background-color: #b30b0e; -fx-background-radius: 7px");
	}
	
	@FXML
	private void exitEncerrarSessao(MouseEvent event) {
		btnEncerrarSessao.setStyle("-fx-background-color: #F6373A; -fx-background-radius: 7px");
	}
	
	@FXML
	private void mostrarFecharPrograma(ActionEvent event) {
		avisoSairPrograma.setVisible(true);
	}
	
	@FXML
	private void mostrarEncerrarSessao(ActionEvent event) {
		avisoEncerrarSessao.setVisible(true);
	}
	
	@FXML
	private void hoverSimEncerrarSessao(MouseEvent event) {
		btnSimEncerrarSessao.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitSimEncerrarSessao(MouseEvent event) {
		btnSimEncerrarSessao.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML
	private void encerrarSessao(ActionEvent event) {
		try {
			fechar(btnSimEncerrarSessao);
			App app = new App();
			app.start(new Stage());
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	@FXML
	private void hoverSimFecharPrograma(MouseEvent event) {
		btnSimSairPrograma.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitSimFecharPrograma(MouseEvent event) {
		btnSimSairPrograma.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML
	private void fecharPrograma(ActionEvent event) {
		fechar(btnSimSairPrograma);
	}
	
	@FXML
	private void hoverNaoFecharPrograma(MouseEvent event) {
		btnNaoSairPrograma.setStyle("-fx-background-color: #b30b0e; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitNaoFecharPrograma(MouseEvent event) {
		btnNaoSairPrograma.setStyle("-fx-background-color: #f6373a; -fx-background-radius: 5px");
	}
	
	@FXML
	private void ocultarFecharPrograma(ActionEvent event) {
		avisoSairPrograma.setVisible(false);
	}
	
	@FXML
	private void hoverNaoEncerrarSessao(MouseEvent event) {
		btnNaoEncerrarSessao.setStyle("-fx-background-color: #b30b0e; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitNaoEncerrarSessao(MouseEvent event) {
		btnNaoEncerrarSessao.setStyle("-fx-background-color: #f6373a; -fx-background-radius: 5px");
	}
	
	@FXML
	private void ocultarEncerrarSessao(ActionEvent event) {
		avisoEncerrarSessao.setVisible(false);
	}
	
	@FXML
	private void hoverBemVindo(MouseEvent event) {
		btnOkBemVindo.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitBemVindo(MouseEvent event) {
		btnOkBemVindo.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML
	private void ocultarBemVindo(ActionEvent event) {
		avisoBemVindo.setVisible(false);
	}
	
	@FXML
	private void hoverPacienteCadastrado(MouseEvent event) {
		btnOkPacienteCadastrado.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitPacienteCadastrado(MouseEvent event) {
		btnOkPacienteCadastrado.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML
	private void ocultarPacienteCadastrado(ActionEvent event) {
		avisoPacienteCadastrado.setVisible(false);
	}
	
	@FXML
	private void hoverOkPacienteRemovido(MouseEvent event) {
		btnOkPacienteRemovido.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitOkPacienteRemovido(MouseEvent event) {
		btnOkPacienteRemovido.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML
	private void ocultarPacienteRemovido(ActionEvent event) {
		avisoPacienteRemovido.setVisible(false);;
	}
	
	public void carregarUsuario(Enfermeiro e) {
		this.e = e;
	}
	
	public void receber() {
		Platform.runLater(() -> {
			try {
				Thread.sleep(200);
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			
			avisoBemVindo.setVisible(true);
			lblBemVindo.setText("Bem-vindo(a), " + e.getNome() + "!");
		});
	}
	
	public void notificarCadastro() {
		Platform.runLater(() -> {
			try {
				Thread.sleep(200);
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			
			avisoPacienteCadastrado.setVisible(true);
		});
	}
	
	public void notificarRemocao() {
		Platform.runLater(() -> {
			try {
				Thread.sleep(200);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});
		
		avisoPacienteRemovido.setVisible(true);
	}
	
	private void fechar(Button source) {
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
}