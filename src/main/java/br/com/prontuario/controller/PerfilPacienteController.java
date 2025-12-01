package br.com.prontuario.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;
import br.com.prontuario.app.App;
import br.com.prontuario.dao.AcompanhanteDAO;
import br.com.prontuario.dao.FatorRiscoDAO;
import br.com.prontuario.dao.HistoricoSaudeDAO;
import br.com.prontuario.dao.PacienteDAO;
import br.com.prontuario.dao.ProntuarioDAO;
import br.com.prontuario.model.Acompanhante;
import br.com.prontuario.model.Enfermeiro;
import br.com.prontuario.model.FatorRisco;
import br.com.prontuario.model.HistoricoSaude;
import br.com.prontuario.model.Paciente;
import br.com.prontuario.model.Prontuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PerfilPacienteController implements Initializable {
	// Componentes da interface
	@FXML private ImageView btnVoltar;
	@FXML private ImageView btnOpcoes;
	@FXML private Label lblTitulo;
	@FXML private ScrollPane painelProntuario;
	@FXML private VBox infoProntuario;
	@FXML private Label lblNenhumProntuario;
	@FXML private Button btnExcluir;
	@FXML private Button btnEditar;
	@FXML private Button btnExcluirProntuario;
	@FXML private Button btnEditarProntuario;
	@FXML private Button btnNovoProntuario;
	@FXML private AnchorPane avisoPacienteModificado;
	@FXML private Button btnOkPacienteModificado;
	@FXML private AnchorPane avisoExcluirPaciente;
	@FXML private Label lblExcluirPaciente;
	@FXML private Button btnSimExcluirPaciente;
	@FXML private Button btnNaoExcluirPaciente;
	@FXML private AnchorPane avisoExcluirProntuario;
	@FXML private Button btnSimExcluirProntuario;
	@FXML private Button btnNaoExcluirProntuario;
	@FXML private AnchorPane avisoProntuarioGerado;
	@FXML private Button btnOkProntuarioGerado;
	@FXML private AnchorPane avisoProntuarioModificado;
	@FXML private Button btnOkProntuarioModificado;
	@FXML private AnchorPane avisoProntuarioRemovido;
	@FXML private Button btnOkProntuarioRemovido;
	@FXML private AnchorPane painelOpcoes;
	@FXML private Button btnFecharPrograma;
	@FXML private Button btnEncerrarSessao;
	@FXML private AnchorPane avisoEncerrarSessao;
	@FXML private Button btnSimEncerrarSessao;
	@FXML private Button btnNaoEncerrarSessao;
	@FXML private AnchorPane avisoSairPrograma;
	@FXML private Button btnSimSairPrograma;
	@FXML private Button btnNaoSairPrograma;
	
	// Variaveis e instancias
	private Enfermeiro e;
	private Paciente p;
	private Prontuario pr;
	private boolean exibirOpcoes;
	private boolean veioDaTelaPacientes;
	private boolean veioDaTelaPrincipal;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		exibirOpcoes = false;
	}
	
	@FXML
	private void hoverEditarPaciente(MouseEvent event) {
		btnEditar.setStyle("-fx-background-color: #297373; -fx-background-radius: 8px");
	}
	
	@FXML
	private void exitEditarPaciente(MouseEvent event) {
		btnEditar.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 8px");
	}
	
	@FXML
	private void editarPaciente(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCadastroPacientes.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		CadastroPacientesController c = loader.getController();
		
		c.carregarUsuario(e);
		c.iniciarModificacao(p);
		c.origemPerfilPaciente();
		c.definirOrigem(veioDaTelaPrincipal, veioDaTelaPacientes);
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Prontuário Digital - Modificar " + p.getNome() + " " + p.getSobrenome());
		stage.setScene(scene);
	}
	
	@FXML
	private void hoverExcluirPaciente(MouseEvent event) {
		btnExcluir.setStyle("-fx-background-color: #b30b0e; -fx-background-radius: 8px");
	}
	
	@FXML
	private void exitExcluirPaciente(MouseEvent event) {
		btnExcluir.setStyle("-fx-background-color: #f6373a; -fx-background-radius: 8px");
	}
	
	@FXML
	private void prepararExclusaoPaciente(ActionEvent event) {
		String nomeCompleto = p.getNome() + " " + p.getSobrenome();
		lblExcluirPaciente.setText("Tem certeza que deseja excluir o paciente " + nomeCompleto + "?");
		avisoExcluirPaciente.setVisible(true);
	}
	
	@FXML
	private void hoverSimExcluirPaciente(MouseEvent event) {
		btnSimExcluirPaciente.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitSimExcluirPaciente(MouseEvent event) {
		btnSimExcluirPaciente.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML
	private void excluirPaciente(ActionEvent event) {
		PacienteDAO pDAO = new PacienteDAO();
		pDAO.delete(p);
		
		avisoExcluirPaciente.setVisible(false);
		
		if (veioDaTelaPacientes) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPacientes.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				PacientesController pc = loader.getController();
				pc.carregarUsuario(e);
				pc.notificarRemocao();
				
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.setTitle("Prontuário Digital - Lista de Pacientes");
				stage.setScene(scene);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (veioDaTelaPrincipal) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				PrincipalController p = loader.getController();
				p.carregarUsuario(e);
				p.notificarRemocao();
				
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.setTitle("Prontuário Digital - " + e.getNome());
				stage.setScene(scene);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void hoverNaoExcluirPaciente(MouseEvent event) {
		btnNaoExcluirPaciente.setStyle("-fx-background-color: #b30b0e; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitNaoExcluirPaciente(MouseEvent event) {
		btnNaoExcluirPaciente.setStyle("-fx-background-color: #f6373a; -fx-background-radius: 5px");
	}
	
	@FXML
	private void naoExcluir(ActionEvent event) {
		avisoExcluirPaciente.setVisible(false);
	}
	
	@FXML
	private void hoverNovoProntuario(MouseEvent event) {
		btnNovoProntuario.setStyle("-fx-background-color: #297373; -fx-background-radius: 8px");
	}
	
	@FXML
	private void exitNovoProntuario(MouseEvent event) {
		btnNovoProntuario.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 8px");
	}
	
	@FXML
	private void criarProntuario(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaProntuario.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ProntuarioController prc = loader.getController();
		
		prc.carregarUsuario(e);
		prc.carregarInformacoesPaciente(p);
		prc.definirOrigem(veioDaTelaPrincipal, veioDaTelaPacientes);
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Prontuário Digital - Gerar Prontuário de " + p.getNome() + " " + p.getSobrenome());
		stage.setScene(scene);
	}
	
	@FXML
	private void hoverEditarProntuario(MouseEvent event) {
		btnEditarProntuario.setStyle("-fx-background-color: #297373; -fx-background-radius: 8px");
	}
	
	@FXML
	private void exitEditarProntuario(MouseEvent event) {
		btnEditarProntuario.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 8px");
	}
	
	@FXML
	private void editarProntuario(ActionEvent event) throws IOException {
		if (pr != null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaProntuario.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			ProntuarioController prc = loader.getController();
			
			prc.carregarUsuario(e);
			prc.carregarInformacoesPaciente(p);
			prc.iniciarModificacao(pr);
			prc.definirOrigem(veioDaTelaPrincipal, veioDaTelaPacientes);
			
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setTitle("Prontuário Digital - Editar Prontuário de " + p.getNome() + " " + p.getSobrenome());
			stage.setScene(scene);
		}
	}
	
	@FXML
	private void hoverExcluirProntuario(MouseEvent event) {
		btnExcluirProntuario.setStyle("-fx-background-color: #b30b0e; -fx-background-radius: 8px");
	}
	
	@FXML
	private void exitExcluirProntuario(MouseEvent event) {
		btnExcluirProntuario.setStyle("-fx-background-color: #f6373a; -fx-background-radius: 8px");
	}
	
	@FXML
	private void prepararExclusaoProntuario(ActionEvent event) {
		avisoExcluirProntuario.setVisible(true);
	}
	
	@FXML
	private void hoverSimExcluirProntuario(MouseEvent event) {
		btnSimExcluirProntuario.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitSimExcluirProntuario(MouseEvent event) {
		btnSimExcluirProntuario.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML 
	private void excluirProntuario(ActionEvent event) {
		if (pr != null) {
			FatorRisco fr = pr.getFatorRisco();
			HistoricoSaude h = pr.getHistoricoSaude();
			
			ProntuarioDAO prDAO = new ProntuarioDAO();
			FatorRiscoDAO frDAO = new FatorRiscoDAO();
			HistoricoSaudeDAO hDAO = new HistoricoSaudeDAO();
			
			prDAO.delete(pr);
			frDAO.delete(fr);
			hDAO.delete(h);
			
			try {
				reiniciar(event);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void hoverNaoExcluirProntuario(MouseEvent event) {
		btnNaoExcluirProntuario.setStyle("-fx-background-color: #b30b0e; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitNaoExcluirProntuario(MouseEvent event) {
		btnNaoExcluirProntuario.setStyle("-fx-background-color: #f6373a; -fx-background-radius: 5px");
	}
	
	@FXML 
	private void naoExcluirProntuario(ActionEvent event) {
		avisoExcluirProntuario.setVisible(false);
	}
	
	private Acompanhante obterAcompanhante(Paciente p) {
		AcompanhanteDAO aDAO = new AcompanhanteDAO();
		List<Acompanhante> acompanhantes = aDAO.findAllByPaciente(p);
		Acompanhante a = null;
		
		for (int i = 0; i < acompanhantes.size(); i++) {
			Paciente pAcompanhante = acompanhantes.get(i).getPaciente();
			
			if (pAcompanhante.getCpf() == p.getCpf()) {
				a = acompanhantes.get(i);
				break;
			}
		}
		
		return a;
	}
	
	@FXML
	private void voltar(MouseEvent event) throws IOException {
		if (veioDaTelaPacientes) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPacientes.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			PacientesController p = loader.getController();
			p.carregarUsuario(e);
			
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setTitle("Prontuário Digital - Lista de Pacientes");
			stage.setScene(scene);
		}
		else if (veioDaTelaPrincipal) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			PrincipalController p = loader.getController();
			p.carregarUsuario(e);
			
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setTitle("Prontuário Digital - " + e.getNome());
			stage.setScene(scene);
		}
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
	private void hoverOkPacienteModificado(MouseEvent event) {
		btnOkPacienteModificado.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitOkPacienteModificado(MouseEvent event) {
		btnOkPacienteModificado.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML
	private void ocultarModificacao(ActionEvent event) {
		avisoPacienteModificado.setVisible(false);
	}
	
	@FXML
	private void hoverOkProntuarioGerado(MouseEvent event) {
		btnOkProntuarioGerado.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitOkProntuarioGerado(MouseEvent event) {
		btnOkProntuarioGerado.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML
	private void ocultarGeracaoProntuario(ActionEvent event) {
		avisoProntuarioGerado.setVisible(false);
	}
	
	@FXML
	private void hoverOkProntuarioModificado(MouseEvent event) {
		btnOkProntuarioModificado.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitOkProntuarioModificado(MouseEvent event) {
		btnOkProntuarioModificado.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML
	private void ocultarModificacaoProntuario(ActionEvent event) {
		avisoProntuarioModificado.setVisible(false);
	}
	
	@FXML
	private void hoverOkProntuarioRemovido(MouseEvent event) {
		btnOkProntuarioRemovido.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitOkProntuarioRemovido(MouseEvent event) {
		btnOkProntuarioRemovido.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void ocultarRemocaoProntuario(ActionEvent event) {
		avisoProntuarioRemovido.setVisible(false);
	}
	
	public void carregarPaciente(Paciente p) {
		this.p = p;
		lblTitulo.setText(this.p.getNome() + " " + this.p.getSobrenome());
		
		pr = encontrarProntuario(this.p);
		
		if (pr != null) {	
			painelProntuario.setVisible(true);
			lblNenhumProntuario.setVisible(false);
			btnEditarProntuario.setDisable(false);
			btnExcluirProntuario.setDisable(false);
			btnNovoProntuario.setDisable(true);
			
			Label informacoesPaciente = gerarTitulo("Informações do paciente");
			
			HBox nome = gerarInformacao();
			Label stNome = gerarSubTitulo("Nome completo:");
			Label pNome = gerarDado(p.getNome() + " " + p.getSobrenome());
			nome.getChildren().setAll(stNome, pNome);
			
			HBox cpf = gerarInformacao();
			Label stCpf = gerarSubTitulo("CPF:");
			Label pCpf = gerarDado(formatarCpf(p.getCpf()));
			cpf.getChildren().setAll(stCpf, pCpf);
			
			HBox sexo = gerarInformacao();
			Label stSexo = gerarSubTitulo("Sexo:");
			Label pSexo = gerarDado((p.getSexo() == 'F') ? "Feminino" : "Masculino");
			sexo.getChildren().setAll(stSexo, pSexo);
			
			HBox peso = gerarInformacao();
			Label stPeso = gerarSubTitulo("Peso:");
			Label pPeso = gerarDado(Double.toString(p.getPeso()) + " kg");
			peso.getChildren().setAll(stPeso, pPeso);
			
			HBox estadoCivil = gerarInformacao();
			Label stEstadoCivil = gerarSubTitulo("Estado civil:");
			Label pEstadoCivil = gerarDado(p.getEstadoCivil());
			estadoCivil.getChildren().setAll(stEstadoCivil, pEstadoCivil);
			
			HBox dataNascimento = gerarInformacao();
			Label stDataNascimento = gerarSubTitulo("Data de nascimento:");
			Label pDataNascimento = gerarDado(p.getDataNascimento().format(p.getFormatoData()));
			dataNascimento.getChildren().setAll(stDataNascimento, pDataNascimento);
			
			HBox dataEntrada = gerarInformacao();
			Label stDataEntrada = gerarSubTitulo("Data de entrada:");
			Label pDataEntrada = gerarDado(p.getDataEntrada().format(p.getFormatoDataHora()));
			dataEntrada.getChildren().setAll(stDataEntrada, pDataEntrada);
			
			String dSaida = (p.getDataSaida() != null) ? p.getDataSaida().format(p.getFormatoDataHora()) : "Nenhuma data registrada no momento";
			HBox dataSaida = gerarInformacao();
			Label stDataSaida = gerarSubTitulo("Data de saída:");
			Label pDataSaida = gerarDado(dSaida);
			dataSaida.getChildren().setAll(stDataSaida, pDataSaida);
			
			HBox idade = gerarInformacao();
			Label stIdade = gerarSubTitulo("Idade:");
			Label pIdade = gerarDado(Integer.toString(Period.between(p.getDataNascimento(), LocalDate.now()).getYears()));
			idade.getChildren().setAll(stIdade, pIdade);
			
			HBox endereco = gerarInformacao();
			Label stEndereco = gerarSubTitulo("Endereço:");
			Label pEndereco = gerarDado(p.getEndereco());
			endereco.getChildren().setAll(stEndereco, pEndereco);
			
			infoProntuario.getChildren().addAll(informacoesPaciente, nome, cpf, sexo, peso, estadoCivil, dataNascimento, idade, dataEntrada, dataSaida, endereco);
			
			Acompanhante a = obterAcompanhante(p);
			
			if (a != null) {
				Label informacoesAcompanhante = gerarTitulo("Informações do acompanhante");
				
				HBox nomeAcompanhante = gerarInformacao();
				Label stNomeAcompanhante = gerarSubTitulo("Nome do acompanhante:");
				Label pNomeAcompanhante = gerarDado(a.getNome());
				nomeAcompanhante.getChildren().setAll(stNomeAcompanhante, pNomeAcompanhante);
				
				HBox cpfAcompanhante = gerarInformacao();
				Label stCpfAcompanhante = gerarSubTitulo("CPF do acompanhante:");
				Label pCpfAcompanhante = gerarDado(formatarCpf(a.getCpf()));
				cpfAcompanhante.getChildren().setAll(stCpfAcompanhante, pCpfAcompanhante);
				
				HBox relacao = gerarInformacao();
				Label stRelacao = gerarSubTitulo("Relação com o paciente:");
				Label pRelacao = gerarDado(a.getRelacao());
				relacao.getChildren().setAll(stRelacao, pRelacao);
				
				infoProntuario.getChildren().addAll(informacoesAcompanhante, nomeAcompanhante, cpfAcompanhante, relacao);
			}
			
			Label informacoesFatorRisco = gerarTitulo("Fatores de risco");
			FatorRisco fr = pr.getFatorRisco();
			infoProntuario.getChildren().addAll(informacoesFatorRisco);
			
			HBox foraRiscoPele = gerarInformacao();
			Label stForaRiscoPele = gerarSubTitulo("Fora de risco:");
			Label pForaRiscoPele = gerarDado((fr.isForaDeRisco()) ? "Sim" : "Não");
			foraRiscoPele.getChildren().setAll(stForaRiscoPele, pForaRiscoPele);
			
			HBox foraRiscoFlebite = gerarInformacao();
			Label stForaRiscoFlebite = gerarSubTitulo("Fora de risco:");
			Label pForaRiscoFlebite = gerarDado((fr.isForaDeRisco()) ? "Sim" : "Não");
			foraRiscoFlebite.getChildren().setAll(stForaRiscoFlebite, pForaRiscoFlebite);
			
			HBox foraRiscoQueda = gerarInformacao();
			Label stForaRiscoQueda = gerarSubTitulo("Fora de risco:");
			Label pForaRiscoQueda = gerarDado((fr.isForaDeRisco()) ? "Sim" : "Não");
			foraRiscoQueda.getChildren().setAll(stForaRiscoQueda, pForaRiscoQueda);
			
			HBox criancaIdosoGestanteFlebite = gerarInformacao();
			Label stCriancaIdosoGestanteFlebite = gerarSubTitulo("Criança/Idoso/Gestante:");
			Label pCriancaIdosoGestanteFlebite = gerarDado((fr.isCriancaIdosoGestante()) ? "Sim" : "Não");
			criancaIdosoGestanteFlebite.getChildren().setAll(stCriancaIdosoGestanteFlebite, pCriancaIdosoGestanteFlebite);
			
			HBox criancaIdosoGestanteQueda = gerarInformacao();
			Label stCriancaIdosoGestanteQueda = gerarSubTitulo("Criança/Idoso/Gestante:");
			Label pCriancaIdosoGestanteQueda = gerarDado((fr.isCriancaIdosoGestante()) ? "Sim" : "Não");
			criancaIdosoGestanteQueda.getChildren().setAll(stCriancaIdosoGestanteQueda, pCriancaIdosoGestanteQueda);
			
			Label informacoesLesaoPele = gerarSubsecao("Lesão de pele:");
			
			HBox consciencia = gerarInformacao();
			Label stConsciencia = gerarSubTitulo("Alteração no nível de consciência:");
			Label pConsciencia = gerarDado((fr.isAlteracaoConsciencia()) ? "Sim" : "Não");
			consciencia.getChildren().setAll(stConsciencia, pConsciencia);
			
			HBox mobilidade = gerarInformacao();
			Label stMobilidade = gerarSubTitulo("Déficit de mobilidade/atividade:");
			Label pMobilidade = gerarDado((fr.isDeficitMobilidade()) ? "Sim" : "Não");
			mobilidade.getChildren().setAll(stMobilidade, pMobilidade);
			
			HBox nutricional = gerarInformacao();
			Label stNutricional = gerarSubTitulo("Déficit nutricional:");
			Label pNutricional = gerarDado((fr.isDeficitNutricional()) ? "Sim" : "Não");
			nutricional.getChildren().setAll(stNutricional, pNutricional);
			
			HBox peleUmida = gerarInformacao();
			Label stPeleUmida = gerarSubTitulo("Pele úmida/molhada:");
			Label pPeleUmida = gerarDado((fr.isPeleUmida()) ? "Sim" : "Não");
			peleUmida.getChildren().setAll(stPeleUmida, pPeleUmida);
			
			HBox cisalhamento = gerarInformacao();
			Label stCisalhamento = gerarSubTitulo("Fricção/Cisalhamento:");
			Label pCisalhamento = gerarDado((fr.isCisalhamento()) ? "Sim" : "Não");
			cisalhamento.getChildren().setAll(stCisalhamento, pCisalhamento);
			
			HBox limitacoesMobilidade = gerarInformacao();
			Label stLimitacoesMobilidade = gerarSubTitulo("Limitações de mobilidade:");
			Label pLimitacoesMobilidade = gerarDado((fr.isLimitacaoMobilidade()) ? "Sim" : "Não");
			limitacoesMobilidade.getChildren().setAll(stLimitacoesMobilidade, pLimitacoesMobilidade);
			
			infoProntuario.getChildren().addAll(informacoesLesaoPele, consciencia, mobilidade, nutricional, peleUmida, cisalhamento, limitacoesMobilidade, foraRiscoPele);
			
			Label informacoesFlebite = gerarSubsecao("Flebite:");
			
			HBox turgorPele = gerarInformacao();
			Label stTurgorPele = gerarSubTitulo("Alteração no turgor da pele:");
			Label pTurgorPele = gerarDado((fr.isTurgorPele()) ? "Sim" : "Não");
			turgorPele.getChildren().setAll(stTurgorPele, pTurgorPele);
			
			HBox imunoDepressao = gerarInformacao();
			Label stImunoDepressao = gerarSubTitulo("Imunodepressão:");
			Label pImunoDepressao = gerarDado((fr.isImunoDepressao()) ? "Sim" : "Não");
			imunoDepressao.getChildren().setAll(stImunoDepressao, pImunoDepressao);
			
			HBox fragilidadeCapilar = gerarInformacao();
			Label stFragilidadeCapilar = gerarSubTitulo("Fragilidade capilar:");
			Label pFragilidadeCapilar = gerarDado((fr.isFragilidadeCapilar()) ? "Sim" : "Não");
			fragilidadeCapilar.getChildren().setAll(stFragilidadeCapilar, pFragilidadeCapilar);
			
			HBox quimioterapia = gerarInformacao();
			Label stQuimioterapia = gerarSubTitulo("Quimioterapia:");
			Label pQuimioterapia = gerarDado((fr.isQuimioterapia()) ? "Sim" : "Não");
			quimioterapia.getChildren().setAll(stQuimioterapia, pQuimioterapia);
			
			HBox medHiperosmolares = gerarInformacao();
			Label stMedHiperosmolares = gerarSubTitulo("Medicações hiperosmolares:");
			Label pMedHiperosmolares = gerarDado((fr.isMedHiperosmolar()) ? "Sim" : "Não");
			medHiperosmolares.getChildren().setAll(stMedHiperosmolares, pMedHiperosmolares);
			
			infoProntuario.getChildren().addAll(informacoesFlebite, criancaIdosoGestanteFlebite, turgorPele, imunoDepressao, fragilidadeCapilar, quimioterapia, medHiperosmolares, foraRiscoFlebite);
			
			Label informacoesQueda = gerarSubsecao("Queda:");
			
			HBox convulsoes = gerarInformacao();
			Label stConvulsoes = gerarSubTitulo("Convulsões:");
			Label pConvulsoes = gerarDado((fr.isConvulsoes()) ? "Sim" : "Não");
			convulsoes.getChildren().setAll(stConvulsoes, pConvulsoes);
			
			HBox delirium = gerarInformacao();
			Label stDelirium = gerarSubTitulo("Confusão mental/delirium:");
			Label pDelirium = gerarDado((fr.isDelirium()) ? "Sim" : "Não");
			delirium.getChildren().setAll(stDelirium, pDelirium);
			
			HBox visaoAudicao = gerarInformacao();
			Label stVisaoAudicao = gerarSubTitulo("Visão/audição diminuída:");
			Label pVisaoAudicao = gerarDado((fr.isVisaoAudicao()) ? "Sim" : "Não");
			visaoAudicao.getChildren().setAll(stVisaoAudicao, pVisaoAudicao);
			
			HBox hipotensao = gerarInformacao();
			Label stHipotensao = gerarSubTitulo("Hipotensão postural:");
			Label pHipotensao = gerarDado((fr.isHipotensao()) ? "Sim" : "Não");
			hipotensao.getChildren().setAll(stHipotensao, pHipotensao);
			
			HBox alcoolDrogas = gerarInformacao();
			Label stAlcoolDrogas = gerarSubTitulo("Uso de álcool/drogas:");
			Label pAlcoolDrogas = gerarDado((fr.isUsoAlcoolDrogas()) ? "Sim" : "Não");
			alcoolDrogas.getChildren().setAll(stAlcoolDrogas, pAlcoolDrogas);
			
			infoProntuario.getChildren().addAll(informacoesQueda, criancaIdosoGestanteQueda, convulsoes, delirium, visaoAudicao, hipotensao, alcoolDrogas, foraRiscoQueda);
			
			Label informacoesHistoricoSaude = gerarTitulo("Histórico de saúde");
			HistoricoSaude h = pr.getHistoricoSaude();

			HBox tabagismo = gerarInformacao();
			Label stTabagismo = gerarSubTitulo("Tabagismo:");
			Label pTabagismo = gerarDado((h.isTabagismo()) ? "Sim" : "Não");
			tabagismo.getChildren().setAll(stTabagismo, pTabagismo);
			
			HBox neoplasia = gerarInformacao();
			Label stNeoplasia = gerarSubTitulo("Neoplasia:");
			Label pNeoplasia = gerarDado((h.isNeoplasia()) ? "Sim" : "Não");
			neoplasia.getChildren().setAll(stNeoplasia, pNeoplasia);
			
			HBox doencaAutoimune = gerarInformacao();
			Label stDoencaAutoimune = gerarSubTitulo("Doença autoimune:");
			Label pDoencaAutoimune = gerarDado((h.isDoencaAutoimune()) ? "Sim" : "Não");
			doencaAutoimune.getChildren().setAll(stDoencaAutoimune, pDoencaAutoimune);
			
			HBox doencaRespiratoria = gerarInformacao();
			Label stDoencaRespiratoria = gerarSubTitulo("Doença respiratória:");
			Label pDoencaRespiratoria = gerarDado((h.isDoencaRespiratoria()) ? "Sim" : "Não");
			doencaRespiratoria.getChildren().setAll(stDoencaRespiratoria, pDoencaRespiratoria);
			
			HBox doencaCardiovascular = gerarInformacao();
			Label stDoencaCardiovascular = gerarSubTitulo("Doença cardiovascular:");
			Label pDoencaCardiovascular = gerarDado((h.isDoencaCardiovascular()) ? "Sim" : "Não");
			doencaCardiovascular.getChildren().setAll(stDoencaCardiovascular, pDoencaCardiovascular);
			
			HBox diabetes = gerarInformacao();
			Label stDiabetes = gerarSubTitulo("Diabetes:");
			Label pDiabetes = gerarDado((h.isDiabetes()) ? "Sim" : "Não");
			diabetes.getChildren().setAll(stDiabetes, pDiabetes);
			
			HBox doencaRenal = gerarInformacao();
			Label stDoencaRenal = gerarSubTitulo("Doença renal:");
			Label pDoencaRenal = gerarDado((h.isDoencaRenal()) ? "Sim" : "Não");
			doencaRenal.getChildren().setAll(stDoencaRenal, pDoencaRenal);
			
			HBox doencasInfectocontagiosas = gerarInformacao();
			Label stDoencasInfectocontagiosas = gerarSubTitulo("Doenças infectocontagiosas:");
			Label pDoencasInfectocontagiosas = gerarDado((h.isDoencasInfectocontagiosas()) ? "Sim" : "Não");
			doencasInfectocontagiosas.getChildren().setAll(stDoencasInfectocontagiosas, pDoencasInfectocontagiosas);
			
			HBox dislipidemia = gerarInformacao();
			Label stDislipidemia = gerarSubTitulo("Dislipidemia:");
			Label pDislipidemia = gerarDado((h.isDislipidemia()) ? "Sim" : "Não");
			dislipidemia.getChildren().setAll(stDislipidemia, pDislipidemia);
			
			HBox etilismo = gerarInformacao();
			Label stEtilismo = gerarSubTitulo("Etilismo:");
			Label pEtilismo = gerarDado((h.isEtilismo()) ? "Sim" : "Não");
			etilismo.getChildren().setAll(stEtilismo, pEtilismo);
			
			HBox viroseInfancia = gerarInformacao();
			Label stViroseInfancia = gerarSubTitulo("Virose na infância:");
			Label pViroseInfancia = gerarDado((h.isViroseInfancia()) ? "Sim" : "Não");
			viroseInfancia.getChildren().setAll(stViroseInfancia, pViroseInfancia);
			
			infoProntuario.getChildren().addAll(informacoesHistoricoSaude, tabagismo, neoplasia, doencaAutoimune, doencaRespiratoria, doencaCardiovascular, diabetes, doencaRenal, doencasInfectocontagiosas, dislipidemia, etilismo, viroseInfancia);
			
			Label informacoesProntuario = gerarTitulo("Dados do prontuário");
			
			HBox motivoInternacao = gerarInformacao();
			Label stMotivoInternacao = gerarSubTitulo("Motivo da internação:");
			Label pMotivoInternacao = gerarDado(pr.getMotivoOncologico());
			motivoInternacao.getChildren().setAll(stMotivoInternacao, pMotivoInternacao);
			
			HBox enfermeiro = gerarInformacao();
			Label stEnfermeiro = gerarSubTitulo("Autor do prontuário:");
			Label pEnfermeiro = gerarDado(pr.getEnfermeiro().getNome());
			enfermeiro.getChildren().setAll(stEnfermeiro, pEnfermeiro);
			
			HBox dataEmissao = gerarInformacao();
			Label stDataEmissao = gerarSubTitulo("Data de emissão:");
			Label pDataEmissao = gerarDado(pr.getDataEmissao().format(pr.getFormatoDataHora()));
			dataEmissao.getChildren().setAll(stDataEmissao, pDataEmissao);
			
			HBox observacoes = gerarInformacao();
			Label stObservacoes = gerarSubTitulo("Observações:");
			Label pObservacoes = gerarDado(pr.getObservacao());
			observacoes.getChildren().setAll(stObservacoes, pObservacoes);
			
			infoProntuario.getChildren().addAll(informacoesProntuario, enfermeiro, dataEmissao, motivoInternacao, observacoes);
		}
		else {
			painelProntuario.setVisible(false);
			lblNenhumProntuario.setVisible(true);
			btnEditarProntuario.setDisable(true);
			btnExcluirProntuario.setDisable(true);
			btnNovoProntuario.setDisable(false);
		}
	}
	
	private HBox gerarInformacao() {
		HBox info = new HBox();
		info.setPadding(new Insets(10, 10, 10, 30));
		info.setPrefWidth(200);
		info.setPrefHeight(100);
		info.setSpacing(150);
		info.setAlignment(Pos.CENTER_LEFT);
		
		return info;
	}
	
	private Label gerarTitulo(String texto) {
		Label titulo = new Label(texto);
		titulo.setFont(Font.font("Afacad SemiBold", 17));
		titulo.setTextFill(Color.web("#545454"));
		
		return titulo;
	}
	
	private Label gerarSubsecao(String nome) {
		Label subsecao = new Label(nome);
		subsecao.setFont(Font.font("Afacad SemiBold", 15));
		subsecao.setTextFill(Color.web("#545454"));
		
		return subsecao;
	}
	
	private Label gerarSubTitulo(String nome) {
		Label subtitulo = new Label(nome);
		subtitulo.setFont(Font.font("Afacad SemiBold", 14));
		subtitulo.setTextFill(Color.web("#545454"));
		subtitulo.setPrefWidth(140);
		subtitulo.setMinWidth(140);
		
		return subtitulo;
	}
	
	private Label gerarDado(String dado) {
		Label d = new Label(dado);
		d.setFont(Font.font("Afacad", 14));
		d.setTextFill(Color.web("#545454"));
		
		d.setPrefWidth(150);
		d.setWrapText(true);
		
		return d;
	}
	
	private String formatarCpf(String cpf) {
		StringBuilder sb = new StringBuilder(cpf);
		
		if (cpf.length() > 3) sb.insert(3, ".");
		if (cpf.length() > 6) sb.insert(7, ".");
		if (cpf.length() > 9) sb.insert(11, "-");
		
		return sb.toString();
	}
	
	private void reiniciar(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPerfilPaciente.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		PerfilPacienteController pP = loader.getController();
		
		pP.carregarUsuario(e);
		pP.carregarPaciente(p);
		pP.notificarRemocaoProntuario();
		
		if (veioDaTelaPacientes) pP.origemTelaPacientes();
		if (veioDaTelaPrincipal) pP.origemTelaPrincipal();
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Prontuário Digital - Informações de " + p.getNome() + " " + p.getSobrenome());
		stage.setScene(scene);
	}
	
	public void carregarUsuario(Enfermeiro e) {
		this.e = e;
	}
	
	public void origemTelaPacientes() {
		veioDaTelaPacientes = true;
		veioDaTelaPrincipal = false;
	}
	
	public void origemTelaPrincipal() {
		veioDaTelaPacientes = false;
		veioDaTelaPrincipal = true;
	}
	
	private Prontuario encontrarProntuario(Paciente p) {
		ProntuarioDAO prDAO = new ProntuarioDAO();
		List<Prontuario> prontuarios = prDAO.findAll();
		Prontuario prontuario = null;
		
		for (int i = 0; i < prontuarios.size(); i++) {
			HistoricoSaude h = prontuarios.get(i).getHistoricoSaude();
			FatorRisco fr = prontuarios.get(i).getFatorRisco();
			
			if (h == null || fr == null) continue;
			if (h.getPaciente() == null || fr.getPaciente() == null) continue;
			
			if (h.getPaciente().getCpf().equals(p.getCpf()) && fr.getPaciente().getCpf().equals(p.getCpf())) {
				prontuario = prontuarios.get(i);
				break;
			}
		}
		
		return prontuario;
	} 
	
	public void notificarModificacao() {
		Platform.runLater(() -> {
			try {
				Thread.sleep(200);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
			avisoPacienteModificado.setVisible(true);
		});
	}
	
	public void notificarGeracaoProntuario() {
		Platform.runLater(() -> {
			try {
				Thread.sleep(200);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
			avisoProntuarioGerado.setVisible(true);
		});
	}
	
	public void notificarModificacaoProntuario() {
		Platform.runLater(() -> {
			try {
				Thread.sleep(200);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
			avisoProntuarioModificado.setVisible(true);
		});
	}
	
	public void notificarRemocaoProntuario() {
		Platform.runLater(() -> {
			try {
				Thread.sleep(200);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
			avisoProntuarioRemovido.setVisible(true);
		});
	}
	
	private void fechar(Button source) {
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
}