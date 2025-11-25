package br.com.prontuario.model;

public class HistoricoSaude {
    private Long id;

    private boolean tabagismo;
    private boolean neoplasia;
    private boolean doencaAutoimune;
    private boolean doencaRespiratoria;
    private boolean doencaCardiovascular;
    private boolean diabetes;
    private boolean doencaRenal;
    private boolean doencasInfectocontagiosas;
    private boolean dislipidemia;
    private boolean etilismo;
    private boolean hipertensao;
    private boolean transfusaoSanguinea;
    private boolean viroseInfancia;

    private Paciente paciente;

    public HistoricoSaude() {

    }

    public HistoricoSaude(boolean tabagismo, boolean neoplasia, boolean doencaAutoimune, boolean doencaRespiratoria, boolean doencaCardiovascular, boolean diabetes, boolean doencaRenal, boolean doencasInfectocontagiosas, boolean dislipidemia, boolean etilismo, boolean hipertensao, boolean transfusaoSanguinea, boolean viroseInfancia, Paciente paciente) {
        this.tabagismo = tabagismo;
        this.neoplasia = neoplasia;
        this.doencaAutoimune = doencaAutoimune;
        this.doencaRespiratoria = doencaRespiratoria;
        this.doencaCardiovascular = doencaCardiovascular;
        this.diabetes = diabetes;
        this.doencaRenal = doencaRenal;
        this.doencasInfectocontagiosas = doencasInfectocontagiosas;
        this.dislipidemia = dislipidemia;
        this.etilismo = etilismo;
        this.hipertensao = hipertensao;
        this.transfusaoSanguinea = transfusaoSanguinea;
        this.viroseInfancia = viroseInfancia;
        this.paciente = paciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isTabagismo() {
        return tabagismo;
    }

    public void setTabagismo(boolean tabagismo) {
        this.tabagismo = tabagismo;
    }

    public boolean isNeoplasia() {
        return neoplasia;
    }

    public void setNeoplasia(boolean neoplasia) {
        this.neoplasia = neoplasia;
    }

    public boolean isDoencaAutoimune() {
        return doencaAutoimune;
    }

    public void setDoencaAutoimune(boolean doencaAutoimune) {
        this.doencaAutoimune = doencaAutoimune;
    }

    public boolean isDoencaRespiratoria() {
        return doencaRespiratoria;
    }

    public void setDoencaRespiratoria(boolean doencaRespiratoria) {
        this.doencaRespiratoria = doencaRespiratoria;
    }

    public boolean isDoencaCardiovascular() {
        return doencaCardiovascular;
    }

    public void setDoencaCardiovascular(boolean doencaCardiovascular) {
        this.doencaCardiovascular = doencaCardiovascular;
    }

    public boolean isDiabetes() {
        return diabetes;
    }

    public void setDiabetes(boolean diabetes) {
        this.diabetes = diabetes;
    }

    public boolean isDoencaRenal() {
        return doencaRenal;
    }

    public void setDoencaRenal(boolean doencaRenal) {
        this.doencaRenal = doencaRenal;
    }

    public boolean isDoencasInfectocontagiosas() {
        return doencasInfectocontagiosas;
    }

    public void setDoencasInfectocontagiosas(boolean doencasInfectocontagiosas) {
        this.doencasInfectocontagiosas = doencasInfectocontagiosas;
    }

    public boolean isDislipidemia() {
        return dislipidemia;
    }

    public void setDislipidemia(boolean dislipidemia) {
        this.dislipidemia = dislipidemia;
    }

    public boolean isEtilismo() {
        return etilismo;
    }

    public void setEtilismo(boolean etilismo) {
        this.etilismo = etilismo;
    }

    public boolean isHipertensao() {
        return hipertensao;
    }

    public void setHipertensao(boolean hipertensao) {
        this.hipertensao = hipertensao;
    }

    public boolean isTransfusaoSanguinea() {
        return transfusaoSanguinea;
    }

    public void setTransfusaoSanguinea(boolean transfusaoSanguinea) {
        this.transfusaoSanguinea = transfusaoSanguinea;
    }

    public boolean isViroseInfancia() {
        return viroseInfancia;
    }

    public void setViroseInfancia(boolean viroseInfancia) {
        this.viroseInfancia = viroseInfancia;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "HistoricoSaude{" +
                "id=" + id +
                ", tabagismo=" + this.tabagismo +
                ", neoplasia=" + this.neoplasia +
                ", doencaAutoimune=" + this.doencaAutoimune +
                ", doencaRespiratoria=" + this.doencaRespiratoria +
                ", doencaCardiovascular=" + this.doencaCardiovascular +
                ", diabetes=" + this.diabetes +
                ", doencaRenal=" + this.doencaRenal +
                ", doencasInfectocontagiosas=" + this.doencasInfectocontagiosas +
                ", dislipidemia=" + this.dislipidemia +
                ", etilismo=" + this.etilismo +
                ", hipertensao=" + this.hipertensao +
                ", transfusaoSanguinea=" + this.transfusaoSanguinea +
                ", viroseInfancia=" + this.viroseInfancia +
                ", paciente=" + this.paciente.getCpf() +
                '}';
    }
}
