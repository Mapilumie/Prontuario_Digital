package br.com.prontuario.model;

public class Acompanhante {
    private String cpf;
    private String nome;
    private String relacao;
    private Paciente paciente;

    public Acompanhante() {

    }

    public Acompanhante(String cpf, String nome, String relacao, Paciente paciente) {
        this.cpf = cpf;
        this.nome = nome;
        this.relacao = relacao;
        this.paciente = paciente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRelacao() {
        return relacao;
    }

    public void setRelacao(String relacao) {
        this.relacao = relacao;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "Acompanhante{" +
                "cpf='" + this.cpf + '\'' +
                ", nome='" + this.nome + '\'' +
                ", relacao='" + this.relacao + '\'' +
                ", paciente=" + this.paciente.getCpf() +
                '}';
    }
}
