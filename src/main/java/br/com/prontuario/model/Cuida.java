package br.com.prontuario.model;

public class Cuida {
    private Enfermeiro enfermeiro;
    private Paciente paciente;

    public Cuida() {

    }

    public Cuida(Enfermeiro enfermeiro, Paciente paciente) {
        this.enfermeiro = enfermeiro;
        this.paciente = paciente;
    }

    public Enfermeiro getEnfermeiro() {
        return enfermeiro;
    }

    public void setEnfermeiro(Enfermeiro enfermeiro) {
        this.enfermeiro = enfermeiro;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "Cuida{" +
                "enfermeiro=" + enfermeiro.getCoren() +
                ", paciente=" + paciente.getCpf() +
                '}';
    }
}
