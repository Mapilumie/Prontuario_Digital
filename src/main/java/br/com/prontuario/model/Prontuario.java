package br.com.prontuario.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Prontuario {

    private static final DateTimeFormatter FORMATO_DATAHORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private Long id;
    private String observacao;
    private String motivoOncologico;
    private LocalDateTime dataEmissao;

    private Enfermeiro enfermeiro;
    private FatorRisco fatorRisco;
    private HistoricoSaude historicoSaude;

    public Prontuario() {
        this.dataEmissao = LocalDateTime.now();
    }

    public Prontuario(String observacao, String motivoOncologico, Enfermeiro enfermeiro, FatorRisco fatorRisco, HistoricoSaude historicoSaude) {
        this.observacao = observacao;
        this.motivoOncologico = motivoOncologico;
        this.dataEmissao = LocalDateTime.now();
        this.enfermeiro = enfermeiro;
        this.fatorRisco = fatorRisco;
        this.historicoSaude = historicoSaude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getMotivoOncologico() {
        return motivoOncologico;
    }

    public void setMotivoOncologico(String motivoOncologico) {
        this.motivoOncologico = motivoOncologico;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Enfermeiro getEnfermeiro() {
        return enfermeiro;
    }

    public void setEnfermeiro(Enfermeiro enfermeiro) {
        this.enfermeiro = enfermeiro;
    }

    public FatorRisco getFatorRisco() {
        return fatorRisco;
    }

    public void setFatorRisco(FatorRisco fatorRisco) {
        this.fatorRisco = fatorRisco;
    }

    public HistoricoSaude getHistoricoSaude() {
        return historicoSaude;
    }

    public void setHistoricoSaude(HistoricoSaude historicoSaude) {
        this.historicoSaude = historicoSaude;
    }

    @Override
    public String toString() {
        return "Prontuario{" +
                "id=" + this.id +
                ", observacao='" + this.observacao + '\'' +
                ", motivoOncologico='" + this.motivoOncologico + '\'' +
                ", dataEmissao=" + this.dataEmissao.format(FORMATO_DATAHORA) +
                ", enfermeiro=" + this.enfermeiro.getCoren() +
                ", fatorRisco=" + this.fatorRisco.getId() +
                ", historicoSaude=" + this.historicoSaude.getId() +
                '}';
    }
}
