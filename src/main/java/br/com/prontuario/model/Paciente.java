package br.com.prontuario.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Paciente {
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_DATAHORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private String cpf;
    private String nome;
    private String sobrenome;
    private String estadoCivil;
    private String endereco;
    private char sexo;
    private Double peso;
    private LocalDate dataNascimento;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

    public Paciente() {
        this.dataEntrada = LocalDateTime.now();
        this.dataSaida = null;
    }

    public Paciente(String cpf, String nome, String sobrenome, String estadoCivil, String endereco, char sexo, Double peso, LocalDate dataNascimento) {
        this.cpf = cpf;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.estadoCivil = estadoCivil;
        this.endereco = endereco;
        this.sexo = sexo;
        this.peso = peso;
        this.dataNascimento = dataNascimento;
        this.dataEntrada = LocalDateTime.now();
        this.dataSaida = null;
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

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }
    
    public final DateTimeFormatter getFormatoData() {
    	return FORMATO_DATA;
    }
    
    public final DateTimeFormatter getFormatoDataHora() {
    	return FORMATO_DATAHORA;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "cpf='" + this.cpf + '\'' +
                ", nome='" + this.nome + '\'' +
                ", sobrenome='" + this.sobrenome + '\'' +
                ", estadoCivil='" + this.estadoCivil + '\'' +
                ", endereco='" + this.endereco + '\'' +
                ", sexo='" + this.sexo + '\'' +
                ", peso=" + this.peso +
                ", dataNascimento=" + this.dataNascimento.format(FORMATO_DATA) +
                ", dataEntrada=" + this.dataEntrada.format(FORMATO_DATAHORA) +
                ", dataSaida=" + (this.dataSaida != null ? this.dataSaida.format(FORMATO_DATAHORA) : "null") +
                '}';
    }
}
