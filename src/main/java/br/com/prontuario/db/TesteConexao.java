package br.com.prontuario.db;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            System.out.println("Conexão estabelecida com sucesso!");
            conexao.close();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
}