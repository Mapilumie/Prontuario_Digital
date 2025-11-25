package br.com.prontuario.dao;

import br.com.prontuario.db.ConnectionFactory;
import br.com.prontuario.model.Paciente;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO implements IPacienteDAO {
    // Puxa o fuso horário da Bahia
    private static final ZoneId FUSO_HORARIO = ZoneId.of("America/Bahia");

    @Override
    public void save(Paciente paciente) {
        String sql = "INSERT INTO paciente (CPF_Paciente, nome, sobrenome, estadoCivil, endereco, sexo, peso, dataNascimento, dataEntrada) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, paciente.getCpf());
            pst.setString(2, paciente.getNome());
            pst.setString(3, paciente.getSobrenome());
            pst.setString(4, paciente.getEstadoCivil());
            pst.setString(5, paciente.getEndereco());
            pst.setString(6, String.valueOf(paciente.getSexo()));
            pst.setDouble(7, paciente.getPeso());
            pst.setObject(8, paciente.getDataNascimento());

            // Recebe a data-hora entrada => Informa o fuso-horário => Converte para UTC  para salvar no bd
            pst.setTimestamp(9, java.sql.Timestamp.from(paciente.getDataEntrada().atZone(FUSO_HORARIO).toInstant()));

            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar paciente: " + e.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
            }

            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar Connection: " + e.getMessage());
            }
        }
    }

    @Override
    public List<Paciente> findAll() {
        String sql = "SELECT * FROM paciente";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        List<Paciente> pacientes = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
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
            System.err.println("Erro ao listar pacientes: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar ResultSet:" + e.getMessage());
            }

            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
            }

            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar Connection: " + e.getMessage());
            }
        }
        return pacientes;
    }

    @Override
    public List<Paciente> findAllByNomeSobrenome(String nomeSobrenome) {
        String sql = "SELECT * FROM paciente WHERE (nome || ' ' || sobrenome) LIKE ?";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        List<Paciente> pacientes = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, nomeSobrenome + "%");
            rs = pst.executeQuery();
            if (rs != null) {
                pacientes = new ArrayList<Paciente>();
                while (rs.next()) {
                    Paciente paciente = new Paciente();
                    paciente.setCpf(rs.getString("CPF_Paciente"));
                    paciente.setNome(rs.getString("nome"));
                    paciente.setSobrenome(rs.getString("sobrenome"));
                    paciente.setEndereco(rs.getString("endereco"));
                    paciente.setEstadoCivil(rs.getString("estadoCivil"));
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
            System.err.println("Erro ao listar pacientes com nome e sobrenome \"" + nomeSobrenome + "\": " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar ResultSet:" + e.getMessage());
            }

            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
            }

            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar Connection: " + e.getMessage());
            }
        }
        return pacientes;
    }

    @Override
    public Paciente findByCpf(String cpf) {
        String sql = "SELECT * FROM paciente WHERE CPF_Paciente=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        Paciente paciente = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cpf);
            rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    paciente = new Paciente();
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
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar paciente por cpf \"" + cpf + "\": " + e.getMessage());
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar ResultSet:" + e.getMessage());
            }

            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
            }

            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar Connection: " + e.getMessage());
            }
        }
        return paciente;
    }

    @Override
    public void update(Paciente paciente) {
        String sql = "UPDATE paciente SET nome=?, sobrenome=?, estadoCivil=?, endereco=?, sexo=?, peso=?, dataNascimento=?, dataSaida=? WHERE CPF_Paciente=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, paciente.getNome());
            pst.setString(2, paciente.getSobrenome());
            pst.setString(3, paciente.getEstadoCivil());
            pst.setString(4, paciente.getEndereco());
            pst.setString(5, String.valueOf(paciente.getSexo()));
            pst.setDouble(6, paciente.getPeso());
            pst.setObject(7, paciente.getDataNascimento());
            if (paciente.getDataSaida() != null) {
                pst.setTimestamp(8, java.sql.Timestamp.from(paciente.getDataSaida().atZone(FUSO_HORARIO).toInstant()));
            } else {
                pst.setNull(8, java.sql.Types.TIMESTAMP);
            }
            pst.setString(9, paciente.getCpf());
            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar paciente: " + e.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
            }

            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar Connection: " + e.getMessage());
            }
        }
    }

    @Override
    public void delete(Paciente paciente) {
        String sql = "DELETE FROM paciente WHERE CPF_Paciente=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, paciente.getCpf());
            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar paciente: " + e.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
            }

            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar Connection: " + e.getMessage());
            }
        }
    }
}
