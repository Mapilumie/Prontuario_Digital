package br.com.prontuario.dao;

import br.com.prontuario.db.ConnectionFactory;
import br.com.prontuario.model.Acompanhante;
import br.com.prontuario.model.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AcompanhanteDAO implements IAcompanhanteDAO {
    @Override
    public void save(Acompanhante acompanhante) {
        String sql = "INSERT INTO acompanhante (CPF_Acompanhante, CPF_Paciente, nomeAcompanhante, relacao) VALUES (?,?,?,?)";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, acompanhante.getCpf());
            if (acompanhante.getPaciente() != null) {
                pst.setString(2, acompanhante.getPaciente().getCpf());
            } else {
                throw new SQLException("Erro ao salvar acompanhante: Nenhum paciente vinculado");
            }
            pst.setString(3, acompanhante.getNome());
            pst.setString(4, acompanhante.getRelacao());
            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar acompanhante: " + e.getMessage());
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

    // Busca acompanhantes de um paciente
    @Override
    public List<Acompanhante> findAllByPaciente(Paciente paciente) {
        String sql = "SELECT * FROM acompanhante WHERE CPF_Paciente=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        List<Acompanhante> acompanhantes = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, paciente.getCpf());
            rs = pst.executeQuery();
            if (rs != null) {
                acompanhantes = new ArrayList<Acompanhante>();
                while (rs.next()) {
                    Acompanhante acompanhante = new Acompanhante();
                    acompanhante.setCpf(rs.getString("CPF_Acompanhante"));
                    acompanhante.setPaciente(paciente);
                    acompanhante.setNome(rs.getString("nomeAcompanhante"));
                    acompanhante.setRelacao(rs.getString("relacao"));
                    acompanhantes.add(acompanhante);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar acompanhantes do paciente \"" + paciente.getCpf() + "\": " + e.getMessage());
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
        return acompanhantes;
    }

    @Override
    public Acompanhante findByCpf(String cpf) {
        String sql = "SELECT * FROM acompanhante WHERE CPF_Acompanhante=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        Acompanhante acompanhante = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cpf);
            rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    acompanhante = new Acompanhante();
                    acompanhante.setCpf(rs.getString("CPF_Acompanhante"));
                    String fkPaciente = rs.getString("CPF_Paciente");
                    if (fkPaciente != null) {
                        Paciente paciente = new Paciente();
                        paciente.setCpf(fkPaciente);
                        acompanhante.setPaciente(paciente);
                    }
                    acompanhante.setNome("nomeAcompanhante");
                    acompanhante.setRelacao("relacao");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar acompanhante por cpf \"" + cpf + "\": " + e.getMessage());
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
        return acompanhante;
    }

    @Override
    public void update(Acompanhante acompanhante) {
        String sql = "UPDATE acompanhante SET nomeAcompanhante=?, relacao=? WHERE CPF_Acompanhante=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, acompanhante.getNome());
            pst.setString(2, acompanhante.getRelacao());
            pst.setString(3, acompanhante.getCpf());
            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar acompanhante: " + e.getMessage());
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

    @Override
    public void delete(Acompanhante acompanhante) {
        String sql = "DELETE FROM acompanhante WHERE CPF_Acompanhante=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, acompanhante.getCpf());
            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar acompanhante: " + e.getMessage());
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
