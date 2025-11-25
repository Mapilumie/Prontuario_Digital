package br.com.prontuario.dao;

import br.com.prontuario.db.ConnectionFactory;
import br.com.prontuario.model.Cuida;
import br.com.prontuario.model.Enfermeiro;
import br.com.prontuario.model.Paciente;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class CuidaDAO implements ICuidaDAO {
    // Puxa o fuso horário da Bahia
    private static final ZoneId FUSO_HORARIO = ZoneId.of("America/Bahia");

    public void save(Cuida cuida) {
        String sql = "INSERT INTO cuida (COREN, CPF_Paciente) VALUES (?,?)";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cuida.getEnfermeiro().getCoren());
            pst.setString(2, cuida.getPaciente().getCpf());
            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar cuida: " + e.getMessage());
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }

            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar Connection: " + e.getMessage());
                }
            }
        }
    }

    // Busca pacientes de um determinado enfermeiro
    public List<Paciente> findPacienteByEnfermeiro(Enfermeiro enfermeiro) {
        String sql = "SELECT p.* FROM paciente p INNER JOIN cuida c ON p.CPF_Paciente=c.CPF_Paciente WHERE c.COREN=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        List<Paciente> pacientes = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, enfermeiro.getCoren());
            rs = pst.executeQuery();
            if (rs != null) {
                pacientes = new ArrayList<Paciente>();
                while (rs.next()) {
                    Paciente paciente = new Paciente();
                    paciente.setCpf(rs.getString("CPF_Paciente"));
                    paciente.setNome(rs.getString("nome"));
                    paciente.setSobrenome(rs.getString("sobrenome"));
                    paciente.setEstadoCivil(rs.getString("estadoCivil"));
                    paciente.setEndereco(rs.getString("endereco"));
                    paciente.setSexo(rs.getString("sexo").charAt(0));
                    paciente.setPeso(rs.getDouble("peso"));
                    paciente.setDataNascimento(rs.getObject("dataNascimento", LocalDate.class));

                    ZonedDateTime entradaFusoHorario = rs.getTimestamp("dataEntrada").toInstant().atZone(FUSO_HORARIO);
                    paciente.setDataEntrada(entradaFusoHorario.toLocalDateTime());

                    Timestamp timeStampSaida = rs.getTimestamp("dataSaida");
                    if (timeStampSaida != null) {
                        ZonedDateTime saidaFusoHorario = timeStampSaida.toInstant().atZone(FUSO_HORARIO);
                        paciente.setDataSaida(saidaFusoHorario.toLocalDateTime());
                    } else {
                        paciente.setDataSaida(null);
                    }

                    pacientes.add(paciente);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pacientes do enfermeiro \"" + enfermeiro.getCoren() + "\": " + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
                }
            }

            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }

            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar Connection: " + e.getMessage());
                }
            }
        }
        return pacientes;
    }

    // Busca enfermeiros de um determinado paciente
    public List<Enfermeiro> findEnfermeiroByPaciente(Paciente paciente) {
        String sql = "SELECT e.* FROM enfermeiro e INNER JOIN cuida c ON e.COREN=c.COREN WHERE c.CPF_PACIENTE=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        List<Enfermeiro> enfermeiros = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, paciente.getCpf());
            rs = pst.executeQuery();
            if (rs != null) {
                enfermeiros = new ArrayList<Enfermeiro>();
                while (rs.next()) {
                    Enfermeiro enfermeiro = new Enfermeiro();
                    enfermeiro.setCoren(rs.getString("COREN"));
                    enfermeiro.setNome(rs.getString("nome"));
                    enfermeiro.setEmail(rs.getString("email"));
                    enfermeiro.setSenha(rs.getString("senha"));
                    enfermeiros.add(enfermeiro);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar enfermeiros do paciente \"" + paciente.getCpf() + "\": " + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
                }
            }

            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }

            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar Connection: " + e.getMessage());
                }
            }
        }
        return enfermeiros;
    }

    public void delete(Cuida cuida) {
        String sql = "DELETE FROM cuida WHERE COREN=? AND CPF_Paciente=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cuida.getEnfermeiro().getCoren());
            pst.setString(2, cuida.getPaciente().getCpf());
            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar cuida: " + e.getMessage());
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }

            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar Connection: " + e.getMessage());
                }
            }
        }
    }
}
