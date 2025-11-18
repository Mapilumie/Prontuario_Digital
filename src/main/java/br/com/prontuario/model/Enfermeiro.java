package br.com.prontuario.model;

public class Enfermeiro {
    private String coren;
    private String nome;
    private String email;
    private String senha;

    public Enfermeiro() {

    }

    public Enfermeiro(String coren, String nome, String email, String senha) {
        this.coren = coren;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getCoren() {
        return coren;
    }

    public void setCoren(String coren) {
        this.coren = coren;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Enfermeiro{" +
                "coren='" + this.coren + '\'' +
                ", nome='" + this.nome + '\'' +
                ", email='" + this.email + '\'' +
                ", senha='" + this.senha + '\'' +
                '}';
    }
}
