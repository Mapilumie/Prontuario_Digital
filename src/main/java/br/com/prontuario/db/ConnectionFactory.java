package br.com.prontuario.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String USERNAME = "postgres";

    private static final String PASSWORD = "gamerben10";

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/prontuario";

    public Connection getConnection() throws SQLException {
        Connection conexao = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        return conexao;
    }
}
