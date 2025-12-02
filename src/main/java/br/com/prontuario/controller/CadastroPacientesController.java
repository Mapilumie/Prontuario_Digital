package br.com.prontuario.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import br.com.prontuario.app.App;
import br.com.prontuario.dao.AcompanhanteDAO;
import br.com.prontuario.dao.CuidaDAO;
import br.com.prontuario.dao.PacienteDAO;
import br.com.prontuario.model.Acompanhante;
import br.com.prontuario.model.Cuida;
import br.com.prontuario.model.Enfermeiro;
import br.com.prontuario.model.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

public class CadastroPacientesController implements Initializable {
	// Componentes da interface
	@FXML private ImageView btnVoltar;
	@FXML private ImageView btnOpcoes;
	@FXML private TextField txtNome;
	@FXML private TextField txtSobrenome;
	@FXML private TextField txtCPF;
	@FXML private ComboBox<String> cbSexo;
	@FXML private Spinner<Double> spPeso;
	@FXML private DatePicker dpNasc;
	@FXML private ComboBox<String> cbEstadoCivil;
	@FXML private DatePicker dpEntrada;
	@FXML private DatePicker dpSaida;
	@FXML private TextField txtEndereco;
	@FXML private TextField txtNomeAcompanhante;
	@FXML private TextField txtCPFAcompanhante;
	@FXML private ComboBox<String> cbRelacao;
	@FXML private Button btnFinalizar;
	@FXML private AnchorPane painelOpcoes;
	@FXML private Button btnFecharPrograma;
	@FXML private Button btnEncerrarSessao;
	@FXML private AnchorPane avisoEncerrarSessao;
	@FXML private Button btnSimEncerrarSessao;
	@FXML private Button btnNaoEncerrarSessao;
	@FXML private AnchorPane avisoSairPrograma;
	@FXML private Button btnSimSairPrograma;
	@FXML private Button btnNaoSairPrograma;
	@FXML private AnchorPane avisoCamposObrigatorios;
	@FXML private Button btnOkCamposObrigatorios;
	
	// Variaveis e instancias
	private Paciente modificado;
	private Enfermeiro e;
	private boolean exibirOpcoes;
	private boolean veioDaTelaPrincipal;
	private boolean veioDoPerfilPaciente;
	private boolean atualizar;
	private boolean perfilOrigemPrincipal;
	private boolean perfilOrigemPacientes;
	private boolean nomeVazio;
	private boolean sobrenomeVazio;
	private boolean cpfVazio;
	private boolean digitouAcompanhante;
	private boolean nomeAcompanhanteVazio;
	private boolean cpfAcompanhanteVazio;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		nomeVazio = false;
		sobrenomeVazio = false;
		cpfVazio = false;
		exibirOpcoes = false;
		
		ObservableList<String> sexo = FXCollections.observableArrayList("Feminino", "Masculino");
		cbSexo.setItems(sexo);
		
		ObservableList<String> estadoCivil = FXCollections.observableArrayList("Solteiro", "Casado", "Separado", "Divorciado", "Viúvo");
		cbEstadoCivil.setItems(estadoCivil);
		
		ObservableList<String> relacao = FXCollections.observableArrayList("Parente", "Esposo(a)", "Filho(a)", "Amigo");
		cbRelacao.setItems(relacao);
		
		SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 300.0, 10.0, 0.1);
		valueFactory.setConverter(new DoubleStringConverter());
		spPeso.setValueFactory(valueFactory);
		spPeso.setEditable(true);
		
		dpEntrada.setValue(LocalDate.now());
		dpEntrada.setMouseTransparent(true);
		
		txtCPF.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (!e.getCharacter().matches("[0-9]")) {
				e.consume();
				return;
			}
			
			String texto = txtCPF.getText().replaceAll("[^0-9]", "");
			
			if (texto.length() >= 11) {
				e.consume();
				return;
			}
			
			texto += e.getCharacter();
			StringBuilder sb = new StringBuilder(texto);
			
			if (texto.length() > 3) sb.insert(3, ".");
			if (texto.length() > 6) sb.insert(7, ".");
			if (texto.length() > 9) sb.insert(11, "-");
			
			txtCPF.setText(sb.toString());
			txtCPF.positionCaret(sb.length());
			e.consume();
		});
		
		txtCPFAcompanhante.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (!e.getCharacter().matches("[0-9]")) {
				e.consume();
				return;
			}
			
			String texto = txtCPFAcompanhante.getText().replaceAll("[^0-9]", "");
			
			if (texto.length() >= 11) {
				e.consume();
				return;
			}
			
			texto += e.getCharacter();
			StringBuilder sb = new StringBuilder(texto);
			
			if (texto.length() > 3) sb.insert(3, ".");
			if (texto.length() > 6) sb.insert(7, ".");
			if (texto.length() > 9) sb.insert(11, "-");
			
			txtCPFAcompanhante.setText(sb.toString());
			txtCPFAcompanhante.positionCaret(sb.length());
			e.consume();
		});
		
		txtNome.textProperty().addListener((obs, oldValue, newValue) -> {
			if (nomeVazio && !newValue.isEmpty()) {
				txtNome.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px");
				nomeVazio = false;
			}
		});
		
		txtSobrenome.textProperty().addListener((obs, oldValue, newValue) -> {
			if (sobrenomeVazio && !newValue.isEmpty()) {
				txtSobrenome.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px");
				sobrenomeVazio = false;
			}
		});
		
		txtCPF.textProperty().addListener((obs, oldValue, newValue) -> {
			if (cpfVazio && !newValue.isEmpty()) {
				txtCPF.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px");
				cpfVazio = false;
			}
		});
		
		txtNomeAcompanhante.textProperty().addListener((obs, oldValue, newValue) -> {
			if (!digitouAcompanhante && !newValue.isEmpty() && txtCPFAcompanhante.getText().isEmpty()) {
				digitouAcompanhante = true;
			}
			else if (digitouAcompanhante && newValue.isEmpty() && txtCPFAcompanhante.getText().isEmpty()) {
				digitouAcompanhante = false;
			}
			
			if (digitouAcompanhante && nomeAcompanhanteVazio && !newValue.isEmpty()) {
				txtNomeAcompanhante.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px");
				nomeAcompanhanteVazio = false;
			}
		});
		
		txtCPFAcompanhante.textProperty().addListener((obs, oldValue, newValue) -> {
			if (!digitouAcompanhante && !newValue.isEmpty() && !txtNomeAcompanhante.getText().isEmpty()) {
				digitouAcompanhante = true;
			}
			else if (digitouAcompanhante && newValue.isEmpty() && txtNomeAcompanhante.getText().isEmpty()) {
				digitouAcompanhante = false;
			}
			
			if (digitouAcompanhante && cpfAcompanhanteVazio && !newValue.isEmpty()) {
				txtCPFAcompanhante.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px");
				cpfAcompanhanteVazio = false;
			}
		});
		
		cbRelacao.valueProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && (txtNomeAcompanhante.getText().isEmpty() || txtCPFAcompanhante.getText().isEmpty())) {
				digitouAcompanhante = true;
			}
			else if (newValue == null && (txtNomeAcompanhante.getText().isEmpty() && txtCPFAcompanhante.getText().isEmpty())) {
				digitouAcompanhante = false;
			}
		});;
	}
	
	@FXML
	private void voltar(MouseEvent event) throws IOException {
		if (veioDaTelaPrincipal) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			PrincipalController p = loader.getController();
			p.carregarUsuario(e);
			
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setTitle("Prontuário Digital - " + e.getNome());
			stage.setScene(scene);
		}
		else if (veioDoPerfilPaciente) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPerfilPaciente.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			PerfilPacienteController pP = loader.getController();
			pP.carregarUsuario(e);
			pP.carregarPaciente(modificado);
			
			if (perfilOrigemPrincipal) pP.origemTelaPrincipal();
			if (perfilOrigemPacientes) pP.origemTelaPacientes();
			
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setTitle("Prontuário Digital - Informações de " + modificado.getNome() + " " + modificado.getSobrenome());
			stage.setScene(scene);
		}
	}
	
	@FXML
	private void hoverFinalizar(MouseEvent event) {
		btnFinalizar.setStyle("-fx-background-color: #297373");
	}
	
	@FXML
	private void exitFinalizar(MouseEvent event) {
		btnFinalizar.setStyle("-fx-background-color: #4c9292");
	}
	
	@FXML
	private void finalizar(ActionEvent event) throws IOException {
		if (verificarCamposObrigatorios()) {
			String nome = txtNome.getText();
			String sobrenome = txtSobrenome.getText();
			String cpf = txtCPF.getText().replaceAll("\\D", "");
			char sexo = (cbSexo.getSelectionModel().getSelectedIndex() == 0) ? 'F' : 'M';
			Double peso = spPeso.getValue();
			LocalDate dataNascimento = dpNasc.getValue();
			String estadoCivil = (String) cbEstadoCivil.getValue();
			String endereco = txtEndereco.getText();
			Paciente p = new Paciente(cpf, nome, sobrenome, estadoCivil, endereco, sexo, peso, dataNascimento);
			Acompanhante a = null;
			
			if (dpSaida.getValue() != null) {
				LocalDateTime dataSaida = dpSaida.getValue().atStartOfDay();
				p.setDataSaida(dataSaida);
			}
			if (verificarAcompanhante()) {
				String nomeAcompanhante = txtNomeAcompanhante.getText();
				String cpfAcompanhante = txtCPFAcompanhante.getText().replaceAll("\\D", "");
				String relacao = (String) cbRelacao.getValue();

				a = new Acompanhante(cpfAcompanhante, nomeAcompanhante, relacao, p);
			}
			
			if (!atualizar) {
				PacienteDAO pDao = new PacienteDAO();
				pDao.save(p);

				Cuida c = new Cuida(e, p);
				CuidaDAO cDao = new CuidaDAO();
				cDao.save(c);

				if (a != null) {
					AcompanhanteDAO aDao = new AcompanhanteDAO();
					aDao.save(a);
				}

				carregarTelaPrincipal(event);
			} 
			else {
				PacienteDAO pDao = new PacienteDAO();
				pDao.update(p);

				if (a != null) {
					AcompanhanteDAO aDao = new AcompanhanteDAO();
					aDao.update(a);
				}

				carregarPerfilPaciente(p, event);
			} 
		}
		else {
			nomeVazio = txtNome.getText().isEmpty();
			sobrenomeVazio = txtSobrenome.getText().isEmpty();
			cpfVazio = txtCPF.getText().isEmpty();
			if (digitouAcompanhante) nomeAcompanhanteVazio = txtNomeAcompanhante.getText().isEmpty();
			if (digitouAcompanhante) cpfAcompanhanteVazio = txtCPFAcompanhante.getText().isEmpty();
			
			if (nomeVazio) txtNome.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px; -fx-border-radius: 8px; -fx-border-color: #f3673a; -fx-border-width: 2px");
			if (sobrenomeVazio) txtSobrenome.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px; -fx-border-radius: 8px; -fx-border-color: #f3673a; -fx-border-width: 2px");
			if (cpfVazio) txtCPF.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px; -fx-border-radius: 8px; -fx-border-color: #f3673a; -fx-border-width: 2px");
			if (digitouAcompanhante && nomeAcompanhanteVazio) txtNomeAcompanhante.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px; -fx-border-radius: 8px; -fx-border-color: #f3673a; -fx-border-width: 2px");
			if (digitouAcompanhante && cpfAcompanhanteVazio) txtCPFAcompanhante.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px; -fx-border-radius: 8px; -fx-border-color: #f3673a; -fx-border-width: 2px");
			
			avisoCamposObrigatorios.setVisible(true);
		}
	}
	
	private void carregarTelaPrincipal(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		PrincipalController p = loader.getController();
		p.carregarUsuario(e);
		p.notificarCadastro();
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Prontuário Digital - " + e.getNome());
		stage.setScene(scene);
	}
	
	private void carregarPerfilPaciente(Paciente p, ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPerfilPaciente.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		PerfilPacienteController pP = loader.getController();
		pP.carregarUsuario(e);
		pP.carregarPaciente(p);
		pP.notificarModificacao();
		
		if (perfilOrigemPrincipal) pP.origemTelaPrincipal();
		if (perfilOrigemPacientes) pP.origemTelaPacientes();
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Prontuário Digital - Informações de " + p.getNome() + " " + p.getSobrenome());
		stage.setScene(scene);
	}
	
	private boolean verificarCamposObrigatorios() {
		if ((txtNome.getText().isEmpty() || txtSobrenome.getText().isEmpty() || txtCPF.getText().isEmpty()) || acompanhanteVazio()) {
			return false;
		}
		
		return true;
	}
	
	private boolean verificarAcompanhante() {
		if (txtCPFAcompanhante.getText().isEmpty() && txtNomeAcompanhante.getText().isEmpty() && cbRelacao.getValue() == null) {
			return false;
		}
		
		return true;
	}
	
	private boolean acompanhanteVazio() {
		if (digitouAcompanhante && (txtNomeAcompanhante.getText().isEmpty() || txtCPFAcompanhante.getText().isEmpty())) {
			return true;
		}
		
		return false;
	}
	
	public void prepararCadastro() {
		atualizar = false;
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
	private void hoverOkCamposObrigatorios(MouseEvent event) {
		btnOkCamposObrigatorios.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitOkCamposObrigatorios(MouseEvent event) {
		btnOkCamposObrigatorios.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML
	private void ocultarAvisoCamposObrigatorios(ActionEvent event) {
		avisoCamposObrigatorios.setVisible(false);
	}
	
	public void carregarUsuario(Enfermeiro e) {
		this.e = e;
	}
	
	public void origemTelaPrincipal() {
		veioDaTelaPrincipal = true;
		veioDoPerfilPaciente = false;
	}
	
	public void origemPerfilPaciente() {
		veioDaTelaPrincipal = false;
		veioDoPerfilPaciente = true;
	}
	
	public void iniciarModificacao(Paciente p) {
		atualizar = true;
		modificado = p;
		
		txtNome.setText(modificado.getNome());
		txtSobrenome.setText(modificado.getSobrenome());
		txtCPF.setText(formatarCpf(modificado.getCpf()));
		if (Character.valueOf(modificado.getSexo()) != null) cbSexo.getSelectionModel().select((modificado.getSexo() == 'F') ? 0 : 1);
		
		if (modificado.getPeso() != null && modificado.getPeso() != 0.0) {
			SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(
					0.0, 300.0, (double) modificado.getPeso(), 0.1);
			valueFactory.setConverter(new DoubleStringConverter());
			spPeso.setValueFactory(valueFactory);
		}
		
		if (modificado.getEstadoCivil() != null && !modificado.getEstadoCivil().isEmpty()) cbEstadoCivil.getSelectionModel().select(obterEstadoCivil(modificado.getEstadoCivil()));
		if (modificado.getDataNascimento() != null) dpNasc.setValue(modificado.getDataNascimento());
		if (modificado.getDataEntrada() != null) dpEntrada.setValue(modificado.getDataEntrada().toLocalDate());
		if (modificado.getDataSaida() != null) dpSaida.setValue(modificado.getDataSaida().toLocalDate());
		if (!modificado.getEndereco().isEmpty()) txtEndereco.setText(modificado.getEndereco());
		
		List<Acompanhante> acompanhantes = new AcompanhanteDAO().findAllByPaciente(modificado);
		Acompanhante a = null;
		
		for (int i = 0; i < acompanhantes.size(); i++) {
			Paciente pAcompanhante = acompanhantes.get(i).getPaciente();
			
			if (pAcompanhante.getCpf() == modificado.getCpf()) {
				a = acompanhantes.get(i);
				break;
			}
		}
		
		if (a != null) {
			txtNomeAcompanhante.setText(a.getNome());
			txtCPFAcompanhante.setText(formatarCpf(a.getCpf()));
			if (a.getRelacao() != null && !a.getRelacao().isEmpty()) cbRelacao.getSelectionModel().select(obterRelacao(a.getRelacao()));
		}
	}
	
	private String formatarCpf(String cpf) {
		StringBuilder sb = new StringBuilder(cpf);
		
		if (cpf.length() > 3) sb.insert(3, ".");
		if (cpf.length() > 6) sb.insert(7, ".");
		if (cpf.length() > 9) sb.insert(11, "-");
		
		return sb.toString();
	}
	
	private int obterEstadoCivil(String estadoCivil) {
		switch (estadoCivil) {
			case "Solteiro":
				return 0;
				
			case "Casado":
				return 1;
				
			case "Separado":
				return 2;
				
			case "Divorciado":
				return 3;
				
			case "Viúvo":
				return 4;
		}
		
		return 0;
	}
	
	private int obterRelacao(String relacao) {
		switch (relacao) {
			case "Parente":
				return 0;
			
			case "Esposo(a)":
				return 1;
				
			case "Filho(a)":
				return 2;
				
			case "Amigo":
				return 3;
		}
		
		return 0;
	}
	
	public void definirOrigem(boolean principal, boolean pacientes) {
		this.perfilOrigemPrincipal = principal;
		this.perfilOrigemPacientes = pacientes;
	}
	
	private void fechar(Button source) {
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
}