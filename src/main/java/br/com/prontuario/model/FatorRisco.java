package br.com.prontuario.model;

public class FatorRisco {
    private Long id;

    // Lesão de pele
    private boolean alteracaoConsciencia;
    private boolean deficitMobilidade;
    private boolean deficitNutricional;
    private boolean peleUmida;
    private boolean cisalhamento;
    private boolean limitacaoMobilidade;

    // Flebite
    private boolean criancaIdosoGestante;
    private boolean turgorPele;
    private boolean imunoDepressao;
    private boolean fragilidadeCapilar;
    private boolean quimioterapia;
    private boolean medHiperosmolar;

    // Queda
    private boolean convulsoes;
    private boolean delirium;
    private boolean visaoAudicao;
    private boolean hipotensao;
    private boolean usoAlcoolDrogas;

    // Não há riscos
    private boolean foraDeRisco;

    private Paciente paciente;

    public FatorRisco() {

    }

    public FatorRisco(boolean alteracaoConsciencia, boolean deficitMobilidade, boolean deficitNutricional, boolean peleUmida, boolean cisalhamento, boolean limitacaoMobilidade, boolean criancaIdosoGestante, boolean turgorPele, boolean imunoDepressao, boolean fragilidadeCapilar, boolean quimioterapia, boolean medHiperosmolar, boolean convulsoes, boolean delirium, boolean visaoAudicao, boolean hipotensao, boolean usoAlcoolDrogas, boolean foraDeRisco, Paciente paciente) {
        this.alteracaoConsciencia = alteracaoConsciencia;
        this.deficitMobilidade = deficitMobilidade;
        this.deficitNutricional = deficitNutricional;
        this.peleUmida = peleUmida;
        this.cisalhamento = cisalhamento;
        this.limitacaoMobilidade = limitacaoMobilidade;
        this.criancaIdosoGestante = criancaIdosoGestante;
        this.turgorPele = turgorPele;
        this.imunoDepressao = imunoDepressao;
        this.fragilidadeCapilar = fragilidadeCapilar;
        this.quimioterapia = quimioterapia;
        this.medHiperosmolar = medHiperosmolar;
        this.convulsoes = convulsoes;
        this.delirium = delirium;
        this.visaoAudicao = visaoAudicao;
        this.hipotensao = hipotensao;
        this.usoAlcoolDrogas = usoAlcoolDrogas;
        this.foraDeRisco = foraDeRisco;
        this.paciente = paciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAlteracaoConsciencia() {
        return alteracaoConsciencia;
    }

    public void setAlteracaoConsciencia(boolean alteracaoConsciencia) {
        this.alteracaoConsciencia = alteracaoConsciencia;
    }

    public boolean isDeficitMobilidade() {
        return deficitMobilidade;
    }

    public void setDeficitMobilidade(boolean deficitMobilidade) {
        this.deficitMobilidade = deficitMobilidade;
    }

    public boolean isDeficitNutricional() {
        return deficitNutricional;
    }

    public void setDeficitNutricional(boolean deficitNutricional) {
        this.deficitNutricional = deficitNutricional;
    }

    public boolean isPeleUmida() {
        return peleUmida;
    }

    public void setPeleUmida(boolean peleUmida) {
        this.peleUmida = peleUmida;
    }

    public boolean isCisalhamento() {
        return cisalhamento;
    }

    public void setCisalhamento(boolean cisalhamento) {
        this.cisalhamento = cisalhamento;
    }

    public boolean isLimitacaoMobilidade() {
        return limitacaoMobilidade;
    }

    public void setLimitacaoMobilidade(boolean limitacaoMobilidade) {
        this.limitacaoMobilidade = limitacaoMobilidade;
    }

    public boolean isCriancaIdosoGestante() {
        return criancaIdosoGestante;
    }

    public void setCriancaIdosoGestante(boolean criancaIdosoGestante) {
        this.criancaIdosoGestante = criancaIdosoGestante;
    }

    public boolean isTurgorPele() {
        return turgorPele;
    }

    public void setTurgorPele(boolean turgorPele) {
        this.turgorPele = turgorPele;
    }

    public boolean isImunoDepressao() {
        return imunoDepressao;
    }

    public void setImunoDepressao(boolean imunoDepressao) {
        this.imunoDepressao = imunoDepressao;
    }

    public boolean isFragilidadeCapilar() {
        return fragilidadeCapilar;
    }

    public void setFragilidadeCapilar(boolean fragilidadeCapilar) {
        this.fragilidadeCapilar = fragilidadeCapilar;
    }

    public boolean isQuimioterapia() {
        return quimioterapia;
    }

    public void setQuimioterapia(boolean quimioterapia) {
        this.quimioterapia = quimioterapia;
    }

    public boolean isMedHiperosmolar() {
        return medHiperosmolar;
    }

    public void setMedHiperosmolar(boolean medHiperosmolar) {
        this.medHiperosmolar = medHiperosmolar;
    }

    public boolean isConvulsoes() {
        return convulsoes;
    }

    public void setConvulsoes(boolean convulsoes) {
        this.convulsoes = convulsoes;
    }

    public boolean isDelirium() {
        return delirium;
    }

    public void setDelirium(boolean delirium) {
        this.delirium = delirium;
    }

    public boolean isVisaoAudicao() {
        return visaoAudicao;
    }

    public void setVisaoAudicao(boolean visaoAudicao) {
        this.visaoAudicao = visaoAudicao;
    }

    public boolean isHipotensao() {
        return hipotensao;
    }

    public void setHipotensao(boolean hipotensao) {
        this.hipotensao = hipotensao;
    }

    public boolean isUsoAlcoolDrogas() {
        return usoAlcoolDrogas;
    }

    public void setUsoAlcoolDrogas(boolean usoAlcoolDrogas) {
        this.usoAlcoolDrogas = usoAlcoolDrogas;
    }

    public boolean isForaDeRisco() {
        return foraDeRisco;
    }

    public void setForaDeRisco(boolean foraDeRisco) {
        this.foraDeRisco = foraDeRisco;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "FatorRisco{" +
                "id=" + this.id +
                ", alteracaoConsciencia=" + this.alteracaoConsciencia +
                ", deficitMobilidade=" + this.deficitMobilidade +
                ", deficitNutricional=" + this.deficitNutricional +
                ", peleUmida=" + this.peleUmida +
                ", cisalhamento=" + this.cisalhamento +
                ", limitacaoMobilidade=" + this.limitacaoMobilidade +
                ", criancaIdosoGestante=" + this.criancaIdosoGestante +
                ", turgorPele=" + this.turgorPele +
                ", imunoDepressao=" + this.imunoDepressao +
                ", fragilidadeCapilar=" + this.fragilidadeCapilar +
                ", quimioterapia=" + this.quimioterapia +
                ", medHiperosmolar=" + this.medHiperosmolar +
                ", convulsoes=" + this.convulsoes +
                ", delirium=" + this.delirium +
                ", visaoAudicao=" + this.visaoAudicao +
                ", hipotensao=" + this.hipotensao +
                ", usoAlcoolDrogas=" + this.usoAlcoolDrogas +
                ", foraDeRisco=" + this.foraDeRisco +
                ", paciente=" + this.paciente.getCpf() +
                '}';
    }
}
