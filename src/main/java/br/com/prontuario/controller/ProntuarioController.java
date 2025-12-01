package br.com.prontuario.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import org.apache.commons.io.IOUtils;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import br.com.prontuario.app.App;
import br.com.prontuario.dao.FatorRiscoDAO;
import br.com.prontuario.dao.HistoricoSaudeDAO;
import br.com.prontuario.dao.ProntuarioDAO;
import br.com.prontuario.model.Enfermeiro;
import br.com.prontuario.model.FatorRisco;
import br.com.prontuario.model.HistoricoSaude;
import br.com.prontuario.model.Paciente;
import br.com.prontuario.model.Prontuario;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProntuarioController implements Initializable {
	// Componentes da interface
	@FXML private ScrollPane painelDados;
	@FXML private TextField txtNome;
	@FXML private TextField txtSobrenome;
	@FXML private TextField txtCPF;
	@FXML private ComboBox<String> cbSexo;
	@FXML private Spinner<Double> spPeso;
	@FXML private DatePicker dpNasc;
	@FXML private ComboBox<String> cbEstadoCivil;
	@FXML private DatePicker dpEntrada;
	@FXML private DatePicker dpSaida;
	@FXML private TextField txtProfissao;
	@FXML private TextField txtTelefone;
	@FXML private TextField txtEndereco;
	@FXML private TextField txtMotivoInternacao;
	@FXML private CheckBox ckbConsciencia;
	@FXML private CheckBox ckbMobilidade;
	@FXML private CheckBox ckbNutricional;
	@FXML private CheckBox ckbPeleUmida;
	@FXML private CheckBox ckbCisalhamento;
	@FXML private CheckBox ckbLimitacoesMobilidade;
	@FXML private CheckBox ckbForaRiscoPele;
	@FXML private CheckBox ckbCriancaIdoso;
	@FXML private CheckBox ckbTurgorPele;
	@FXML private CheckBox ckbImunodepressao;
	@FXML private CheckBox ckbFragilidadeCapilar;
	@FXML private CheckBox ckbQuimioterapia;
	@FXML private CheckBox ckbMedicacoesHiperosmolares;
	@FXML private CheckBox ckbForaRiscoFlebite;
	@FXML private CheckBox ckbCriancaIdosoGestante;
	@FXML private CheckBox ckbConvulsoes;
	@FXML private CheckBox ckbDelirium;
	@FXML private CheckBox ckbVisaoAudicao;
	@FXML private CheckBox ckbHipotensao;
	@FXML private CheckBox ckbAlcoolDrogas;
	@FXML private CheckBox ckbForaRiscoQueda;
	@FXML private CheckBox ckbDoencasInfectocontagiosas;
	@FXML private CheckBox ckbDislipidemia;
	@FXML private CheckBox ckbEtilismo;
	@FXML private CheckBox ckbHipertensao;
	@FXML private CheckBox ckbTransfusaoSanguinea;
	@FXML private CheckBox ckbViroseInfancia;
	@FXML private CheckBox ckbTabagismo;
	@FXML private CheckBox ckbNeoplasia;
	@FXML private CheckBox ckbDoencaAutoimune;
	@FXML private CheckBox ckbDoencaRespiratoria;
	@FXML private CheckBox ckbDoencaCardiovascular;
	@FXML private CheckBox ckbDiabetes;
	@FXML private CheckBox ckbDoencaRenal;
	@FXML private TextArea txtAlergias;
	@FXML private TextArea txtObservacoes;
	@FXML private Button btnFinalizar;
	@FXML private ImageView btnVoltar;
	@FXML private ImageView btnOpcoes;
	@FXML private AnchorPane painelOpcoes;
	@FXML private Button btnFecharPrograma;
	@FXML private Button btnEncerrarSessao;
	@FXML private AnchorPane avisoEncerrarSessao;
	@FXML private Button btnSimEncerrarSessao;
	@FXML private Button btnNaoEncerrarSessao;
	@FXML private AnchorPane avisoSairPrograma;
	@FXML private Button btnSimSairPrograma;
	@FXML private Button btnNaoSairPrograma;
	@FXML private AnchorPane avisoCaminhoProntuario;
	@FXML private Button btnOkCaminhoProntuario;
	@FXML private AnchorPane avisoMotivoOncologicoVazio;
	@FXML private Button btnOkMotivoOncologicoVazio;
	
	// Variaveis e instancias
	private long idModificado;
	private long idHistoricoSaude;
	private long idFatorRisco;
	private Prontuario modificado;
	private Enfermeiro e;
	private Paciente p;
	private boolean motivoOncologicoVazio;
	private boolean atualizar;
	private boolean exibirOpcoes;
	private boolean veioDaTelaPrincipal;
	private boolean veioDaTelaPacientes;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		exibirOpcoes = false;
		
		ObservableList<String> sexo = FXCollections.observableArrayList("Feminino", "Masculino");
		cbSexo.setItems(sexo);
		
		ObservableList<String> estadoCivil = FXCollections.observableArrayList("Solteiro", "Casado", "Separado", "Divorciado", "Viúvo");
		cbEstadoCivil.setItems(estadoCivil);
		
		txtTelefone.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				String digitos = newValue.replaceAll("[^0-9]", "");
				
				if (digitos.length() > 11)
	                digitos = digitos.substring(0, 11);

	            StringBuilder telefone = new StringBuilder();

	            if (digitos.length() > 0) {
	                telefone.append("(");
	                telefone.append(digitos.substring(0, Math.min(2, digitos.length())));
	            }
	            if (telefone.length() >= 2) {
	                telefone.append(") ");
	                
	                if (digitos.length() >= 7) {
	                    if (digitos.length() == 11) {
	                        telefone.append(digitos.substring(2, 7));
	                        telefone.append("-");
	                        telefone.append(digitos.substring(7));
	                    } 
	                    else { 
	                        telefone.append(digitos.substring(2, 6));
	                        telefone.append("-");
	                        telefone.append(digitos.substring(6));
	                    }
	                } 
	                else if (digitos.length() > 2) {
	                    telefone.append(digitos.substring(2));
	                }
	            }

	            if (!telefone.toString().equals(newValue)) {
	                txtTelefone.setText(telefone.toString());
	                txtTelefone.positionCaret(telefone.length());
	            }
			}
		});
		
		txtMotivoInternacao.textProperty().addListener((obs, oldValue, newValue) -> {
			if (motivoOncologicoVazio && !newValue.isEmpty()) {
				txtMotivoInternacao.setStyle("-fx-background-color: #fffff; -fx-background-radius: 8px");
				motivoOncologicoVazio = false;
			}
		});
		
		txtMotivoInternacao.setTextFormatter(new TextFormatter<String>(change -> {
			if (change.getControlNewText().length() > 20) {
				return null;
			}
			
			return change;
		}));
		
		txtObservacoes.setTextFormatter(new TextFormatter<String>(change -> {
			if (change.getControlText().length() > 300) {
				return null;
			}
			
			return change;
		}));
	}
	
	@FXML
	private void voltar(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPerfilPaciente.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		PerfilPacienteController pP = loader.getController();
		pP.carregarPaciente(p);
		pP.carregarUsuario(e);
		
		if (veioDaTelaPrincipal) pP.origemTelaPrincipal();
		if (veioDaTelaPacientes) pP.origemTelaPacientes();
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Prontuário Digital - Informações de " + p.getNome() + " " + p.getSobrenome());
		stage.setScene(scene);
	}
	
	@FXML
	private void eliminarRiscoLesaoPele(ActionEvent event) {
		if (ckbForaRiscoPele.isSelected()) {
			ckbForaRiscoFlebite.setSelected(true);
			ckbForaRiscoQueda.setSelected(true);
		}
		else {
			ckbForaRiscoFlebite.setSelected(false);
			ckbForaRiscoQueda.setSelected(false);
		}
		
		configurarForaRiscoPele();
		configurarForaRiscoFlebite();
		configurarForaRiscoQueda();
	}
	
	private void configurarForaRiscoPele() {
		if (ckbForaRiscoPele.isSelected()) {
			ckbConsciencia.setSelected(false);
			ckbMobilidade.setSelected(false);
			ckbNutricional.setSelected(false);
			ckbPeleUmida.setSelected(false);
			ckbCisalhamento.setSelected(false);
			ckbLimitacoesMobilidade.setSelected(false);
			
			ckbConsciencia.setMouseTransparent(true);
			ckbMobilidade.setMouseTransparent(true);
			ckbNutricional.setMouseTransparent(true);
			ckbPeleUmida.setMouseTransparent(true);
			ckbCisalhamento.setMouseTransparent(true);
			ckbLimitacoesMobilidade.setMouseTransparent(true);
		}
		else {
			ckbConsciencia.setMouseTransparent(false);
			ckbMobilidade.setMouseTransparent(false);
			ckbNutricional.setMouseTransparent(false);
			ckbPeleUmida.setMouseTransparent(false);
			ckbCisalhamento.setMouseTransparent(false);
			ckbLimitacoesMobilidade.setMouseTransparent(false);
		}
	}
	
	@FXML
	private void eliminarRiscoFlebite(ActionEvent event) {
		if (ckbForaRiscoFlebite.isSelected()) {
			ckbForaRiscoPele.setSelected(true);
			ckbForaRiscoQueda.setSelected(true);
		}
		else {
			ckbForaRiscoPele.setSelected(false);
			ckbForaRiscoQueda.setSelected(false);
		}

		configurarForaRiscoPele();
		configurarForaRiscoFlebite();
		configurarForaRiscoQueda();
	}
	
	private void configurarForaRiscoFlebite() {
		if (ckbForaRiscoFlebite.isSelected()) {
			ckbCriancaIdoso.setSelected(false);
			ckbTurgorPele.setSelected(false);
			ckbImunodepressao.setSelected(false);
			ckbFragilidadeCapilar.setSelected(false);
			ckbQuimioterapia.setSelected(false);
			ckbMedicacoesHiperosmolares.setSelected(false);
			
			ckbCriancaIdoso.setMouseTransparent(true);
			ckbTurgorPele.setMouseTransparent(true);
			ckbImunodepressao.setMouseTransparent(true);
			ckbFragilidadeCapilar.setMouseTransparent(true);
			ckbQuimioterapia.setMouseTransparent(true);
			ckbMedicacoesHiperosmolares.setMouseTransparent(true);
		}
		else {
			ckbCriancaIdoso.setMouseTransparent(false);
			ckbTurgorPele.setMouseTransparent(false);
			ckbImunodepressao.setMouseTransparent(false);
			ckbFragilidadeCapilar.setMouseTransparent(false);
			ckbQuimioterapia.setMouseTransparent(false);
			ckbMedicacoesHiperosmolares.setMouseTransparent(false);
		}
	}
	
	@FXML
	private void eliminarRiscoQueda(ActionEvent event) {
		if (ckbForaRiscoQueda.isSelected()) {
			ckbForaRiscoPele.setSelected(true);
			ckbForaRiscoFlebite.setSelected(true);
		}
		else {
			ckbForaRiscoPele.setSelected(false);
			ckbForaRiscoFlebite.setSelected(false);
		}

		configurarForaRiscoPele();
		configurarForaRiscoFlebite();
		configurarForaRiscoQueda();
	}
	
	private void configurarForaRiscoQueda() {
		if (ckbForaRiscoQueda.isSelected()) {
			ckbCriancaIdosoGestante.setSelected(false);
			ckbConvulsoes.setSelected(false);
			ckbDelirium.setSelected(false);
			ckbVisaoAudicao.setSelected(false);
			ckbHipotensao.setSelected(false);
			ckbAlcoolDrogas.setSelected(false);
			
			ckbCriancaIdosoGestante.setMouseTransparent(true);
			ckbConvulsoes.setMouseTransparent(true);
			ckbDelirium.setMouseTransparent(true);
			ckbVisaoAudicao.setMouseTransparent(true);
			ckbHipotensao.setMouseTransparent(true);
			ckbAlcoolDrogas.setMouseTransparent(true);

			ckbForaRiscoPele.setSelected(true);
			ckbForaRiscoFlebite.setSelected(true);
		}
		else {
			ckbCriancaIdosoGestante.setMouseTransparent(false);
			ckbConvulsoes.setMouseTransparent(false);
			ckbDelirium.setMouseTransparent(false);
			ckbVisaoAudicao.setMouseTransparent(false);
			ckbHipotensao.setMouseTransparent(false);
			ckbAlcoolDrogas.setMouseTransparent(false);
		}
	}
	
	@FXML
	private void selecionarCriancaIdoso(ActionEvent event) {
		ckbCriancaIdosoGestante.setSelected(ckbCriancaIdoso.isSelected());
	}
	
	@FXML
	private void selecionarCriancaIdosoGestante(ActionEvent event) {
		ckbCriancaIdoso.setSelected(ckbCriancaIdosoGestante.isSelected());
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
	private void finalizar(ActionEvent event) {
		if (!txtMotivoInternacao.getText().isEmpty()) {
			String motivoOncologico = txtMotivoInternacao.getText();
			String observacoes = txtObservacoes.getText();
			boolean consciencia = ckbConsciencia.isSelected();
			boolean mobilidade = ckbMobilidade.isSelected();
			boolean nutricional = ckbNutricional.isSelected();
			boolean peleUmida = ckbPeleUmida.isSelected();
			boolean cisalhamento = ckbCisalhamento.isSelected();
			boolean limitacoesMobilidade = ckbLimitacoesMobilidade.isSelected();
			
			boolean turgorPele = ckbTurgorPele.isSelected();
			boolean imunodepressao = ckbImunodepressao.isSelected();
			boolean fragilidadeCapilar = ckbFragilidadeCapilar.isSelected();
			boolean quimioterapia = ckbQuimioterapia.isSelected();
			boolean medicacoesHiperosmolares = ckbMedicacoesHiperosmolares.isSelected();
			
			boolean convulsoes = ckbConvulsoes.isSelected();
			boolean delirium = ckbDelirium.isSelected();
			boolean visaoAudicao = ckbVisaoAudicao.isSelected();
			boolean hipotensao = ckbHipotensao.isSelected();
			boolean alcoolDrogas = ckbAlcoolDrogas.isSelected();
			
			boolean criancaIdosoGestante = ckbCriancaIdoso.isSelected() && ckbCriancaIdosoGestante.isSelected();
			boolean foraRisco = ckbForaRiscoPele.isSelected() && ckbForaRiscoFlebite.isSelected() && ckbForaRiscoQueda.isSelected();
			
			boolean doencasInfectocontagiosas = ckbDoencasInfectocontagiosas.isSelected();
			boolean dislipidemia = ckbDislipidemia.isSelected();
			boolean etilismo = ckbEtilismo.isSelected();
			boolean hipertensao = ckbHipertensao.isSelected();
			boolean transfusaoSanguinea = ckbTransfusaoSanguinea.isSelected();
			boolean viroseInfancia = ckbViroseInfancia.isSelected();
			boolean tabagismo = ckbTabagismo.isSelected();
			boolean neoplasia = ckbNeoplasia.isSelected();
			boolean doencaAutoimune = ckbDoencaAutoimune.isSelected();
			boolean doencaRespiratoria = ckbDoencaRespiratoria.isSelected();
			boolean doencaCardiovascular = ckbDoencaCardiovascular.isSelected();
			boolean diabetes = ckbDiabetes.isSelected();
			boolean doencaRenal = ckbDoencaRenal.isSelected();
			
			FatorRisco fr = new FatorRisco(consciencia, mobilidade, nutricional, peleUmida, cisalhamento,
					limitacoesMobilidade, criancaIdosoGestante, turgorPele, imunodepressao, fragilidadeCapilar,
					quimioterapia, medicacoesHiperosmolares, convulsoes, delirium, visaoAudicao, hipotensao,
					alcoolDrogas, foraRisco, p);
			HistoricoSaude h = new HistoricoSaude(tabagismo, neoplasia, doencaAutoimune, doencaRespiratoria,
					doencaCardiovascular, diabetes, doencaRenal, doencasInfectocontagiosas, dislipidemia, etilismo,
					hipertensao, transfusaoSanguinea, viroseInfancia, p);
			
			if (atualizar && idFatorRisco != 0) fr.setId(idFatorRisco);
			if (atualizar && idHistoricoSaude != 0) h.setId(idHistoricoSaude);
			
			ProntuarioDAO prDAO = new ProntuarioDAO();
			FatorRiscoDAO frDAO = new FatorRiscoDAO();
			HistoricoSaudeDAO hDAO = new HistoricoSaudeDAO();
			
			if (!atualizar) {
				frDAO.save(fr);
				hDAO.save(h);
			} 
			else {
				frDAO.update(fr);
				hDAO.update(h);
			}
			Prontuario pr = new Prontuario(observacoes, motivoOncologico, e, frDAO.findByPaciente(p),
					hDAO.findByPaciente(p));
			if (atualizar && idModificado != 0) pr.setId(idModificado);
			
			if (!atualizar) {
				prDAO.save(pr);
			} 
			else {
				prDAO.update(pr);
			}
			
			gerarProntuario(pr, event);
		}
		else {
			motivoOncologicoVazio = txtMotivoInternacao.getText().isEmpty();
			if (motivoOncologicoVazio) txtMotivoInternacao.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px; -fx-border-color: #f3673a; -fx-border-radius: 8px; -fx-border-width: 2px");
			painelDados.setVvalue(0.25);
			avisoMotivoOncologicoVazio.setVisible(true);
		}
	}
	
	private void gerarProntuario(Prontuario pr, ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		fc.setInitialFileName("Prontuário Digital - " + p.getNome() + " " + p.getSobrenome());
		File f = fc.showSaveDialog(new Stage());
		
		if (f != null) {
			try {
				PdfWriter writer = new PdfWriter(f.getAbsolutePath());
				PdfDocument pdf = new PdfDocument(writer);
				Document doc = new Document(pdf);
				
				InputStream arialIs = getClass().getResourceAsStream("/fonts/arial.ttf"); 
				InputStream arialBoldIs = getClass().getResourceAsStream("/fonts/arialbd.ttf");
				InputStream arialItalicIs = getClass().getResourceAsStream("/fonts/ariali.ttf");
				
				PdfFont arial = PdfFontFactory.createFont(IOUtils.toByteArray(arialIs), PdfEncodings.WINANSI, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
				PdfFont arialBold = PdfFontFactory.createFont(IOUtils.toByteArray(arialBoldIs), PdfEncodings.WINANSI, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
				PdfFont arialItalic = PdfFontFactory.createFont(IOUtils.toByteArray(arialItalicIs), PdfEncodings.WINANSI, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
				
				Paragraph titulo = new Paragraph("Prontuário do Paciente").setFontSize(14)
												.setTextAlignment(TextAlignment.CENTER).setFont(arialBold);
				
				doc.add(titulo);
				doc.add(new Paragraph(""));
				
				float[] colunas = {150f, 250f, 150f, 250f};
				Table tabelaPaciente = new Table(colunas);
				tabelaPaciente.setWidth(UnitValue.createPercentValue(100));
				
				Cell cpfLabel =  new Cell(1, 1).add(new Paragraph("CPF do paciente:").setFontSize(11).setFont(arial).setVerticalAlignment(VerticalAlignment.MIDDLE));
				tabelaPaciente.addCell(cpfLabel);
				
				Cell conteudoCpf = new Cell(1, 1).add(new Paragraph(formatarCpf(p.getCpf())).setFontSize(11).setFont(arial).setVerticalAlignment(VerticalAlignment.MIDDLE));
				tabelaPaciente.addCell(conteudoCpf);
				
				Cell nomeLabel = new Cell(1, 1).add(new Paragraph("Nome do paciente:").setFontSize(11).setFont(arial).setVerticalAlignment(VerticalAlignment.MIDDLE));
				tabelaPaciente.addCell(nomeLabel);
				
				Cell conteudoNome = new Cell(1, 1).add(new Paragraph(p.getNome() + " " + p.getSobrenome()).setFontSize(11).setVerticalAlignment(VerticalAlignment.MIDDLE));
				tabelaPaciente.addCell(conteudoNome);
				
				Cell labelInternacao = new Cell(1, 1).add(new Paragraph("Motivo da internação:").setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(labelInternacao);
				
				Cell conteudoInternacao = new Cell(1, 3).add(new Paragraph(pr.getMotivoOncologico()).setFontSize(11).setFont(arial).setVerticalAlignment(VerticalAlignment.MIDDLE));
				tabelaPaciente.addCell(conteudoInternacao);
				
				Cell labelEndereco = new Cell(1, 1).add(new Paragraph("Endereço:").setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(labelEndereco);
				
				Cell conteudoEndereco = new Cell(1, 1).add(new Paragraph(p.getEndereco()).setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(conteudoEndereco);
				
				Cell labelProfissao = new Cell(1, 1).add(new Paragraph("Profissão:").setFontSize(11).setFont(arial).setVerticalAlignment(VerticalAlignment.MIDDLE));
				tabelaPaciente.addCell(labelProfissao);
				
				Cell conteudoProfissao = new Cell(1, 1).add(new Paragraph(txtProfissao.getText()).setFontSize(11).setFont(arial).setVerticalAlignment(VerticalAlignment.MIDDLE));
				tabelaPaciente.addCell(conteudoProfissao);
				
				Cell labelIdade = new Cell(1, 1).add(new Paragraph("Idade:").setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(labelIdade);
				
				String idade = Integer.toString(Period.between(p.getDataNascimento(), LocalDate.now()).getYears());
				Cell conteudoIdade = new Cell(1, 1).add(new Paragraph(idade).setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(conteudoIdade);
				
				Cell labelSexo = new Cell(1, 1).add(new Paragraph("Sexo:").setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(labelSexo);
				
				Cell conteudoSexo = new Cell(1, 1).add(new Paragraph((p.getSexo() == 'F') ? "Feminino" : "Masculino").setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(conteudoSexo);
				
				Cell labelEstadoCivil = new Cell(1, 1).add(new Paragraph("Estado civil:").setFontSize(11).setFont(arial).setVerticalAlignment(VerticalAlignment.MIDDLE));
				tabelaPaciente.addCell(labelEstadoCivil);
				
				Cell conteudoEstadoCivil = new Cell(1, 1).add(new Paragraph(p.getEstadoCivil()).setFontSize(11).setFont(arial).setVerticalAlignment(VerticalAlignment.MIDDLE));
				tabelaPaciente.addCell(conteudoEstadoCivil);
				
				Cell labelMarcarRisco = new Cell(1, 1).add(new Paragraph("Fatores de risco (marque):").setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(labelMarcarRisco);
				
				Cell conteudoMarcarRisco = new Cell(1, 1);
				tabelaPaciente.addCell(conteudoMarcarRisco);
				
				FatorRisco fr = pr.getFatorRisco();
				HistoricoSaude h = pr.getHistoricoSaude();
				
				Cell labelFatorRisco = new Cell(1, 1).add(new Paragraph("Fatores de risco (especificar):").setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(labelFatorRisco);
				
				String lesaoPele = ((fr.isAlteracaoConsciencia()) ? "[X]" : "[ ]") + " Alteração no nível de consciência" + "\n" +
				                   ((fr.isDeficitMobilidade()) ? "[X]" : "[ ]") + " Déficit de mobilidade e atividade" + "\n" +
				                   ((fr.isDeficitNutricional()) ? "[X]" : "[ ]") + " Déficit nutricional" + "\n" +
				                   ((fr.isPeleUmida()) ? "[X]" : "[ ]") + " Pele úmida/molhada" + "\n" +
				                   ((fr.isCisalhamento()) ? "[X]" : "[ ]") + " Fricção/Cisalhamento" + "\n" +
				                   ((fr.isLimitacaoMobilidade()) ? "[X]" : "[ ]") + " Limitações de mobilidade" + "\n" +
				                   ((fr.isForaDeRisco()) ? "[X]" : "[ ]") + " Fora de risco" + "\n";
				
				String flebite = ((fr.isCriancaIdosoGestante()) ? "[X]" : "[ ]") + " Criança/Idoso" + "\n" +
		                         ((fr.isTurgorPele()) ? "[X]" : "[ ]") + " Alteração no turgor da pele" + "\n" +
		                         ((fr.isImunoDepressao()) ? "[X]" : "[ ]") + " Imunodepressão" + "\n" +
		                         ((fr.isFragilidadeCapilar()) ? "[X]" : "[ ]") + " Fragilidade capilar" + "\n" +
		                         ((fr.isQuimioterapia()) ? "[X]" : "[ ]") + " Quimioterapia" + "\n" +
		                         ((fr.isMedHiperosmolar()) ? "[X]" : "[ ]") + " Medicações hiperosmolares" + "\n" +
		                         ((fr.isForaDeRisco()) ? "[X]" : "[ ]") + " Fora de risco" + "\n";
				
		        
                String queda = ((fr.isCriancaIdosoGestante()) ? "[X]" : "[ ]") + " Criança/Idoso/Gestante" + "\n" +
                               ((fr.isConvulsoes()) ? "[X]" : "[ ]") + " Convulsões" + "\n" +
                               ((fr.isDelirium()) ? "[X]" : "[ ]") + " Confursão mental/delirium" + "\n" +
                               ((fr.isVisaoAudicao()) ? "[X]" : "[ ]") + " Visão/audição diminuída" + "\n" +
                               ((fr.isHipotensao()) ? "[X]" : "[ ]") + " Hipotensão postural" + "\n" +
                               ((fr.isUsoAlcoolDrogas()) ? "[X]" : "[ ]") + " Uso de álcool/drogas" + "\n" +
                               ((fr.isForaDeRisco()) ? "[X]" : "[ ]") + " Fora de risco" + "\n";
				
				Cell conteudoFatorRisco = new Cell(1, 1).add(new Paragraph("Lesão de pele:" + "\n").setFontSize(11).setFont(arialBold));
				conteudoFatorRisco.add(new Paragraph(lesaoPele).setFontSize(11).setFont(arial));
				conteudoFatorRisco.add(new Paragraph("Flebite:" + "\n").setFontSize(11).setFont(arialBold));
				conteudoFatorRisco.add(new Paragraph(flebite).setFontSize(11).setFont(arial));
				conteudoFatorRisco.add(new Paragraph("Queda:" + "\n").setFontSize(11).setFont(arialBold));
				conteudoFatorRisco.add(new Paragraph(queda).setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(conteudoFatorRisco);
				
				Cell labelHistoricoSaude = new Cell(1, 1).add(new Paragraph("Histórico de saúde (marque):").setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(labelHistoricoSaude);
				
				String historicoSaude = ((h.isTabagismo()) ? "[X]" : "[ ]") + " Tabagismo" + "\n" +
                        				((h.isNeoplasia()) ? "[X]" : "[ ]") + " Neoplasia" + "\n" +
                        				((h.isDoencaAutoimune()) ? "[X]" : "[ ]") + " Doença autoimune" + "\n" +
                        				((h.isDoencaRespiratoria()) ? "[X]" : "[ ]") + " Doença respiratória" + "\n" +
                        				((h.isDoencaCardiovascular()) ? "[X]" : "[ ]") + " Doença cardiovascular" + "\n" +
                        				((h.isDiabetes()) ? "[X]" : "[ ]") + " Diabetes" + "\n" +
                        				((h.isDoencaRenal()) ? "[X]" : "[ ]") + " Doença renal" + "\n" +
                        				((h.isDoencasInfectocontagiosas()) ? "[X]" : "[ ]") + " Doenças infectocontagiosas" + "\n" +
                        				((h.isDislipidemia()) ? "[X]" : "[ ]") + " Dislipidemia" + "\n" +
                        				((h.isEtilismo()) ? "[X]" : "[ ]") + " Etilismo" + "\n" +
                        				((h.isViroseInfancia()) ? "[X]" : "[ ]") + " Virose na infância" + "\n";
				
				Cell conteudoHistoricoSaude = new Cell(1, 1).add(new Paragraph(historicoSaude).setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(conteudoHistoricoSaude);
				
				Cell labelAlergias = new Cell(1, 1).add(new Paragraph("Alergias:").setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(labelAlergias);
				
				Cell conteudoAlergias = new Cell(1, 3).add(new Paragraph(txtAlergias.getText()).setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(conteudoAlergias);
				
				Cell labelTelefone = new Cell(1, 1).add(new Paragraph("Contato de emergência (telefone):").setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(labelTelefone);
				
				Cell conteudoTelefone = new Cell(1, 3).add(new Paragraph(txtTelefone.getText()).setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(conteudoTelefone);
				
				Cell labelObservacoes = new Cell(1, 1).add(new Paragraph("Observações:").setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(labelObservacoes);
				
				Cell conteudoObservacoes = new Cell(1, 3).add(new Paragraph(pr.getObservacao()).setFontSize(11).setFont(arial));
				tabelaPaciente.addCell(conteudoObservacoes);
				
				doc.add(tabelaPaciente);
				
				doc.add(new Paragraph("\nAnotações do enfermeiro(a):").setFont(arialBold).setFontSize(11));
				doc.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n"));
				
				float[] colunasAssinatura = {150f, 150f};
		        Table tabelaAssinatura = new Table(colunasAssinatura);
		        tabelaAssinatura.setWidth(UnitValue.createPercentValue(100));
		        
		        Cell labelCarimbo = new Cell(1, 1).add(new Paragraph("Carimbo do enfermeiro:").setFontSize(11).setFont(arial)).setHeight(60);
		        tabelaAssinatura.addCell(labelCarimbo);
		        
		        Cell carimbo = new Cell(1, 1).setHeight(60);
		        tabelaAssinatura.addCell(carimbo);
		        
		        Cell labelAssinatura = new Cell(1, 1).add(new Paragraph("Assinatura do enfermeiro:").setFontSize(11).setFont(arial));
		        tabelaAssinatura.addCell(labelAssinatura);
		        
		        Cell assinatura = new Cell(1, 1).setHeight(40);
		        tabelaAssinatura.addCell(assinatura);
		        
		        doc.add(tabelaAssinatura);
				
				LocalDateTime dataEmissao = pr.getDataEmissao();
		        DateTimeFormatter formatData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		        DateTimeFormatter formatHora = DateTimeFormatter.ofPattern("HH:mm");
		        
		        String dataFormatada = dataEmissao.format(formatData);
		        String horaFormatada = dataEmissao.format(formatHora);
		        
		        doc.add(new Paragraph("\nData: " + dataFormatada + " Hora: " + horaFormatada).setFontSize(11).setFont(arial));
		        doc.add(new Paragraph("Observação: Preencher todos os campos obrigatórios. Este prontuário é confidencial.")
		        		.setFontSize(9).setFont(arialItalic));

				doc.close();
				carregarPerfil(event);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			avisoCaminhoProntuario.setVisible(true);
		}
	}
	
	@FXML
	private void hoverOkMotivoOncologicoVazio(MouseEvent event) {
		btnOkMotivoOncologicoVazio.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitOkMotivoOncologicoVazio(MouseEvent event) {
		btnOkMotivoOncologicoVazio.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML
	private void ocultarMotivoOncologicoVazio(ActionEvent event) {
		avisoMotivoOncologicoVazio.setVisible(false);
	}
	
	@FXML
	private void hoverOkCaminhoProntuario(MouseEvent event) {
		btnOkCaminhoProntuario.setStyle("-fx-background-color: #297373; -fx-background-radius: 5px");
	}
	
	@FXML
	private void exitOkCaminhoProntuario(MouseEvent event) {
		btnOkCaminhoProntuario.setStyle("-fx-background-color: #4c9292; -fx-background-radius: 5px");
	}
	
	@FXML
	private void ocultarCaminhoProntuario(ActionEvent event) {
		avisoCaminhoProntuario.setVisible(false);
	}
	
	private void carregarPerfil(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPerfilPaciente.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		PerfilPacienteController pP = loader.getController();
		pP.carregarPaciente(p);
		pP.carregarUsuario(e);
		
		if (!atualizar) pP.notificarGeracaoProntuario();
		if (atualizar) pP.notificarModificacaoProntuario();
		
		if (veioDaTelaPrincipal) pP.origemTelaPrincipal();
		if (veioDaTelaPacientes) pP.origemTelaPacientes();
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Prontuário Digital - Informações de " + p.getNome() + " " + p.getSobrenome());
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
	
	public void carregarUsuario(Enfermeiro e) {
		this.e = e;
	}
	
	public void carregarInformacoesPaciente(Paciente p) {
		this.p = p;
		
		txtNome.setText(this.p.getNome());
		txtSobrenome.setText(this.p.getSobrenome());
		txtCPF.setText(formatarCpf(this.p.getCpf()));
		if (Character.valueOf(this.p.getSexo()) != null) cbSexo.getSelectionModel().select((this.p.getSexo() == 'F') ? 0 : 1);
		if (this.p.getPeso() != null) spPeso.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100.0, (double) this.p.getPeso(), 0.1));
		if (this.p.getEstadoCivil() != null || !this.p.getEstadoCivil().isEmpty()) cbEstadoCivil.getSelectionModel().select(obterEstadoCivil(this.p.getEstadoCivil()));
		if (this.p.getDataNascimento() != null) dpNasc.setValue(this.p.getDataNascimento());
		if (this.p.getDataEntrada() != null) dpEntrada.setValue(this.p.getDataEntrada().toLocalDate());
		if (this.p.getDataSaida() != null) dpSaida.setValue(this.p.getDataSaida().toLocalDate());
		txtEndereco.setText(this.p.getEndereco());
		
		txtNome.setEditable(false);
		txtSobrenome.setEditable(false);
		txtCPF.setEditable(false);
		cbSexo.setMouseTransparent(true);
		spPeso.setMouseTransparent(true);
		cbEstadoCivil.setMouseTransparent(true);
		dpNasc.setMouseTransparent(true);
		dpEntrada.setMouseTransparent(true);
		dpSaida.setMouseTransparent(true);
		txtEndereco.setEditable(false);
	}
	
	public void iniciarModificacao(Prontuario pr) {
		modificado = pr;
		atualizar = true;
		
		idModificado = modificado.getId();
		FatorRisco fr = pr.getFatorRisco();
		HistoricoSaude h = pr.getHistoricoSaude();
		idFatorRisco = fr.getId();
		idHistoricoSaude = h.getId();
		
		ckbConsciencia.setSelected(fr.isAlteracaoConsciencia());
		ckbMobilidade.setSelected(fr.isDeficitMobilidade());
		ckbNutricional.setSelected(fr.isDeficitNutricional());
		ckbPeleUmida.setSelected(fr.isPeleUmida());
		ckbCisalhamento.setSelected(fr.isCisalhamento());
		ckbLimitacoesMobilidade.setSelected(fr.isLimitacaoMobilidade());
		
		ckbCriancaIdoso.setSelected(fr.isCriancaIdosoGestante());
		ckbTurgorPele.setSelected(fr.isTurgorPele());
		ckbImunodepressao.setSelected(fr.isImunoDepressao());
		ckbFragilidadeCapilar.setSelected(fr.isFragilidadeCapilar());
		ckbQuimioterapia.setSelected(fr.isQuimioterapia());
		ckbMedicacoesHiperosmolares.setSelected(fr.isMedHiperosmolar());
		
		ckbCriancaIdosoGestante.setSelected(fr.isCriancaIdosoGestante());
		ckbConvulsoes.setSelected(fr.isConvulsoes());
		ckbDelirium.setSelected(fr.isDelirium());
		ckbVisaoAudicao.setSelected(fr.isVisaoAudicao());
		ckbHipotensao.setSelected(fr.isHipotensao());
		ckbAlcoolDrogas.setSelected(fr.isUsoAlcoolDrogas());
		
		ckbDoencasInfectocontagiosas.setSelected(h.isDoencasInfectocontagiosas());
		ckbDislipidemia.setSelected(h.isDislipidemia());
		ckbEtilismo.setSelected(h.isEtilismo());
		ckbHipertensao.setSelected(h.isHipertensao());
		ckbTransfusaoSanguinea.setSelected(h.isTransfusaoSanguinea());
		ckbViroseInfancia.setSelected(h.isViroseInfancia());
		ckbTabagismo.setSelected(h.isTabagismo());
		ckbNeoplasia.setSelected(h.isNeoplasia());;
		ckbDoencaAutoimune.setSelected(h.isDoencaAutoimune());
		ckbDoencaRespiratoria.setSelected(h.isDoencaRespiratoria());;
		ckbDoencaCardiovascular.setSelected(h.isDoencaCardiovascular());
		ckbDiabetes.setSelected(h.isDiabetes());
		ckbDoencaRenal.setSelected(h.isDoencaRenal());
		
		ckbForaRiscoPele.setSelected(fr.isForaDeRisco());
		ckbForaRiscoFlebite.setSelected(fr.isForaDeRisco());
		ckbForaRiscoQueda.setSelected(fr.isForaDeRisco());
		
		txtMotivoInternacao.setText(modificado.getMotivoOncologico());
		txtObservacoes.setText(modificado.getObservacao());
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
	
	public void definirOrigem(boolean principal, boolean pacientes) {
		this.veioDaTelaPrincipal = principal;
		this.veioDaTelaPacientes = pacientes;
	}
	
	private void fechar(Button source) {
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
}